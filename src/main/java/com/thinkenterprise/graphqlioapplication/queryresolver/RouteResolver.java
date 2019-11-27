package com.thinkenterprise.graphqlioapplication.queryresolver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.thinkenterprise.graphqlio.server.gts.context.GtsContext;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord.GtsArityType;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord.GtsOperationType;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsScope;
import com.thinkenterprise.graphqlioapplication.domain.Route;
import com.thinkenterprise.graphqlioapplication.repository.RouteRepository;

import graphql.schema.DataFetchingEnvironment;

@Component
public class RouteResolver implements GraphQLQueryResolver {

	private RouteRepository routeRepository;

	public RouteResolver(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	public List<Route> routes(DataFetchingEnvironment env) {
		
//      ctx.scope.record({ op: "read", arity: "all", dstType: "Item", dstIds: result.map((item) => item.id), dstAttrs: attr })
    	
    	GtsContext context = env.getContext();
    	GtsScope scope = context.getScope();
		scope.addRecord(GtsRecord.builder()
    		.op(GtsOperationType.READ)
    		.arity(GtsArityType.ALL)
    		.dstType("Route")
    		.dstIds(new String[] {"*"})
    		.dstAttrs(new String[] {"*"})
    		.build());
		
		Iterable<Route> allUsers = routeRepository.findAll();

		List<Route> allRoutesList = new ArrayList<>();
		allUsers.forEach(allRoutesList::add);
		return allRoutesList;
	}
}
