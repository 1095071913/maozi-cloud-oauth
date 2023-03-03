package com.maozi.sso.oauth.config;

import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Service;

import com.maozi.sso.OauthUserDetails;

@Service
public class UserAuthenticationConverter extends DefaultUserAuthenticationConverter {
 
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
    	
    	Map<String, Object> convertUserAuthentication = (Map<String, Object>) super.convertUserAuthentication(authentication);
    	
    	AbstractAuthenticationToken authenticationNew = (AbstractAuthenticationToken) authentication;
    	
    	OauthUserDetails oauthUserDetails = (OauthUserDetails) authenticationNew.getPrincipal();
    	
    	Map<String,Map<String,Object>> userInfos = oauthUserDetails.getUserInfos();
    	
		convertUserAuthentication.put("user_infos", userInfos);
    	
        return convertUserAuthentication; 
    }
}
