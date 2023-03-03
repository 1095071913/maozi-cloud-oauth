package com.maozi.sso.oauth.api.impl.rest.v1;

import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.maozi.factory.result.AbstractBaseResult;
import com.maozi.sso.oauth.api.impl.OauthTokenServiceImpl;
import com.maozi.sso.oauth.api.rest.v1.OauthTokenServiceRestV1;

@Service
@RestController
public class OauthTokenServiceRestImpl extends OauthTokenServiceImpl implements OauthTokenServiceRestV1{

	@Override
	public AbstractBaseResult<DefaultOAuth2AccessToken> getToken(Map<String, String> parameters) {
		return success(superGetToken(parameters, null));
	}

	@Override
	public AbstractBaseResult<Map> checkToken(String token) {
		return success(superCheckToken(token));
	}

	@Override
	public AbstractBaseResult<Void> destroyToken(String token) {
		superDestroyToken(token);
		return success(null);
	}

}