package com.retail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class MyRetailSecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Override to disable security
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.csrf().disable();
//
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests().antMatchers("/products/admin/**").hasRole("ADMIN").and().csrf()
				.disable();
		http.httpBasic().and().authorizeRequests().antMatchers("/products/user/**").hasRole("USER").and().csrf()
		.disable();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
	}

}
