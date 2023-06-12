package com.maozi.sso.oauth.api.impl.rpc.v1;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.maozi.common.result.AbstractBaseResult;
import com.maozi.sso.client.api.impl.ClientServiceImpl;
import com.maozi.sso.oauth.api.impl.OauthTokenServiceImpl;
import com.maozi.sso.oauth.api.rpc.v1.RpcOauthTokenServiceV1;
import com.maozi.sso.oauth.dto.platform.dto.OauthToken;
import com.maozi.sso.oauth.dto.platform.param.ClientParam;
import com.maozi.sso.oauth.dto.platform.param.ClientUserParam;
import com.maozi.sso.oauth.dto.platform.param.TokenInfoParam;

@DubboService
public class RpcOauthTokenServiceImplV1 extends OauthTokenServiceImpl implements RpcOauthTokenServiceV1 {
	
	@Resource(name = "clientServiceImpl")
	private ClientServiceImpl clientServiceImpl;
	
	@Override
	public AbstractBaseResult<OauthToken> rpcGet(TokenInfoParam param) throws Exception {
		
		OauthToken oauthToken = get(param);
		
		oauthToken.setRefreshToken(null);
		oauthToken.setRefreshTokenExpiresTime(null);
		
		return success(oauthToken);
		
	}
	
	@Override
	public AbstractBaseResult<OauthToken> rpcRefresh(String token,ClientParam param) throws Exception {
		
		DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) tokenStore.readAccessToken(token);
		
		OauthToken oauthToken = refresh(accessToken.getRefreshToken().getValue(),param);
		
		oauthToken.setRefreshToken(null);
		oauthToken.setRefreshTokenExpiresTime(null);
		
		return success(oauthToken);
		
	}
	
	@Override
	public AbstractBaseResult<Map> rpcCheck(String token) {
		return success(check(token));
	}

	@Override
	public AbstractBaseResult<Void> rpcDestroy(String token) {
		
		destroy(token);
		
		return success(null);
		
	}

	@Override
	public AbstractBaseResult<Void> rpcDestroy(ClientUserParam param) {
		
		String clientId = clientServiceImpl.getClientId(param.getClientId());
		
		Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, param.getUsername());
		
		tokens.parallelStream().forEach(token->{
			destroy(token.getValue());
		});
		
		return success(null);
		
	}

	@Override
	public AbstractBaseResult<Void> rpcDestroys(List<ClientUserParam> params) {
		
		collectionIsEmptyThrowError(params, "【客户端】列表");
		
		Set<Long> clientIds = params.stream().map(ClientUserParam::getClientId).collect(Collectors.toSet());
		
		Map<Long, String> clientsMap = clientServiceImpl.getClientIdsMap(clientIds);
		
		params.parallelStream().forEach(param -> {
			
			Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientsMap.get(param.getClientId()), param.getUsername());
			
			tokens.parallelStream().forEach(token->{
				destroy(token.getValue());
			});
			
		});
		
		return success(null);
		
	}

}
