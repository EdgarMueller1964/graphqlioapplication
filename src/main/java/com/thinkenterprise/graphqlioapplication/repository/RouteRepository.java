package com.thinkenterprise.graphqlioapplication.repository;

/*
 * Copyright (C) 2016 Thinkenterprise
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * @author Rafael Kansy
 * @author Michael Schaefer
 */

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.thinkenterprise.graphqlioapplication.domain.Route;


public interface RouteRepository extends CrudRepository<Route, Long> {
    Iterable<Route> findByDeparture(@Param("departure") String departure);

    @EntityGraph(value = "routeFlight", type = EntityGraph.EntityGraphType.LOAD)
    Iterable<Route> getByDestination(@Param("destination") String destination);

    @Query(value = "select r from Route r where r.departure = :departure")
    Iterable<Route> queryFindByDeparture(@Param("departure") String departure);

    @Query(value = "select r from Route r where r.destination = :destination")
    Iterable<Route> findByDestination(@Param("destination") String destination);

    @Query(value = "SELECT * FROM ROUTE WHERE FLIGHT_NUMBER = ?1", nativeQuery = true)
    Iterable<Route> findByFlightNumber(@Param("flightNumber") String flightNumber);

    @Query(value = "SELECT * FROM ROUTE WHERE FLIGHT_NUMBER = ?1", nativeQuery = true)
    Route getByFlightNumber(@Param("flightNumber") String flightNumber);
    
}
