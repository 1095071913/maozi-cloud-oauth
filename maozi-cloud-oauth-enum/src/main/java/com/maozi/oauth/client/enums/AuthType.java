package com.maozi.oauth.client.enums;

import com.maozi.base.IEnum;
import com.maozi.base.enums.config.annotation.SwaggerDisplayEnum;
import lombok.Getter;
import lombok.Setter;

@SwaggerDisplayEnum
public enum AuthType implements IEnum {
	
	authorizationCode(0,"authorization_code","授权码模式"),password(1,"password","密码模式"),refreshToken(2,"refresh_token","刷新令牌"),implicit(3,"implicit","简单模式"),clientCredentials(4,"client_credentials","客户端模式");
	
	AuthType(Integer value,String desc,String name) {
		
		this.value = value;
		
		this.desc = desc;
		
		this.name = name;
		
	}
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String desc;

	@Getter
	@Setter
	private String name;
	
}
