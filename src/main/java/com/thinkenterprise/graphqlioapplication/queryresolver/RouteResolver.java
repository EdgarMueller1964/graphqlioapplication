package com.thinkenterprise.graphqlioapplication.queryresolver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.thinkenterprise.graphqlioapplication.domain.Route;
import com.thinkenterprise.graphqlioapplication.repository.RouteRepository;

@Component
public class RouteResolver implements GraphQLQueryResolver {

	private RouteRepository routeRepository;

	public RouteResolver(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	public List<Route> routes() {
		Iterable<Route> allUsers = routeRepository.findAll();

		List<Route> allRoutesList = new ArrayList<>();
		allUsers.forEach(allRoutesList::add);
		return allRoutesList;
	}
}
