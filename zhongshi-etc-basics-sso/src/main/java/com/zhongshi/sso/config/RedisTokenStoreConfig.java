package com.zhongshi.sso.config;

import java.util.Date;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenStoreConfig extends RedisTokenStore {
	

	private ClientDetailsService clientDetailsService;
	
	
	public RedisTokenStoreConfig(RedisConnectionFactory connectionFactory, ClientDetailsService clientDetailsService) {
		
		super(connectionFactory);
		
		this.clientDetailsService = clientDetailsService;
		
		setPrefix("zhongshi-etc-sso:token:");
		
	}
	

	@Override 
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		
		OAuth2Authentication result = readAuthentication(token.getValue()); 
		
		if (result != null) {    
			
			DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) token;
			
			DefaultExpiringOAuth2RefreshToken oAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken)oAuth2AccessToken.getRefreshToken();
			
			Long tokenTime = (oAuth2AccessToken.getExpiration().getTime()-System.currentTimeMillis())/1000;
			
			if(tokenTime <= 60 * 60 * 3) {
				
				ClientDetails client = clientDetailsService.loadClientByClientId(result.getOAuth2Request().getClientId());
				 
				oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (client.getAccessTokenValiditySeconds()*1000L)));
				
				oAuth2RefreshToken.setExpiration(new Date(System.currentTimeMillis() + (client.getRefreshTokenValiditySeconds()*1000L)));
				 
				storeAccessToken(token, result);
				
				storeRefreshToken(token.getRefreshToken(), result);
			}
			
		}
		return result;
	}
	
	
}
