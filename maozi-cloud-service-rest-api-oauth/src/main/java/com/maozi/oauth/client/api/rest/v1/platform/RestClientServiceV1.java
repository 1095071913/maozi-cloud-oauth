package com.maozi.oauth.client.api.rest.v1.platform;

import com.maozi.base.param.PageParam;
import com.maozi.base.result.DropDownResult;
import com.maozi.base.result.PageResult;
import com.maozi.common.result.AbstractBaseResult;
import com.maozi.oauth.client.dto.v1.platform.ListParam;
import com.maozi.oauth.client.dto.v1.platform.SaveUpdateParam;
import com.maozi.oauth.client.vo.v1.platform.InfoVo;
import com.maozi.oauth.client.vo.v1.platform.ListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "【平台】【V1】客户端模块")
@RequestMapping("/client/platform/v1")
public interface RestClientServiceV1 {
	
	@PostMapping("/list")
	@ApiOperation(value = "列表")
	@PreAuthorize("hasAuthority('system:client:list')")
	AbstractBaseResult<PageResult<ListVo>> restList(@RequestBody PageParam<ListParam> pageParam);
	
	@PostMapping("/save")
	@ApiOperation(value = "保存")
	@PreAuthorize("hasAuthority('system:client:save')")
	AbstractBaseResult<Long> restSave(@RequestBody SaveUpdateParam param);
	
	@GetMapping(value="/dropDownList")
	@ApiOperation(value = "下拉列表")
	@PreAuthorize("hasAuthority('system:user:list')")
	AbstractBaseResult<List<DropDownResult>> dropDownListResult();
	
	@Api(tags = "【平台】【V1】客户端模块")
	@RequestMapping("/client/platform/v1/{id}")
	public interface RestClientServiceCurrentV1 {
		
		@GetMapping(value="/get")
		@ApiOperation(value = "详情")
		@PreAuthorize("hasAuthority('system:client:get')")
		AbstractBaseResult<InfoVo> restGet(@PathVariable Long id);
		
		@PostMapping("/update")
		@ApiOperation(value = "更新")
		@PreAuthorize("hasAuthority('system:client:update')")
		AbstractBaseResult<Void> restUpdate(@PathVariable Long id,@RequestBody SaveUpdateParam param);
		
		@PostMapping(value="/updateStatus")
		@ApiOperation(value = "更新状态")
		@PreAuthorize("hasAuthority('system:client:update')")
		AbstractBaseResult<Void> restUpdateStatus(@PathVariable Long id);
		
	}
	
}