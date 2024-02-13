package com.maozi.oauth.client.vo.v1.platform;

import com.maozi.base.AbstractBaseVomain;
import com.maozi.base.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClientListVo extends AbstractBaseVomain {
	
	private Long id;
	
	@Schema(description = "客户端ID")
	private String clientId;
	
	@Schema(description = "名称")
	private String name;
	
	@Schema(description = "授权令牌有效期 秒 默认一天")
	private Integer accessTokenValiditySeconds;
	
	@Schema(description = "刷新令牌有效期 秒 默认一天")
	private Integer refreshTokenValiditySeconds;
	
	@Schema(description = "状态",ref = "EnumResult")
	private Status status; 

}
