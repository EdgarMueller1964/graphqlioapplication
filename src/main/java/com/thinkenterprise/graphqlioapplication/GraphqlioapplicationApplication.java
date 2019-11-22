package com.thinkenterprise.graphqlioapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thinkenterprise.graphqlio.EnableGraphQLIOLibraryModule;

@SpringBootApplication
@EnableGraphQLIOLibraryModule
public class GraphqlioapplicationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GraphqlioapplicationApplication.class, args);
		
	}
}
