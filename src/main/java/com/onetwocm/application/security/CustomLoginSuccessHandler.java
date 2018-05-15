package com.onetwocm.application.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
    	Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {
        	
        	List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        	session.setAttribute("authorities", authorities);
        	for(GrantedAuthority authority : authorities) {
        		System.out.println("User Authority : " + authority.getAuthority());
        	}
        	
        	super.onAuthenticationSuccess(request, response, authentication);
        	
//            String redirectUrl = (String) session.getAttribute("prevPage");
//            if (redirectUrl != null) {
//                session.removeAttribute("prevPage");
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            } else {
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}