package com.thinkenterprise.graphqlio.client;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;

public class GraphQLIOClientBinary {

	public static void main(String[] args) {
		try {
			WebSocketClient webSocketClient = new StandardWebSocketClient();
			WebSocketSession webSocketSession = webSocketClient.doHandshake(new StandardClientTextWebSocketHandler(),
					new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();
			int i = 0;
			while (i++ < 10) {
				byte[] bytes = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id } } ]".getBytes();
				DataItem dataItem = new ByteString(bytes);

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				new CborEncoder(os).encode(dataItem);

				webSocketSession.sendMessage(new BinaryMessage(os.toByteArray()));
				Thread.sleep(5000);
			}
			webSocketSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}