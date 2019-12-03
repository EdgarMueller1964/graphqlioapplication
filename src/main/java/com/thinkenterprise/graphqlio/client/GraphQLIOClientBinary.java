package com.thinkenterprise.graphqlio.client;

import java.util.ArrayList;
import java.util.List;

import com.thinkenterprise.graphqlio.server.gs.handler.GsWebSocketHandler;

public class GraphQLIOClientBinary {

	public static void main(String[] args) {
		try {
			String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id } } ]";

			List list = new ArrayList<>();
			list.add(GsWebSocketHandler.createFromStringCbor(simpleQuery));

			new GraphQLIOClient().runQueries(list, new WebSocketHandlerCbor(), GsWebSocketHandler.SUB_PROTOCOL_CBOR);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}