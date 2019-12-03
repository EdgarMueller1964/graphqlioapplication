package com.thinkenterprise.graphqlio.client;

import java.util.ArrayList;
import java.util.List;

import com.thinkenterprise.graphqlio.server.gs.handler.GsWebSocketHandler;

public class GraphQLIOClientBinaryMsgPack {

	public static void main(String[] args) {
		try {
			String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id flightNumber destination } } ]";

			List list = new ArrayList<>();
			list.add(GsWebSocketHandler.createFromStringMsgPack(simpleQuery));

			new GraphQLIOClient().runQueries(list, new WebSocketHandlerMsgPack(),
					GsWebSocketHandler.SUB_PROTOCOL_MSGPACK);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}