package com.onetwocm.application.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onetwocm.application.data.jpa.domain.Member;
import com.onetwocm.application.data.jpa.service.MemberRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		Optional<Member> findUser = Optional.ofNullable(memberRepository.findByUid(uid));
		if(!findUser.isPresent()) {
			throw new UsernameNotFoundException("not found user");
		}
		return new SecurityMember(findUser.get());
	}
}