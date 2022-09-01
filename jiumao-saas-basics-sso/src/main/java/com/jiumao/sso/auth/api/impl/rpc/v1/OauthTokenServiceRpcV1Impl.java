package com.jiumao.sso.auth.api.impl.rpc.v1;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

import com.jiumao.factory.result.AbstractBaseResult;
import com.jiumao.sso.api.OauthTokenServiceRpcV1;
import com.jiumao.sso.auth.api.impl.OauthTokenServiceImpl;

public class OauthTokenServiceRpcV1Impl extends OauthTokenServiceImpl implements OauthTokenServiceRpcV1 {
	
	@Resource
	private CheckTokenEndpoint checkTokenEndpoint;
	
	@Resource
	public ConsumerTokenServices consumerTokenServices;
	
	@Override
	public AbstractBaseResult checkToken(String token) {
		
		Map checkTokenData = null;
		try {checkTokenData = checkTokenEndpoint.checkToken(token);}catch (Exception e) {  
			return error(code(3));     
		}
		return success(checkTokenData);
		
	}

	
	@Override
	public AbstractBaseResult getToken(Map<String, String> parameters) {
		return getToken(parameters,null);
	}
	
	
	@Override
	public AbstractBaseResult<DefaultOAuth2AccessToken> getToken(Map<String, String> parameters,Map<String,Object> userInfos) {
		return success(super.getSuperToken(parameters, userInfos));
	}


	@Override
	public AbstractBaseResult<Void> destroyToken(String token) {
		consumerTokenServices.revokeToken(token);
		return success(null);
	}

}
