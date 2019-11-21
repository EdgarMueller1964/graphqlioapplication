package com.thinkenterprise.graphqlioapplication.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.thinkenterprise.graphqlio.server.gs.graphql.GsGraphQLService;

@Service
public class GraphQLIOService {
	
	
	private GsGraphQLService gsGraphQlService;
	
	GraphQLIOService(GsGraphQLService gsGraphQlService){
		this.gsGraphQlService = gsGraphQlService;
	}

	
	@PostConstruct
	void initialize() {
  	////   add schema
		gsGraphQlService.start();    	
	}
	
	
}
