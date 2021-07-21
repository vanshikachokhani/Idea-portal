package com.psl.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.psl.idea.filters.AuthFilter;

@SpringBootApplication
public class IdeaPortalApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(IdeaPortalApplication.class, args);}
	
		@Bean
		public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
			FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
			AuthFilter authFilter = new AuthFilter();
			registrationBean.setFilter(authFilter);
			registrationBean.addUrlPatterns("/api/loggedin/*");
			return registrationBean;
	}

}
