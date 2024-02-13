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

package com.maozi.oauth.client.dto.v1.platform;

import com.maozi.base.AbstractBaseDtomain;
import com.maozi.base.enums.Status;
import com.maozi.oauth.client.enums.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ClientSaveUpdateParam extends AbstractBaseDtomain {
	
	@NotNull(message = "客户端ID不能为空")
	@Schema(description = "客户端ID")
	private String clientId;
	
	@NotNull(message = "客户端密钥不能为空")
	@Schema(description = "客户端密钥")
	private String clientSecret;
	
	@NotNull(message = "名称不能为空")
	@Schema(description = "名称")
	private String name;
	
	@Schema(description = "授权模式")
	private Set<AuthType> authTypes;
	
	@Schema(description = "授权令牌有效期 秒 默认一天")
	private Integer accessTokenValiditySeconds;
	
	@Schema(description = "刷新令牌有效期 秒 默认一天")
	private Integer refreshTokenValiditySeconds;
	
	@Schema(description = "备注")
	private String remark;
	
	@Schema(description = "状态")
	private Status status;
	
}
