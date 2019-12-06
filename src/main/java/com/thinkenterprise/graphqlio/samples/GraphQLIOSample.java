package com.thinkenterprise.graphqlio.samples;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.thinkenterprise.graphqlio.server.gs.handler.GsWebSocketHandler;

public class GraphQLIOSample {

	private final Logger logger = LoggerFactory.getLogger(GraphQLIOSample.class);

	private static final String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id flightNumber } } ]";
	private static final String simpleSubscription = "[1,0,\"GRAPHQL-REQUEST\",query { _Subscription { subscribe } routes { id departure destination } } ]";
	private static final String simpleMutation = "[1,0,\"GRAPHQL-REQUEST\",mutation { updateRoute (flightNumber: \"LH7902\" input: { flightNumber:\"LH7902\" } ) { id flightNumber departure destination } } ]";
	private static final String simpleUnsubscribe = "[1,0,\"GRAPHQL-REQUEST\",mutation { _Subscription { unsubscribe ( sid: \"%s\" ) } } ]";

	private static final List<String> subscriptionIds = new ArrayList<String>();

	public static void main(String[] args) {
		try {
			GraphQLIOSampleHandler webSocketHandler = new GraphQLIOSampleHandler(subscriptionIds);

			WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
			headers.setSecWebSocketProtocol(Arrays.asList(GsWebSocketHandler.SUB_PROTOCOL_TEXT));

			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient
					.doHandshake(webSocketHandler, headers, URI.create("ws://127.0.0.1:8080/api/data/graph")).get();

			// send 1st query to GraphQLIO server:
			webSocketSession.sendMessage(new TextMessage(simpleQuery));
			// a little wait for getting and handling answer(s)
			Thread.sleep(200);

			// subscribe to another query
			webSocketSession.sendMessage(new TextMessage(simpleSubscription));
			// a little wait for getting and handling answer(s)
			// here: receiving subscriptionId
			Thread.sleep(200);

			// send a mutation so a notification should be triggered
			webSocketSession.sendMessage(new TextMessage(simpleMutation));
			// a little wait for getting and handling answer(s)
			// here: answer and dnotification
			Thread.sleep(200);

			// unsubscribe from subscription (same id!)
			webSocketSession.sendMessage(new TextMessage(simpleUnsubscribe.replace("%s", subscriptionIds.get(0))));
			// a little wait for getting and handling answer(s)
			Thread.sleep(200);

			// send a mutation; now no notification should be triggered
			webSocketSession.sendMessage(new TextMessage(simpleMutation));
			// a little wait for getting and handling answer(s)
			// here: no notification is triggered because no subscription is active
			Thread.sleep(200);

			webSocketSession.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}