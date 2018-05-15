/*
 * Copyright 2012-2018 the original author or authors.
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.onetwocm.application.data.jpa.domain.City;

@Component("cityService")
@Transactional
class CityServiceImpl implements CityService {

	private final CityRepository cityRepository;

	public CityServiceImpl(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Override
	public Page<City> findCities(CitySearchCriteria criteria, Pageable pageable) {

		Assert.notNull(criteria, "Criteria must not be null");
		String name = criteria.getName();
		String country = criteria.getCountry() == null ? "" : criteria.getCountry();

		if (!StringUtils.hasLength(name) && !StringUtils.hasLength(country)) {
			return this.cityRepository.findAll(pageable);
		}

		return this.cityRepository
				.findByNameContainingAndCountryContainingAllIgnoringCase(name,
						country, pageable);
	}

	@Override
	public City getCity(String name, String country) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(country, "Country must not be null");
		return this.cityRepository.findByNameAndCountryAllIgnoringCase(name, country);
	}

	@Override
	public City saveCity(City city) {
		return cityRepository.save(city);
	}

}
