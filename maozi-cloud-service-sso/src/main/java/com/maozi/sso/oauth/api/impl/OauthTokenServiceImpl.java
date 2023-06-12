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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.maozi.common.BaseCommon;
import com.maozi.common.result.error.exception.BusinessResultException;
import com.maozi.sso.client.enums.AuthType;
import com.maozi.sso.oauth.api.OauthTokenService;
import com.maozi.sso.oauth.dto.platform.dto.OauthToken;
import com.maozi.sso.oauth.dto.platform.param.ClientParam;
import com.maozi.sso.oauth.dto.platform.param.TokenInfoParam;

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

public class OauthTokenServiceImpl extends BaseCommon implements OauthTokenService {

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
	
	@Override
	public String getServiceName() { return "oauthToken-rpc"; }
	
	@Override
	public String getAbbreviationModelName() { return "【授权】"; }
	
	public OauthToken get(String clientId,String clientSecret,AuthType type,String username,String password,String refreshTokenParam) throws Exception {
		
		try {
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(clientId, clientSecret);
			
			Authentication authenticate = authorizationServerSecurityConfiguration.authenticationManagerBean().authenticate(authRequest);
			
			Map<String, String> map = new HashMap<>() {{
				put("client_id", clientId);
				put("client_secret", clientSecret);
				put("grant_type", type.getDesc());
				put("username", username);
				put("password", password);
				put("refresh_token", refreshTokenParam);
			}};
			
			ResponseEntity<OAuth2AccessToken> accessTokenResult = tokenEndpoint.getAccessToken(authenticate, map);
			
			DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) accessTokenResult.getBody();
			
			DefaultExpiringOAuth2RefreshToken refreshToken = (DefaultExpiringOAuth2RefreshToken) accessToken.getRefreshToken();
			
			return new OauthToken(accessToken.getValue(),refreshToken.getValue(),(accessToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000L,(refreshToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000L);
			
		} catch (InvalidGrantException e) {throw new BusinessResultException(getAbbreviationModelName()+"密码错误");
		
		} catch (InternalAuthenticationServiceException e) {throw new BusinessResultException(e.getLocalizedMessage());}
		
		
	}
	
	public OauthToken get(TokenInfoParam param) throws Exception {
		return get(param.getClientId(),param.getClientSecret(),AuthType.password,param.getUsername(),param.getPassword(),null);
	}
	
	public OauthToken refresh(String refreshToken,ClientParam param) throws Exception {
		return get(param.getClientId(),param.getClientSecret(),AuthType.refreshToken,null,null,refreshToken);
	}
	
	public Map check(String token) {
		
		Map checkTokenData = null;
		
		try {checkTokenData = checkTokenEndpoint.checkToken(token);}catch (Exception e) {  
			throw new BusinessResultException(error(code(3)));
		}
		
		return checkTokenData;
		
	}
	
	public void destroy(String token) {
		consumerTokenServices.revokeToken(token);
	}

}
