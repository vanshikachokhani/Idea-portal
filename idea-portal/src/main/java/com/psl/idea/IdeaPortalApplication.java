package com.psl.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:4200",
						"http://localhost:8080",
						"https://hopeful-leavitt-8a8a98.netlify.app",
						"https://psl-ideaportal.herokuapps.com"
						);
			}
		};
	}

}
