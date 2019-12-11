package com.thinkenterprise.graphqlio.client;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.thinkenterprise.graphqlio.server.gs.handler.GsWebSocketHandler;

public class WebSocketHandlerCbor extends BinaryWebSocketHandler {

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		System.out.println(this.getClass().getName() + "::handleBinaryMessage : " + session.getId());
		System.out.println(this.getClass().getName() + "::getAcceptedProtocol : " + session.getAcceptedProtocol());
		System.out.println(this.getClass().getName() + "::getFromCbor : " + GsWebSocketHandler.getFromCbor(message));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("connection established  : " + session.getId());
	}

}
