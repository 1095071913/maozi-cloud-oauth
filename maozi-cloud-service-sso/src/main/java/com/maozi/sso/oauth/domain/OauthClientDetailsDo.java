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

package com.maozi.sso.oauth.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import com.maozi.base.AbstractBaseDomain;

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
@TableName("oauth_client_details")
@TableComment("租户/渠道表")
public class OauthClientDetailsDo extends AbstractBaseDomain{
	
	@Column(value = "client_id",comment = "账号id")
	private String clientId;
	
	@Column(value = "authorized_grant_types" , defaultValue = "client_credentials")
	private String authorizedGrantTypes;
	
	@Column(value = "name",comment = "名称")
	private String name;
	
	@Column(value = "remark",comment = "备注")
	private String remark;
	
	@Column(value = "resource_ids" , defaultValue = "backend-resources")
	private String resourceIds;
	
	@Column(value = "client_secret",comment = "密码")
	private String clientSecret;
	
	@Column(value = "scope",defaultValue = "backend",comment = "范围")
	private String scope;
	
	@Column("web_server_redirect_uri")
	private String webServerRedirectUri;
	
	@Column("authorities")
	private String authorities;
	
	@Column(value = "access_token_validity",defaultValue = "86400")
	private String accessTokenValidity;
	
	@Column(value = "refresh_token_validity",defaultValue = "86400")
	private String refreshTokenValidity;
	
	@Column("additional_information")
	private String additionalInformation;
	
	@Column("autoapprove")
	private String autoapprove;

}
