package com.thinkenterprise.graphqlio.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class GraphQLIOClient {

	private static final Random RAND = new Random();

	public static void main(String[] args) {
		// final String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",{\"query\":\"{ routes {
		// id } } \"}]";
		final String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id } } ]";

		List list = new ArrayList<>();
		list.add(new TextMessage(simpleQuery));

		new GraphQLIOClient().runQueries(list);
	}

	public void runQueries(List<AbstractWebSocketMessage> message, Integer... b) {
		try {
			// min in [500..4999]
			Integer min = b.length > 0 ? b[0] : 4999;
			if (min < 500)
				min = 500;
			else if (min > 4999)
				min = 4999;

			// max in [1..4500]
			Integer max = b.length > 1 ? b[1] : 1;
			if (max < 1)
				max = 1;
			else if (max > 5000 - min)
				max = 5000 - min;

			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient.doHandshake(new StandardClientTextWebSocketHandler(),
					new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();
			int i = 0;
			while (i++ < 10) {
				webSocketSession.sendMessage(message.get(RAND.nextInt(message.size())));

				// zufällige Wartezeit 500-5000 ms
				Thread.sleep(min + RAND.nextInt(max));
			}
			webSocketSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}