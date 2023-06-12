package com.maozi.sso.oauth.api.impl.rest.v1;

import java.util.Map;

import com.maozi.base.annotation.RestService;
import com.maozi.common.result.AbstractBaseResult;
import com.maozi.sso.oauth.api.impl.OauthTokenServiceImpl;
import com.maozi.sso.oauth.api.rest.v1.RestOauthTokenServiceV1;
import com.maozi.sso.oauth.dto.platform.dto.OauthToken;
import com.maozi.sso.oauth.dto.platform.param.ClientParam;
import com.maozi.sso.oauth.dto.platform.param.TokenInfoParam;

@RestService
public class RestOauthTokenServiceImplV1 extends OauthTokenServiceImpl implements RestOauthTokenServiceV1 {

	@Override
	public AbstractBaseResult<OauthToken> restGet(TokenInfoParam param) throws Exception {
		return success(get(param));
	}

	@Override
	public AbstractBaseResult<OauthToken> restRefresh(String refreshToken,ClientParam param) throws Exception {
		return success(refresh(refreshToken,param));
	}

	@Override
	public AbstractBaseResult<Map> restCheck(String token) {
		return success(check(token));
	}

	@Override
	public AbstractBaseResult<Void> restDestroy(String token) {
		
		destroy(token);
		
		return success(null);
		
	}

}