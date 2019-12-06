package com.thinkenterprise.graphqlioapplication.mutationresolver;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.thinkenterprise.graphqlio.server.gts.context.GtsContext;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord.GtsArityType;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord.GtsOperationType;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsScope;
import com.thinkenterprise.graphqlioapplication.domain.Route;
import com.thinkenterprise.graphqlioapplication.domain.mutationinput.CreateRouteInput;
import com.thinkenterprise.graphqlioapplication.domain.mutationinput.UpdateRouteInput;
import com.thinkenterprise.graphqlioapplication.errors.RouteGraphQLError;
import com.thinkenterprise.graphqlioapplication.exceptions.RouteException;
import com.thinkenterprise.graphqlioapplication.repository.RouteRepository;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;

@Component
public class MutationResolver implements GraphQLMutationResolver {

	@Value("${route.exception}")
	private Boolean exception; 
		
	private RouteRepository routeRepository;

	public MutationResolver(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	@Transactional
	public Route updateRoute(String flightNumber, UpdateRouteInput input, DataFetchingEnvironment env) {
		Route route = routeRepository.getByFlightNumber(flightNumber);

		route.setFlightNumber(input.getFlightNumber());
		route.setDeparture(input.getDeparture());
		route.setDestination(input.getDestination());
		route.setDisabled(input.getDisabled());
		route.setSignature(input.getSignature());
		route.setBookingDate(input.getBookingDate());

		Route modifiedRoute = null;
		if(!exception)
			modifiedRoute =  routeRepository.save(route);
		else 
			throw new RouteException("Test Exception ....");
								
//		      ctx.scope.record({ op: "read", arity: "all", dstType: "Item", dstIds: result.map((item) => item.id), dstAttrs: attr })
	   	   	GtsContext context = env.getContext();
	    	GtsScope scope = context.getScope();
			scope.addRecord(GtsRecord.builder()
	    		.op(GtsOperationType.UPDATE)
	    		.arity(GtsArityType.ONE)
	    		.dstType(Route.class.getName())
	    		.dstIds(new String[] { modifiedRoute.getId().toString()} )
	    		.dstAttrs(new String[] {"*"})
	    		.build());			
			
			return modifiedRoute;
			
	}

	@Transactional
	public Route createRoute(CreateRouteInput input) {
		return routeRepository.save(input.toRoute());
	}
//	public Long deleteRoute(String flightNumber) {
//		routeRepository.delete((Route) routeRepository.findByFlightNumber(flightNumber));
//	return 1L;
//	}

	
	@ExceptionHandler(RouteException.class)
	public GraphQLError exception(RouteException routeException) {
		return new RouteGraphQLError(routeException.getMessage());
	}
	
	
}
