package com.thinkenterprise.graphqlio.client;

import java.net.URI;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class GraphQLIOClientMutation {

    public static void main(String[] args)  {
        try {
            final String mutationQuery = "[1,0,\"GRAPHQL-REQUEST\",{\"mutation\":\"{  updateRoute(flightNumber: \"LH7902\" input:  {" + 
            		"    flightNumber:\"LH7902\"" + 
            		"    departure:\"MUC\"" + 
            		"    destination: \"BER\"" + 
            		"    disabled:true" + 
            		"    signature:\"3b241101-e2bb-4255-8caf-4136c566a964\"" + 
            		"    bookingDate:\"2019-11-21 00:00:00\"" + 
            		"  })" + 
            		"  {" + 
            		"    flightNumber" + 
            		"    departure" + 
            		"    destination" + 
            		"    disabled" + 
            		"    signature" + 
            		"    bookingDate" + 
            		"  } \"}]";

            WebSocketClient webSocketClient = new StandardWebSocketClient();
            WebSocketSession webSocketSession = webSocketClient.doHandshake(new StandardClientTextWebSocketHandler(), new WebSocketHttpHeaders(), URI.create("ws://127.0.0.1:8080/api/data/graph")).get();
            int i = 0;
            while(i++<10) {
              webSocketSession.sendMessage(new TextMessage(mutationQuery)); 
              Thread.sleep(5000);
            }
            webSocketSession.close(); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
     
    }


}