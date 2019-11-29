package com.thinkenterprise.graphqlioapplication.mutationresolver;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.thinkenterprise.graphqlio.server.gts.context.GtsContext;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsScope;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord.GtsArityType;
import com.thinkenterprise.graphqlio.server.gts.tracking.GtsRecord.GtsOperationType;
import com.thinkenterprise.graphqlioapplication.domain.Route;
import com.thinkenterprise.graphqlioapplication.domain.mutationinput.UpdateRouteInput;
import com.thinkenterprise.graphqlioapplication.repository.RouteRepository;

import graphql.schema.DataFetchingEnvironment;

@Component
public class MutationResolver implements GraphQLMutationResolver {

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

		Route modifiedRoute =  routeRepository.save(route);
		
//	      ctx.scope.record({ op: "read", arity: "all", dstType: "Item", dstIds: result.map((item) => item.id), dstAttrs: attr })
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

//	@Transactional
//	public Route createRoute(CreateRouteInput input) {
//		return routeRepository.save(input.toRoute());
//	}
//	public Long deleteRoute(String flightNumber) {
//		routeRepository.delete((Route) routeRepository.findByFlightNumber(flightNumber));
//	return 1L;
//	}

}
