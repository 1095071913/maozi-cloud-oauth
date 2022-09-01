package com.jiumao.sso.api.v1.rest;

import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiumao.factory.result.AbstractBaseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "【第三方】Token接口")
@RequestMapping("/oauth/token")	
@RestController
public interface OauthTokenServiceRest {
	
	@GetMapping("/get")	
	@ApiOperation(value = "获取Token")
	AbstractBaseResult<DefaultOAuth2AccessToken> getToken(@RequestParam Map<String, String> parameters);
	
}
