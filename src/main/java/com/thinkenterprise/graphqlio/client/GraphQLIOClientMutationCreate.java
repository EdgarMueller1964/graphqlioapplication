package com.thinkenterprise.graphqlio.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.TextMessage;

public class GraphQLIOClientMutationCreate {

	public static void main(String[] args) {
		final String mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",mutation { createRoute(input: { flightNumber:\"LH888\" departure:\"FRA\" destination: \"PVG\" "
				+ " disabled:true signature:\"3b241101-e2bb-4255-8caf-4136c566a964\" "
				+ " bookingDate:\"2019-11-24 00:00:00\" } ) "
				+ " { flightNumber departure destination disabled signature bookingDate } } ]";

		List list = new ArrayList<>();
		list.add(new TextMessage(mutationQuery));

		new GraphQLIOClient().runQueries(list);
	}

}