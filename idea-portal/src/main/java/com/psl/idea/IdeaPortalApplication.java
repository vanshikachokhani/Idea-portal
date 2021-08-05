package com.psl.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.psl.idea.filters.AuthFilter;

@SpringBootApplication
public class IdeaPortalApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(IdeaPortalApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/loggedin/*");
		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
	   FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
	   UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	   CorsConfiguration config = new CorsConfiguration();
	   config.addAllowedOrigin("*");
	   config.addAllowedHeader("*");
	   config.addAllowedMethod("*");
	   source.registerCorsConfiguration("/**", config);
	   registrationBean.setFilter(new CorsFilter(source));
	   registrationBean.setOrder(0);
	   return registrationBean;
	}

}
