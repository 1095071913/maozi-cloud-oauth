package com.zhongshi.sso.config;

import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Service;
import com.zhongshi.sso.OauthUserDetails;

@Service
public class UserAuthenticationConverter extends DefaultUserAuthenticationConverter {
 
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
    	
    	Map<String, Object> convertUserAuthentication = (Map<String, Object>) super.convertUserAuthentication(authentication);
    	
    	UsernamePasswordAuthenticationToken authenticationNew = (UsernamePasswordAuthenticationToken) authentication;
    	
    	OauthUserDetails oauthUserDetails = (OauthUserDetails) authenticationNew.getPrincipal();
    	
    	Map<String,Map<String,Object>> userInfos = oauthUserDetails.getUserInfos();
    	
    	userInfos.get("com.zhongshi.user.SsoUserInfo").remove("password");
    	
		convertUserAuthentication.put("user_infos", userInfos);
    	
        return convertUserAuthentication; 
    }
}
