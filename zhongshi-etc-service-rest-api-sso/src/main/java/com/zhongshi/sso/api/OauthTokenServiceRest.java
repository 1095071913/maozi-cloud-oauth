package com.zhongshi.sso.api;

import java.security.Principal;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhongshi.factory.result.AbstractBaseResult;
import io.swagger.annotations.Api;

@Api("操作Token接口")
@RestController
public interface OauthTokenServiceRest {

	@GetMapping("/user/role")
    public AbstractBaseResult<Collection<GrantedAuthority>> userInfo(OAuth2Authentication principal);
	
	@RequestMapping("/user")
    public Principal user(Principal principal);
	
}
