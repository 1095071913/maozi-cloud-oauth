package com.maozi.oauth.client.vo.v1.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maozi.base.AbstractBaseVomain;
import com.maozi.base.enums.Status;
import com.maozi.oauth.client.enums.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;

@Data
public class ClientInfoVo extends AbstractBaseVomain {

	@Schema(description = "客户端ID")
	private String clientId;
	
	@Schema(description = "名称")
	private String name;
	
	@Schema(description = "授权模式",ref = "EnumResult")
	private Set<AuthType> authTypes;
	
	@Schema(description = "授权令牌有效期 秒 默认一天")
	private Integer accessTokenValiditySeconds;
	
	@Schema(description = "刷新令牌有效期 秒 默认一天")
	private Integer refreshTokenValiditySeconds;
	
	@Schema(description = "备注")
	private String remark;
	
	@Schema(description = "状态")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	private Status status;
	
}
