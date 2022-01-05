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

package com.zhongshi.sso.config;

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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhongshi.factory.BaseResultFactory;
import com.zhongshi.factory.result.AbstractBaseResult;
import com.zhongshi.sso.OauthUserDetails;
import com.zhongshi.tool.MapperUtils;
import com.zhongshi.user.rpc.api.UserServiceRpc;

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


public class UserDetailsServiceConfig extends BaseResultFactory implements UserDetailsService {
	
	@DubboReference
	private UserServiceRpc userServiceRpc;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		RpcContext rpcClient = RpcContext.getContext();
		
		AbstractBaseResult<Map<String,Object>> userDetailsResult = userServiceRpc.rpcSelectUserOne(username);
		
		if (userDetailsResult.isSuccess()) {
			
			Map<String, Object> userData = userDetailsResult.getData();
			
			List<GrantedAuthority> grantedAuthorities = Lists.newCopyOnWriteArrayList();
			
//			Long permissionId = (Long) userData.get("permissionId");
//			
//			if(!StringUtils.isEmpty(permissionId)) {
//				
//				AbstractBaseResult<List<PermissionDo>> userPermissions = permissionServiceRpc.queryUserPermission(permissionId);
//				if (userPermissions.isSuccess()) { 
//					
//					List<PermissionDo> data = userPermissions.getData();
//					data.forEach(userPermission -> {
//						if (userPermission != null && userPermission.getEnname() != null) {
//							grantedAuthorities.add(new SimpleGrantedAuthority(userPermission.getEnname()));
//						}
//					});
//					
//				} 
//			}
			
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM"));
			
			HttpServletRequest request = getRequest();
			
			Map<String,Object> userInfos=Maps.newHashMap();
			
			String userInfoJson=isNull(request)?rpcClient.getAttachment("userInfos"):(String)request.getParameter("userInfos");
			
			if(!isNull(userInfoJson)) {
				try {userInfos=MapperUtils.json2mapDeeply(userInfoJson);} catch (Exception e) {
					BaseResultFactory.getStackTrace(e);
				}
			}
			
			userInfos.put("com.zhongshi.user.SsoUserInfo",userData);
			
			return new OauthUserDetails((String)userData.get("username"), (String)userData.get("password"), grantedAuthorities,userInfos);
			
		} 
		
		return null;
		
	}


}
