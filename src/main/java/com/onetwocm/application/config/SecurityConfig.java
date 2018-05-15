package com.onetwocm.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.onetwocm.application.constant.BizConst;
import com.onetwocm.application.data.jpa.domain.MemberRole;
import com.onetwocm.application.security.CustomLoginFailureHandler;
import com.onetwocm.application.security.CustomLoginSuccessHandler;
import com.onetwocm.application.security.CustomUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
//	@Override
//	public void configure(WebSecurity web) throws Exception
//	{
//		web.ignoring().antMatchers(BizConst.Security.ANT_PATTERNS_FOR_WEB_STATIC_PERMIT_ALL);
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf().ignoringAntMatchers(BizConst.Security.ANT_PATTERNS_FOR_API_PERMIT_TO_USER)
				.and()
//			.csrf().disable()
			.sessionManagement()
			//중복로그인체크
			.maximumSessions(1).expiredUrl("/").and()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
			.authorizeRequests()
			//Role에 따른 접근제어
			.antMatchers(BizConst.Security.ANT_PATTERNS_FOR_PERMIT_TO_MANAGER)
				.hasAnyRole(MemberRole.MemberRoleType.AGENT.name(), MemberRole.MemberRoleType.ADMIN.name())
			.antMatchers(BizConst.Security.ANT_PATTERNS_FOR_PERMIT_TO_USER).authenticated()
			.antMatchers("/**").permitAll()
			.and().formLogin()
			.loginPage(BizConst.Security.loginUrl)
//			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/")
			.successHandler(new CustomLoginSuccessHandler(BizConst.Security.mypageUrl))
			.failureHandler(new CustomLoginFailureHandler())
//    			.failureUrl("/login")
    			.and()
    			.logout().logoutUrl(BizConst.Security.logoutUrl);
    	;
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
