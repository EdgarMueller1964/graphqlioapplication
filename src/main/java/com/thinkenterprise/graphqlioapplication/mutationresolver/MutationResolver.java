package com.thinkenterprise.graphqlioapplication.mutationresolver;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.thinkenterprise.graphqlioapplication.domain.Route;
import com.thinkenterprise.graphqlioapplication.domain.mutationinput.CreateRouteInput;
import com.thinkenterprise.graphqlioapplication.domain.mutationinput.UpdateRouteInput;
import com.thinkenterprise.graphqlioapplication.repository.RouteRepository;
 
@Component
public class MutationResolver implements GraphQLMutationResolver{
	
private RouteRepository routeRepository;

	public MutationResolver(RouteRepository routeRepository) {
		this.routeRepository=routeRepository;
	}
	@Transactional
	public Route updateRoute(String flightNumber,UpdateRouteInput input) {
		Route route =routeRepository.getByFlightNumber(flightNumber);
				route.setDeparture(input.getDeparture());
				route.setDestination(input.getDestination());
				route.setDisabled(input.getDisabled());
				route.setSignature(input.getSignature());
				route.setBookingDate(input.getBookingDate());
		return routeRepository.save(route);
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
