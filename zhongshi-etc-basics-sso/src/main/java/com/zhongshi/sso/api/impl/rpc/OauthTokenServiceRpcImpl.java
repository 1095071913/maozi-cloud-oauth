package com.zhongshi.sso.api.impl.rpc;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import com.zhongshi.factory.BaseResultFactory;
import com.zhongshi.factory.result.AbstractBaseResult;
import com.zhongshi.factory.result.code.CodeAttribute;
import com.zhongshi.factory.result.code.CodeHashMap;
import com.zhongshi.sso.api.OauthTokenServiceRpc;

public class OauthTokenServiceRpcImpl extends BaseResultFactory implements OauthTokenServiceRpc {
	
	
	static {

		codes(new CodeHashMap("rpc-sso") {

			{

				this.put(new CodeAttribute(1001, "client无效"));
				
				this.put(new CodeAttribute(1002, "用户或密码错误"));

			}

		});

	}
	
	@Resource
	public TokenStore tokenStore;
	
	@Resource
	private TokenEndpoint tokenEndpoint;
	
	@Resource
	private CheckTokenEndpoint checkTokenEndpoint;
	
	@Resource
	private ClientDetailsService clientDetailsService;
	
	@Resource
	private AuthorizationServerSecurityConfiguration authorizationServerSecurityConfiguration;
	
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
	public AbstractBaseResult getToken(Map<String, String> parameters,Map<String,Object> userInfos) {
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(parameters.get("client_id"), parameters.get("client_secret"));
		
		Authentication authenticate = null;
		
		try {authenticate = authorizationServerSecurityConfiguration.authenticationManagerBean().authenticate(authRequest);}catch (Exception e) {
			return error(code(1001));
		}
		
		RpcContext.getContext().set("userInfos", userInfos);
		
		ResponseEntity<OAuth2AccessToken> accessTokenResult = null;
		
		try {accessTokenResult = tokenEndpoint.getAccessToken(authenticate, parameters);} catch (Exception e) {
			if("Bad credentials".equals(e.getMessage())) {
				return error(code(1002));
			}  
			return error(code(800)); 
		}
		
		DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) accessTokenResult.getBody();
		
		DefaultExpiringOAuth2RefreshToken oAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken)oAuth2AccessToken.getRefreshToken();
		
		OAuth2Authentication readAuthentication = tokenStore.readAuthentication(oAuth2AccessToken.getValue());

		ClientDetails client = clientDetailsService.loadClientByClientId(readAuthentication.getOAuth2Request().getClientId());
		
		Long tokenTime = (oAuth2AccessToken.getExpiration().getTime()-System.currentTimeMillis())/1000;
		
		if(tokenTime<=client.getAccessTokenValiditySeconds()*1000L-2) {
			
			oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (client.getAccessTokenValiditySeconds()*1000L)));
			
			oAuth2RefreshToken.setExpiration(new Date(System.currentTimeMillis() + (client.getRefreshTokenValiditySeconds()*1000L)));
			
			tokenStore.storeAccessToken(oAuth2AccessToken,readAuthentication);
			
			tokenStore.storeRefreshToken(oAuth2RefreshToken, readAuthentication);
		}
		
		return success(oAuth2AccessToken);
	}



	@Override
	public AbstractBaseResult destroyToken(String token) {
		OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
		tokenStore.removeAccessToken(oAuth2AccessToken);
		tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
		return success("退出成功");
	}

}
