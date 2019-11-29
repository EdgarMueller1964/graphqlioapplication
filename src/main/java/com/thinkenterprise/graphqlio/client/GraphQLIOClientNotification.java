package com.thinkenterprise.graphqlio.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

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

	public void runSubscriptions() {
		try {
			List<AbstractWebSocketMessage> messages = new ArrayList<AbstractWebSocketMessage>();

			String subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id flightNumber departure destination } } ]";
			messages.add(new TextMessage(subscriptionQuery));

			subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id flightNumber } } ]";
			messages.add(new TextMessage(subscriptionQuery));

			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient.doHandshake(new StandardClientTextWebSocketHandler(),
					new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();

			for (AbstractWebSocketMessage message : messages) {
				webSocketSession.sendMessage(message);
				// Wartezeit 15 sec
				Thread.sleep(15000);
			}

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

			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient.doHandshake(new StandardClientTextWebSocketHandler(),
					new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();

			for (AbstractWebSocketMessage message : messages) {
				webSocketSession.sendMessage(message);
				// Wartezeit 9 sec
				Thread.sleep(9000);
			}

			webSocketSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}