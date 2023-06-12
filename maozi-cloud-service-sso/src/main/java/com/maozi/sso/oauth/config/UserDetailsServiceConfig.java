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

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.maozi.common.BaseCommon;
import com.maozi.system.user.api.rpc.v1.RpcUserInfoServiceV1;
import com.maozi.system.user.api.rpc.v1.RpcUserServiceV1;

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
public class UserDetailsServiceConfig extends BaseCommon implements UserDetailsService {
	
	@DubboReference
	private RpcUserServiceV1 rpcUserService;

	@DubboReference
	private RpcUserInfoServiceV1 rpcUserInfoService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<String> permissions = rpcUserService.rpcGetPermissionsByUsername(username).getResultDataThrowError();

		List<GrantedAuthority> grantedAuthorities = Lists.newCopyOnWriteArrayList();
		
		permissions.stream().forEach((permission)->{
			grantedAuthorities.add(new SimpleGrantedAuthority(permission));
		});

		return new User(username,rpcUserService.rpcGetPasswordByUsername(username).getResultDataThrowError(), grantedAuthorities);

	}

}
