package com.thinkenterprise.graphqlioapplication.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.thinkenterprise.graphqlio.server.gs.server.GsServer;

@Service
public class GraphQLIOService implements ApplicationRunner {
	
	
	private GsServer graphqlioServer;
	
	GraphQLIOService(GsServer graphqlioServer){
		this.graphqlioServer = graphqlioServer;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.graphqlioServer.start();
		
	}
	
}
