package com.thinkenterprise.graphqlio.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.thinkenterprise.graphqlio.server.gs.handler.GsWebSocketHandler;

public class GraphQLIOClientThreads extends Thread {

	QueryType typ = QueryType.SIMPLE;

	public static void main(String[] args) {
		GraphQLIOClientThreads x = new GraphQLIOClientThreads(QueryType.SIMPLE);
		x.start();
		x = new GraphQLIOClientThreads(QueryType.MUTATION);
		x.start();
		x = new GraphQLIOClientThreads(QueryType.SUBSCRIPTION);
		x.start();
		x = new GraphQLIOClientThreads(QueryType.BINARY);
		x.start();
	}

	public GraphQLIOClientThreads(QueryType typ) {
		this.typ = typ;
	}

	public void run() {
		switch (this.typ) {
		case SIMPLE:
			runSimple();
			break;

		case MUTATION:
			runMutation();
			break;

		case SUBSCRIPTION:
			runSubscription();
			break;

		case BINARY:
			runBinary();
			break;

		default:
			break;
		}
	}

	private void runSimple() {
		List list = new ArrayList<>();

		String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id } } ]";
		list.add(new TextMessage(simpleQuery));

		simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id flightNumber departure } } ]";
		list.add(new TextMessage(simpleQuery));

		simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id departure destination signature } } ]";
		list.add(new TextMessage(simpleQuery));

		new GraphQLIOClient().runQueries(list, 500, 4500);
	}

	private void runMutation() {
		List list = new ArrayList<>();

		String mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
				+ " input: { flightNumber:\"LH7902\" departure:\"MUC\" destination: \"BER\""
				+ " disabled:true signature:\"3b241101-e2bb-4255-8caf-4136c566a964\" "
				+ " bookingDate:\"2019-11-21 00:00:00\" } )"
				+ " { flightNumber departure destination disabled signature bookingDate } } ]";
		list.add(new TextMessage(mutationQuery));

		mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
				+ " input: { flightNumber:\"LH7902\" "
				+ " disabled:true signature:\"3b241101-e2bb-4255-8caf-4136c566a964\" "
				+ " bookingDate:\"2019-11-21 00:00:00\" } )" + " { flightNumber departure destination } } ]";
		list.add(new TextMessage(mutationQuery));

		mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute(flightNumber: \"LH7902\" "
				+ " input: { flightNumber:\"LH7902\" bookingDate:\"2019-11-21 00:00:00\" } )"
				+ " { id disabled signature bookingDate } } ]";
		list.add(new TextMessage(mutationQuery));

		new GraphQLIOClient().runQueries(list, 500, 4500);
	}

	private void runSubscription() {
		List list = new ArrayList<>();

		String subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id flightNumber } } ]";
		list.add(new TextMessage(subscriptionQuery));

		subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { unsubscribe( sid: \"test\" ) } routes { id } } ]";
		list.add(new TextMessage(subscriptionQuery));

		subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { flightNumber departure destination } } ]";
		list.add(new TextMessage(subscriptionQuery));

		new GraphQLIOClient().runQueries(list, 500, 4500);
	}

	private void runBinary() {
		try {
			String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id } } ]";

			List list = new ArrayList<>();
			list.add(GsWebSocketHandler.createFromStringCbor(simpleQuery));

			new GraphQLIOClient().runQueries(list, new WebSocketHandlerCbor(), GsWebSocketHandler.SUB_PROTOCOL_CBOR,
					500, 4500);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	enum QueryType {
		SIMPLE, MUTATION, SUBSCRIPTION, BINARY;
	}

}