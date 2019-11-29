package com.thinkenterprise.graphqlio.client;

import java.util.ArrayList;
import java.util.List;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.springframework.web.socket.BinaryMessage;

public class GraphQLIOClientBinaryMsgPack {

	public static void main(String[] args) {
		try {
			String simpleQuery = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id flightNumber destination } } ]";

			MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
			packer.packString(simpleQuery);
			packer.close();

			List list = new ArrayList<>();
			list.add(new BinaryMessage(packer.toByteArray()));

			new GraphQLIOClient().runQueries(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}