package com.nacho.neo4jdemo.config;

import static org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest.to;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.requestMatchers(to(HealthEndpoint.class, MetricsEndpoint.class)).permitAll()
			.and()
			.csrf().disable()
			.httpBasic();
	}
}
