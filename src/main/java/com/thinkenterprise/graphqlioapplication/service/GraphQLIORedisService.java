package com.thinkenterprise.graphqlioapplication.service;

import java.io.IOException;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.thinkenterprise.graphqlio.server.gts.keyvaluestore.GtsGraphQLRedisService;

@Profile("test")
@Service
public class GraphQLIORedisService implements ApplicationRunner, DisposableBean {

	@Autowired
	private GtsGraphQLRedisService graphQLRedisService;

	public GraphQLIORedisService() {
	}

	@Override
	public void run(ApplicationArguments args) {
		try {
			this.graphQLRedisService.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() throws Exception {
		this.graphQLRedisService.stop();
	}

}
