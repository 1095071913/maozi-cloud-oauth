package com.maozi.oauth.token.config;

import com.maozi.oauth.properties.ApiWhitelistProperties;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Resource 
	private UserDetailsService userDetailsService;
	
	@Resource
	public ApiWhitelistProperties apiWhitelistProperties;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
		passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
		return passwordEncoder;
	} 
	
	@Override
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
    public void configure(WebSecurity web) throws Exception {
		
		apiWhitelistProperties.getDefaultWitelist().addAll(apiWhitelistProperties.getWhitelist());
		
        web.ignoring().antMatchers(apiWhitelistProperties.getDefaultWitelist().toArray(new String[apiWhitelistProperties.getDefaultWitelist().size()]));
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and().authorizeRequests().anyRequest().authenticated();
		
	}
	

}