package com.idx.imds.api.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.idx.imds.api.controller.configuration.security.BasicAuthorizationFilter;
import com.idx.imds.api.controller.configuration.security.CustomAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetails() {
		return new CustomUserDetailsService();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public BasicAuthorizationFilter basicAuthorizationFilter() throws Exception {
	
		return new BasicAuthorizationFilter(authenticationManager());
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = userDetails();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.cors().and().csrf().disable().authorizeRequests()
		 .antMatchers("/renew/**").permitAll()
		 .antMatchers("/topics").permitAll()
         .antMatchers("/actuator/**", "/token/**","/ldap/**").authenticated()
         .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
         .and()
//         .addFilter(new JwtAuthenticationFilter(authenticationManager()))
         .addFilter(basicAuthorizationFilter())
         // this disables session creation on Spring Security
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	@Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

}
