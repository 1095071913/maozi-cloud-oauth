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

package com.maozi.sso.oauth.api.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.dubbo.rpc.RpcContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.maozi.factory.BaseResultFactory;
import com.maozi.factory.result.error.exception.BusinessResultException;
import com.maozi.sso.oauth.api.OauthTokenService;

/**
 * 
 * Specifications：功能
 * 
 * Author：彭晋龙
 * 
 * Creation Date：2021-12-18:16:32:34
 *
 * Copyright Ownership：xiao mao zi
 * 
 * Agreement That：Apache 2.0
 * 
 */

public class OauthTokenServiceImpl extends BaseResultFactory implements OauthTokenService {
	
	public OauthTokenServiceImpl() {
		setServiceName("oauthToken-rpc");
	}

	@Resource
	public TokenStore tokenStore;

	@Resource
	private TokenEndpoint tokenEndpoint;
	
	@Resource
	private CheckTokenEndpoint checkTokenEndpoint;

	@Resource
	private ClientDetailsService clientDetailsService;
	
	@Resource
	public ConsumerTokenServices consumerTokenServices;

	@Resource
	private AuthorizationServerSecurityConfiguration authorizationServerSecurityConfiguration;
	
	
	public DefaultOAuth2AccessToken superGetToken(Map<String, String> parameters,Map<String,Object> userInfos) {
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(parameters.get("client_id"), parameters.get("client_secret"));
		
		Authentication authenticate = null;
		
		try {authenticate = authorizationServerSecurityConfiguration.authenticationManagerBean().authenticate(authRequest);}catch (Exception e) {
			throw new BusinessResultException(error(code(1001)));
		}
		
		RpcContext.getContext().set("userInfos", userInfos);
		
		ResponseEntity<OAuth2AccessToken> accessTokenResult = null;
		
		try {accessTokenResult = tokenEndpoint.getAccessToken(authenticate, parameters);} catch (Exception e) {
			throw new BusinessResultException(error(code(1002)));
		}
		
		DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) accessTokenResult.getBody();
		
		OAuth2Authentication readAuthentication = tokenStore.readAuthentication(oAuth2AccessToken.getValue());

		ClientDetails client = clientDetailsService.loadClientByClientId(readAuthentication.getOAuth2Request().getClientId());
		
		Long tokenTime = (oAuth2AccessToken.getExpiration().getTime()-System.currentTimeMillis())/1000;
		
		if(tokenTime<=client.getAccessTokenValiditySeconds()*1000L-2){
			
			oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (client.getAccessTokenValiditySeconds()*1000L)));
			tokenStore.storeAccessToken(oAuth2AccessToken,readAuthentication);
			
			DefaultExpiringOAuth2RefreshToken oAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken)oAuth2AccessToken.getRefreshToken();
			
			if(isNotNull(oAuth2RefreshToken)) {
				oAuth2AccessToken.setRefreshToken(new DefaultExpiringOAuth2RefreshToken(oAuth2RefreshToken.getValue(),new Date(System.currentTimeMillis() + (client.getRefreshTokenValiditySeconds()*1000L))));
				tokenStore.storeRefreshToken(oAuth2RefreshToken, readAuthentication);
			}
			
		}
		
		return oAuth2AccessToken;
	}
	
	public Map superCheckToken(String token) {
		
		Map checkTokenData = null;
		
		try {checkTokenData = checkTokenEndpoint.checkToken(token);}catch (Exception e) {  
			throw new BusinessResultException(error(code(3)));
		}
		
		return checkTokenData;
		
	}
	
	public void superDestroyToken(String token) {
		consumerTokenServices.revokeToken(token);
	}

}
