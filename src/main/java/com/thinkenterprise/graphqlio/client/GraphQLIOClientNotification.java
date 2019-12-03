package com.thinkenterprise.graphqlio.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GraphQLIOClientNotification extends Thread {

	QueryType typ = QueryType.MUTATION;

	public static void main(String[] args) {
		try {
			// der Sub-Thread startet die Subscriptions
			// jeweils nacheinander mit ausreichend Zeit dazwischen
			new GraphQLIOClientNotification(QueryType.SUBSCRIPTION).start();

			// der Mut-Thread startet die Mutations
			// jeweils nacheinander mit etwas weniger Zeit dazwischen,
			// so dass die Mutations verschiedene Notifications triggern
			new GraphQLIOClientNotification(QueryType.MUTATION).start();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public GraphQLIOClientNotification(QueryType typ) {
		this.typ = typ;
	}

	enum QueryType {
		SUBSCRIPTION, MUTATION;
	}

	public void run() {
		switch (this.typ) {
		case SUBSCRIPTION:
			runSubscriptions();
			break;

		case MUTATION:
			runMutations();
			break;

		default:
			break;
		}
	}

	private List<String> subscriptionIds = new ArrayList<String>();

	public void runSubscriptions() {
		try {
			List<AbstractWebSocketMessage> messages = new ArrayList<AbstractWebSocketMessage>();

			String subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id flightNumber departure destination } } ]";
			messages.add(new TextMessage(subscriptionQuery));

			subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id flightNumber } } ]";
			messages.add(new TextMessage(subscriptionQuery));

			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient.doHandshake(new LocalWebSocketHandler(subscriptionIds),
					new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();

			// Subscriptions:
			for (AbstractWebSocketMessage message : messages) {
				webSocketSession.sendMessage(message);
				// Wartezeit 13 sec
				Thread.sleep(13000);
			}

			messages = new ArrayList<AbstractWebSocketMessage>();

			String unsubscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { _Subscription { unsubscribe ( sid: \""
					+ subscriptionIds.get(0) + "\" ) } } ]";
			messages.add(new TextMessage(unsubscriptionQuery));

			unsubscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { _Subscription { unsubscribe ( sid: \""
					+ subscriptionIds.get(1) + "\" ) } } ]";
			messages.add(new TextMessage(unsubscriptionQuery));

			// Un-Subscriptions:
			for (AbstractWebSocketMessage message : messages) {
				webSocketSession.sendMessage(message);
				// Wartezeit 1 sec
				Thread.sleep(1000);
			}
			// danach noch 8 sec warten:
			Thread.sleep(8000);

			webSocketSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runMutations() {
		try {
			List<AbstractWebSocketMessage> messages = new ArrayList<AbstractWebSocketMessage>();

			String mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
					+ " input: { flightNumber: \"LH7902\" departure: \"MUC\" destination: \"BER\" } )"
					+ " { id flightNumber departure destination disabled signature bookingDate } } ]";
			messages.add(new TextMessage(mutationQuery));

			mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
					+ " input: { flightNumber:\"LH7902\" } )" + " { id flightNumber departure destination } } ]";
			messages.add(new TextMessage(mutationQuery));

			mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
					+ " input: { flightNumber:\"LH7902\" destination: \"HAM\" } )"
					+ " { id departure destination bookingDate } } ]";
			messages.add(new TextMessage(mutationQuery));

			mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
					+ " input: { flightNumber: \"LH7902\" departure: \"MUC\" destination: \"BER\" } )"
					+ " { id flightNumber departure destination disabled signature bookingDate } } ]";
			messages.add(new TextMessage(mutationQuery));

			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient.doHandshake(new StandardClientTextWebSocketHandler(),
					new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();

			for (AbstractWebSocketMessage message : messages) {
				// Wartezeit 2 sec
				Thread.sleep(2000);

				webSocketSession.sendMessage(message);

				// Wartezeit 7 sec
				Thread.sleep(7000);
			}

			webSocketSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class LocalWebSocketHandler extends TextWebSocketHandler {

		private List<String> subscriptionIds = new ArrayList<String>();

		public LocalWebSocketHandler(List<String> subscriptionIds) {
			this.subscriptionIds = subscriptionIds;
		}

		// LocalWebSocketHandler::message received :
		// [1,1,"GRAPHQL-RESPONSE",{"data":{"_Subscription":{"subscribe":"71adc7f9-8747-49c9-bd5a-4b6ccd81d660"},"routes":[{"id":"1","flightNumber":"LH7902","departure":"MUC","destination":"IAH"},{"id":"2","flightNumber":"LH1602","departure":"MUC","destination":"IBZ"},{"id":"3","flightNumber":"LH401","departure":"FRA","destination":"NYC"}]}}]

		/*
		 * {"data":{ "_Subscription":{
		 * "subscribe":"71adc7f9-8747-49c9-bd5a-4b6ccd81d660" },"routes":[
		 * {"id":"1","flightNumber":"LH7902","departure":"MUC","destination":"IAH"},
		 * {"id":"2","flightNumber":"LH1602","departure":"MUC","destination":"IBZ"},
		 * {"id":"3","flightNumber":"LH401","departure":"FRA","destination":"NYC"} ] }}
		 */

		@Override
		protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			System.out.println("LocalWebSocketHandler::message received : " + message.getPayload());

			String payload = message.getPayload();
			int pos_gql = payload.indexOf("GRAPHQL-RESPONSE");
			int pos_sub = payload.indexOf("_Subscription");
			int pos = payload.indexOf("\"subscribe");
			if (pos_gql > 0 && pos_sub > 0 && pos > 0) {
				payload = payload.substring(pos - 1, payload.indexOf("}", pos) + 1);
				System.out.println("LocalWebSocketHandler::payload = " + payload);
				JSONObject json = new JSONObject(payload);
				String subscriptionId = json.getString("subscribe");
				this.subscriptionIds.add(subscriptionId);
				System.out.println("LocalWebSocketHandler::subscriptionId = " + subscriptionId);
			}
		}

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			System.out.println("LocalWebSocketHandler::connection esablished  : " + session.getId());
		}

	}

}