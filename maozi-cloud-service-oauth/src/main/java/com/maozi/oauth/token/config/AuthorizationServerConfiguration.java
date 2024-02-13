
/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.maozi.oauth.token.config;

import com.maozi.oauth.client.api.ClientService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	@Resource
	private TokenStore tokenStore;

	@Resource 
	private UserDetailsService userDetailsService;
	
	@Resource
	private AuthenticationManager authenticationManager;
	
	@Resource
	private RedisConnectionFactory redisConnectionFactory;
	
	@Resource
	@Qualifier("clientServiceImpl")
	private ClientService clientService;
	
//	@Resource
//	private org.springframework.security.oauth2.provider.token.UserAuthenticationConverter userAuthenticationConverter;
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientService);
	}
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
//		DefaultAccessTokenConverter defaultAccessTokenConverter=new DefaultAccessTokenConverter();
//        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);  
        
		endpoints
				.tokenGranter(new CompositeTokenGranter(getDefaultTokenGranters(endpoints.getTokenServices(),endpoints.getClientDetailsService(),endpoints.getOAuth2RequestFactory(),endpoints.getAuthorizationCodeServices())))
				.authenticationManager(authenticationManager)
				.tokenStore(tokenStore)
				.userDetailsService(userDetailsService)
				.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);
//				.accessTokenConverter(defaultAccessTokenConverter);
	}
	
	private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,AuthorizationCodeServices authorizationCodeServices) {

		List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
		
		tokenGranters.add(new ResourceOwnerSmsTokenGranter(tokenServices,clientDetailsService,requestFactory,userDetailsService));
		
		tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService,requestFactory));
		
		tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
		
		ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory);
		tokenGranters.add(implicit);
		tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
		
		if (authenticationManager != null) {
			tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,clientDetailsService, requestFactory));
		}
		
		return tokenGranters;
		
	}
	

}