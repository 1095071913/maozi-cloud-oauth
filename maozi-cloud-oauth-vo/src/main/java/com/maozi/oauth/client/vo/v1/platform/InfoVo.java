package com.maozi.oauth.client.vo.v1.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maozi.base.AbstractBaseVomain;
import com.maozi.base.enums.Status;
import com.maozi.oauth.client.enums.AuthType;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import lombok.Data;

@Data
public class InfoVo extends AbstractBaseVomain {

	@ApiModelProperty("客户端ID")
	private String clientId;
	
	@ApiModelProperty("名称")
	private String name;
	
	@ApiModelProperty(value = "授权模式",dataType = "com.maozi.base.result.EnumResult")
	private Set<AuthType> authTypes;
	
	@ApiModelProperty("授权令牌有效期 秒 默认一天")
	private Integer accessTokenValiditySeconds;
	
	@ApiModelProperty("刷新令牌有效期 秒 默认一天")
	private Integer refreshTokenValiditySeconds;
	
	@ApiModelProperty("备注")
	private String remark;
	
	@ApiModelProperty("状态")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	private Status status;
	
}
