package com.maozi.oauth.token.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class ResourceOwnerSmsTokenGranter extends AbstractTokenGranter {
	
    private static final String GRANT_TYPE = "sms";
    
    private UserDetailsService userDetailsService;

    public ResourceOwnerSmsTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,UserDetailsService userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
    	
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        
        String username = (String) parameters.get("username");
        
        String vxcode = (String) parameters.get("vxcode");
        
        //todo vxcode get vxAccessToken get vxid
        
        UserDetails user = userDetailsService.loadUserByUsername(username);

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, client.getClientId(), user.getAuthorities());

        OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
        
        return new OAuth2Authentication(storedOAuth2Request, authentication);
        
    }

}