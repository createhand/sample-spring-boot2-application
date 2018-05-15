package com.onetwocm.application.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
        super();
    }

    /* ******************************************************************* */
    /*  GENERAL CONFIGURATION ARTIFACTS                                    */
    /*  Static Resources, i18n Messages, Formatters (Conversion Service)   */
    /* ******************************************************************* */

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    		"classpath:/resources/","classpath:/static/", "classpath:/messages/"
    		};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // / (root): `/` 로 진입했을 때 login 페이지 표시
//        registry.addViewController("/").setViewName("login");

        // login: 로그인 페이지
//        registry.addViewController("/login").setViewName("login");

//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    // messages properties
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        return messageSource;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
//        registry.addFormatter(varietyFormatter());
        registry.addFormatter(dateFormatter());
    }

//    @Bean
//    public VarietyFormatter varietyFormatter() {
//        return new VarietyFormatter();
//    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }
    
    //locale config
    @Bean
    public LocaleResolver localeResolver(){
           SessionLocaleResolver localeResolver = new SessionLocaleResolver();
           localeResolver.setDefaultLocale(Locale.KOREA);
           return  localeResolver;
      }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }

}
