package com.zhongshi.sso.api.impl.rest;

import java.security.Principal;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import com.zhongshi.factory.BaseResultFactory;
import com.zhongshi.factory.result.AbstractBaseResult;
import com.zhongshi.sso.api.OauthTokenServiceRest;

public class OauthTokenServiceRestImpl extends BaseResultFactory implements OauthTokenServiceRest{

	@Override
	public AbstractBaseResult<Collection<GrantedAuthority>> userInfo(OAuth2Authentication oauthAuthentication) {
		return success(oauthAuthentication.getAuthorities());
	}
	
	@Override
	public Principal user(Principal principal) {
		return principal;
	}

}
