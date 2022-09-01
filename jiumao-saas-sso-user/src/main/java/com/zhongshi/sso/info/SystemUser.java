package com.zhongshi.sso.info;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiumao.base.AbstractBaseDtomain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUser extends AbstractBaseDtomain{
	
	private Long id;

	@ApiModelProperty("账号")
	private String username;
	
	@ApiModelProperty("权限")
	private List<String> permissions;
	
}