/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.maozi.sso.oauth.config;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.maozi.factory.BaseResultFactory;
import com.maozi.factory.result.error.exception.BusinessResultException;
import com.maozi.sso.OauthUserDetails;
import com.maozi.sso.info.SystemUser;
import com.maozi.tool.MapperUtils;
import com.maozi.user.user.api.rpc.v1.UserServiceRpcV1;

/**
 * 
 * 功能说明：UserDetails认证配置
 * 
 * 功能作者：彭晋龙 ( 联系方式QQ/微信：1095071913 )
 *
 * 创建日期：2019-10-12 ：3:09:00
 *
 * 版权归属：蓝河团队
 *
 * 协议说明：Apache2.0（ 文件顶端 ）
 * 
 */

@Service
public class UserDetailsServiceConfig extends BaseResultFactory implements UserDetailsService {

	@DubboReference
	private UserServiceRpcV1 userServiceRpcV1;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		RpcContext rpcClient = RpcContext.getContext();

		SystemUser user = null;
		
		try {user = userServiceRpcV1.rpcGetInfoPermissionByUsername(username).ifResultThrowErrorOrGetData();} catch (Exception e) {
			
			log.error(e.getLocalizedMessage());
			
			throw new BusinessResultException(500,"未知错误",500);
			
		}

		List<GrantedAuthority> grantedAuthorities = Lists.newCopyOnWriteArrayList();
 
		if(isNotNull(user.getPermissions())) {
			for (String permission : user.getPermissions()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(permission));
			}
		}
		
		user.setPermissions(null);

		HttpServletRequest request = getRequest();

		Map<String, Object> userInfos = Maps.newHashMap();

		String userInfoJson = isNull(request) ? rpcClient.getAttachment("userInfos") : (String) request.getParameter("userInfos");

		if (!isNull(userInfoJson)) {
			try {
				userInfos = MapperUtils.json2mapDeeply(userInfoJson);
			} catch (Exception e) {
				BaseResultFactory.getStackTrace(e);
			}
		}

		userInfos.put(SystemUser.class.getName(), MapperUtils.pojo2Map(user));

		return new OauthUserDetails(user.getUsername(),userServiceRpcV1.rpcGetPasswordById(user.getId()).ifResultThrowErrorOrGetData(), grantedAuthorities, userInfos);

	}

}
