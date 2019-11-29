package com.thinkenterprise.graphqlio.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.BinaryMessage;

import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;

public class GraphQLIOClientBinary {

	public static void main(String[] args) {
		try {
			byte[] bytes = "[1,0,\"GRAPHQL-REQUEST\",query { routes { id } } ]".getBytes();
			DataItem dataItem = new ByteString(bytes);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			new CborEncoder(os).encode(dataItem);

			List list = new ArrayList<>();
			list.add(new BinaryMessage(os.toByteArray()));

			new GraphQLIOClient().runQueries(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}