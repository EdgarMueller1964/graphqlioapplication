package com.thinkenterprise.graphqlio.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.TextMessage;

public class GraphQLIOClientSubscription {

	public static void main(String[] args) {
		// final String subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",{\"query\":\"{
		// _Subscription { subscribe } routes { id } } \"}]";
		final String subscriptionQuery = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id } } ]";

		List list = new ArrayList<>();
		list.add(new TextMessage(subscriptionQuery));

		new GraphQLIOClient().runQueries(list);
	}

}