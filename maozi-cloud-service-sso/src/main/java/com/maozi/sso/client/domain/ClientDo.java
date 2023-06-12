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

package com.maozi.sso.client.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.alibaba.nacos.shaded.com.google.common.collect.Sets;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.maozi.base.AbstractBaseDomain;
import com.maozi.common.BaseCommon;
import com.maozi.sso.client.enums.AuthType;
import com.maozi.sso.client.handler.AuthTypesHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**	
 * 
 *  Specifications：功能
 * 
 *  Author：彭晋龙
 * 
 *  Creation Date：2021-12-18:16:32:34
 *
 *  Copyright Ownership：xiao mao zi
 * 
 *  Agreement That：Apache 2.0
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@TableName(value = "oauth_client_details",autoResultMap = true)
@TableComment("客户端")
public class ClientDo extends AbstractBaseDomain implements ClientDetails {
	
	@Index
	@Column(value = "client_id",comment = "客户端Id")
	private String clientId;

	@TableField(typeHandler = AuthTypesHandler.class)
	@Column(value = "auth_types",defaultValue = "\"[4]\"",type = MySqlTypeConstant.VARCHAR)
	private Set<AuthType> authTypes;
	
	@Column(value = "remark",comment = "备注")
	private String remark;

	@TableField(typeHandler = JacksonTypeHandler.class)
	@Column(value = "resource_ids",defaultValue = "\"[\\\"backend-resources\\\"]\"",type = MySqlTypeConstant.VARCHAR)
	private Set<String> resourceIds;
	
	@Column(value = "client_secret",comment = "客户端密钥")
	private String clientSecret;

	@TableField(typeHandler = JacksonTypeHandler.class)
	@Column(value = "scope",defaultValue = "\"[\\\"backend\\\"]\"",comment = "范围",type = MySqlTypeConstant.VARCHAR)
	private Set<String> scope;

	@TableField(typeHandler = JacksonTypeHandler.class)
	@Column(value = "registered_redirect_uri",comment = "重定向地址",type = MySqlTypeConstant.VARCHAR)
	private Set<String> registeredRedirectUri;

	@TableField(typeHandler = JacksonTypeHandler.class)
	@Column(value = "authorities",comment = "权限标识",defaultValue = "\"[]\"",type = MySqlTypeConstant.VARCHAR)
	private List<GrantedAuthority> authorities;
	
	@Column(value = "access_token_validity_seconds",defaultValue = "86400",comment = "授权令牌有效期 秒")
	private Integer accessTokenValiditySeconds;
	
	@Column(value = "refresh_token_validity_seconds",defaultValue = "86400",comment = "刷新令牌有效期 秒")
	private Integer refreshTokenValiditySeconds;

	@TableField(typeHandler = JacksonTypeHandler.class)
	@Column(value = "additional_information",type = MySqlTypeConstant.VARCHAR)
	private Map<String, Object> additionalInformation;

	@TableField(typeHandler = JacksonTypeHandler.class)
	@Column(value = "autoapprove",type = MySqlTypeConstant.VARCHAR)
	private Set<String> autoapprove;

	@Override
	public boolean isSecretRequired() {
		return this.clientSecret != null;
	}

	@Override
	public boolean isScoped() {
		return this.scope != null && !this.scope.isEmpty();
	}

	@Override
	public boolean isAutoApprove(String scope) {
		
		if (autoapprove == null) {
			return false;
		}
		
		for (String auto : autoapprove) {
			if (auto.equals("true") || scope.matches(auto)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public Set<String> getAuthorizedGrantTypes(){
		
		if(BaseCommon.collectionIsEmpty(authTypes)) {
			return null;
		}
		
		Set<String> datas = Sets.newHashSet();
		
		for(AuthType authType : authTypes) {
			datas.add(authType.getDesc());
		}
		
		return datas;
				
	}
	
}
