/*
 * Copyright 2012-2013 the original author or authors.
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
 */

package com.onetwocm.application.data.jpa.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.onetwocm.application.data.jpa.domain.City;

@ComponentScan
public interface CityRepository extends CrudRepository<City, Long> {
	
	//document
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.named-queries
	
	/*
	 * Basic CRUD
	 */
	
	//insert, update
	@SuppressWarnings("unchecked")
	City save(City entity);

	//select
	Page<City> findAll(Pageable pageable);
	
	boolean existsById(Long id);
	
	//delete
	void delete(City entity);
	
	
	/*
	 * Advanced 
	 */
	Page<City> findByNameContainingAndCountryContainingAllIgnoringCase(String name,
			String country, Pageable pageable);

	City findByNameAndCountryAllIgnoringCase(String name, String country);
	
	
	/*
	@Query("select h.city as city, h.name as name, avg(r.rating) as averageRating "
			+ "from Hotel h left outer join h.reviews r where h.city = ?1 group by h")
	Page<City> findByCity(City city, Pageable pageable);
	 */
}
