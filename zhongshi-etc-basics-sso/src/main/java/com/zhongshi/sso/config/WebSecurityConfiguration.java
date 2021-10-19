package com.zhongshi.sso.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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

/**
 * 
 * 	功能说明：SpringSecurity配置
 * 
 *	功能作者：彭晋龙 ( 联系方式QQ/微信：1095071913 )
 *
 *	创建日期：2019-10-05 ：13:09:00
 *
 *	版权归属：蓝河团队
 *
 *	协议说明：Apache2.0（ 文件顶端 ）
 *
 */

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
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
		
		.and().authorizeRequests().anyRequest().authenticated()
		
		.and().requestMatchers().antMatchers("/oauth/authorize");
		
	}
	

}