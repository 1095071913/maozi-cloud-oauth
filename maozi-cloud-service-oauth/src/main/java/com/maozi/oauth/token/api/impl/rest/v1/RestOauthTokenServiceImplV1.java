package com.maozi.oauth.token.api.impl.rest.v1;

import com.maozi.base.annotation.RestService;
import com.maozi.common.result.AbstractBaseResult;
import com.maozi.oauth.token.api.impl.OauthTokenServiceImpl;
import com.maozi.oauth.token.api.rest.v1.RestOauthTokenServiceV1;
import com.maozi.oauth.token.dto.platform.dto.OauthToken;
import com.maozi.oauth.token.dto.platform.param.ClientParam;
import com.maozi.oauth.token.dto.platform.param.TokenInfoParam;
import java.util.Map;

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