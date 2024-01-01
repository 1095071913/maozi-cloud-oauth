package com.maozi.oauth.client.vo.v1.platform;

import com.maozi.base.AbstractBaseVomain;
import com.maozi.base.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListVo extends AbstractBaseVomain {
	
	private Long id;
	
	@ApiModelProperty("客户端ID")
	private String clientId;
	
	@ApiModelProperty("名称")
	private String name;
	
	@ApiModelProperty("授权令牌有效期 秒 默认一天")
	private Integer accessTokenValiditySeconds;
	
	@ApiModelProperty("刷新令牌有效期 秒 默认一天")
	private Integer refreshTokenValiditySeconds;
	
	@ApiModelProperty(value = "状态",dataType = "com.maozi.base.result.EnumResult")
	private Status status; 

}
