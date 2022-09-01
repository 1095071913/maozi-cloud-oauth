package com.jiumao.sso.auth.api.impl.rest.v1;

import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

import com.jiumao.factory.result.AbstractBaseResult;
import com.jiumao.sso.api.v1.rest.OauthTokenServiceRest;
import com.jiumao.sso.auth.api.impl.OauthTokenServiceImpl;

public class OauthTokenServiceRestImpl extends OauthTokenServiceImpl implements OauthTokenServiceRest{

	@Override
	public AbstractBaseResult<DefaultOAuth2AccessToken> getToken(Map<String, String> parameters) {
		return success(getSuperToken(parameters, null));
	}

}