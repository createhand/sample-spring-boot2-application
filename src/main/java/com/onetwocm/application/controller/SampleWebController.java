/*
 * Copyright 2012-2016 the original author or authors.
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

package com.onetwocm.application.controller;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.onetwocm.application.data.jpa.domain.City;
import com.onetwocm.application.data.jpa.domain.Member;
import com.onetwocm.application.data.jpa.domain.MemberRole;
import com.onetwocm.application.data.jpa.service.CityRepository;
import com.onetwocm.application.data.jpa.service.CitySearchCriteria;
import com.onetwocm.application.data.jpa.service.CityService;
import com.onetwocm.application.data.jpa.service.MemberRepository;

@Controller
public class SampleWebController {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleWebController.class);

	@Autowired 
	private CityService cityService;
	
	@Autowired 
	private CityRepository cityRepository;
	
	@Autowired 
	private MemberRepository memberRepository;
	

	/************************************************************/
	// View
	/************************************************************/
	@GetMapping("/")
	public String index() {
		return "home";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/list")
	public String list(@RequestParam Map<String, Object> param, 
			@PageableDefault(value=4,sort= {"id"}, size=5, direction=Direction.DESC) Pageable pageable, 
			Model model) {
//		Page<City> cities = cityRepository.findAll(pageable);
		
		CitySearchCriteria criteria = new CitySearchCriteria();
		criteria.setName((String)param.get("name"));
		criteria.setCountry((String)param.get("country"));
		Page<City> cities = cityService.findCities(criteria, pageable);
		logger.info("cities.getNumber() : "+cities.getNumber());
		logger.info("cities.getNumberOfElements() : "+cities.getNumberOfElements());
		logger.info("cities.getSize() : "+cities.getSize());
		logger.info("cities.getTotalElements() : "+cities.getTotalElements());
		logger.info("cities.getTotalPages() : "+cities.getTotalPages());
		logger.info("cities.getPageable() : "+cities.getPageable());
		logger.info("\n\n==============================================");
		logger.info("cities.hasNext() : "+cities.hasNext());
		logger.info("cities.hasPrevious() : "+cities.hasPrevious());
		logger.info("cities.hasContent() : "+cities.hasContent());
		logger.info("cities.isFirst() : "+cities.isFirst());
		logger.info("cities.isLast() : "+cities.isLast());
		logger.info("cities.nextPageable() : "+cities.nextPageable());
		logger.info("cities.previousPageable() : "+cities.previousPageable());
		logger.info("\n\n==============================================");
		logger.info("cities.getPageable().isPaged() : "+cities.getPageable().isPaged());
		logger.info("cities.getPageable().isUnpaged() : "+cities.getPageable().isUnpaged());
		logger.info("cities.getPageable().first() : "+cities.getPageable().first());
		logger.info("cities.getPageable().getSort() : "+cities.getPageable().getSort());
		logger.info("cities.getPageable().getOffset() : "+cities.getPageable().getOffset());
		logger.info("cities.getPageable().getPageNumber() : "+cities.getPageable().getPageNumber());
		logger.info("cities.getPageable().getPageSize() : "+cities.getPageable().getPageSize());
		logger.info("cities.getPageable().hasPrevious() : "+cities.getPageable().hasPrevious());
		logger.info("cities.getPageable().next() : "+cities.getPageable().next());
		model.addAttribute("cities", cities);
		model.addAttribute("param", param);
		return "list";
	}
	
	@GetMapping("/listToJson")
	public String listToJson() {
		return "listToJson";
	}
	
	//return json
	@PostMapping("/api/listToJson")
	public ResponseEntity<Page<City>> listToJson(@RequestParam Map<String, Object> param, 
			@PageableDefault(value=4,sort= {"id"}, size=5, direction=Direction.DESC) Pageable pageable) {

		Page<City> persons = cityRepository.findAll(pageable);
		return ResponseEntity.ok(persons);
	}
	
//    @PostMapping
//    public ResponseEntity<Admin> registAdmin(@RequestBody Admin admin){
//        userService.createUser(admin);
//        Admin createAdmin = adminRepository.findByAdminId(admin.getAdminId());
//
//        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
//        return ResponseEntity.created(selfLink).body(createAdmin);
//    }
	
	@GetMapping("/detail")
	public String detail(@RequestParam Map<String, Object> param, Model model) {
		Optional<City> city = cityRepository.findById(Long.parseLong(param.get("cityId").toString()));
		
		model.addAttribute("city", city.get());
		return "detail";
	}
	
	@GetMapping("/save")
	public String save(@RequestParam Map<String, Object> param, Model model) {
		return "save";
	}
	
	@GetMapping("/signup")
	public String signup(@RequestParam Map<String, Object> param) {
		return "signup";
	}
	
	@GetMapping("/login")
	public String loginForm(HttpServletRequest req) {
//		String referer = req.getHeader("Referer");
//		req.getSession().setAttribute("prevPage", referer);
		return "login";
	}
	
	@GetMapping("/admin")
	public String admin(@RequestParam Map<String, Object> param) {
		return "admin";
	}
	
	@GetMapping("/mypage")
	public String mypage(@RequestParam Map<String, Object> param) {
		return "mypage";
	}
	
	@GetMapping("/locale")
	public String locale(@RequestParam Map<String, Object> param, Model model) {
//		param.put("locale", Locale.getDefault());
		if(param.get("lang") == null) {
			param.put("lang", Locale.getDefault());
		}
		model.addAttribute("lang", param.get("lang"));
		return "locale";
	}
	
	
	/************************************************************/
	// Action
	/************************************************************/
	@GetMapping("/logout")
    public String getLogoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login";
    }
	
	@PostMapping("/saveCity")
	public String saveCity(City city, Model model) {
		cityRepository.save(city);
		model.addAttribute("city", city);		
		return "list";
	}
	
	@PostMapping("/api/saveCity")
	public ResponseEntity<City> saveCityApi(@RequestBody City city, Model model) {
		City res = cityRepository.save(city);
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/saveMember")
	public String create(Member member) {
		MemberRole role = new MemberRole();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setUpw(passwordEncoder.encode(member.getUpw()));
		role.setRoleName(MemberRole.MemberRoleType.USER);
		member.setRoles(Arrays.asList(role));
		memberRepository.save(member);
		return "login";
	}
	
}
