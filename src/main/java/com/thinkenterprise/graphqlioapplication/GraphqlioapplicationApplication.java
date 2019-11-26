package com.thinkenterprise.graphqlioapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thinkenterprise.graphqlio.server.gs.EnableGraphQLIOGsLibraryModule;
import com.thinkenterprise.graphqlio.server.gts.EnableGraphQLIOGtsLibraryModule;
import com.thinkenterprise.graphqlio.server.gtt.EnableGraphQLIOGttLibraryModule;
import com.thinkenterprise.graphqlio.server.wsf.EnableGraphQLIOWsfLibraryModule;

//import com.thinkenterprise.graphqlio.EnableGraphQLIOLibraryModule;

@SpringBootApplication
@EnableGraphQLIOWsfLibraryModule
@EnableGraphQLIOGttLibraryModule
@EnableGraphQLIOGtsLibraryModule
@EnableGraphQLIOGsLibraryModule
public class GraphqlioapplicationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GraphqlioapplicationApplication.class, args);
		
	}
}
