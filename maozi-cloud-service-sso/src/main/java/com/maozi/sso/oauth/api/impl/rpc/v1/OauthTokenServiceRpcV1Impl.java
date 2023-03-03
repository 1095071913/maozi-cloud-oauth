package com.maozi.sso.oauth.api.impl.rpc.v1;

import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

import com.maozi.factory.result.AbstractBaseResult;
import com.maozi.sso.oauth.api.impl.OauthTokenServiceImpl;
import com.maozi.sso.oauth.api.rpc.v1.OauthTokenServiceRpcV1;

@DubboService
public class OauthTokenServiceRpcV1Impl extends OauthTokenServiceImpl implements OauthTokenServiceRpcV1 {
	
	@Override
	public AbstractBaseResult checkToken(String token) {
		return success(superCheckToken(token));
	}

	
	@Override
	public AbstractBaseResult getToken(Map<String, String> parameters) {
		return getToken(parameters,null);
	}
	
	
	@Override
	public AbstractBaseResult<DefaultOAuth2AccessToken> getToken(Map<String, String> parameters,Map<String,Object> userInfos) {
		return success(superGetToken(parameters, userInfos));
	}


	@Override
	public AbstractBaseResult<Void> destroyToken(String token) {
		superDestroyToken(token);
		return success(null);
	}

}
