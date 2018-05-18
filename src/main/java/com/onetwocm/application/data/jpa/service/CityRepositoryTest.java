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

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.onetwocm.application.data.jpa.domain.City;
import com.onetwocm.application.data.jpa.domain.MemberRole;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class CityRepositoryTest {
	@Autowired
	CityRepository cityRepository;
	
	@Test
	public void insertTest() {
		for(int i=0; i<100; i++) {
			City city = new City();
			city.setName("city" + i);
			city.setCountry("country" + i);
			city.setState("state" + i);
			city.setMap("map" + i);
			cityRepository.save(city);
		}
	}
	
	@Test
	public void testMember() {
		Optional<City> result = cityRepository.findById(85L);
		result.ifPresent(city -> log.info("city " + city));
	}
}