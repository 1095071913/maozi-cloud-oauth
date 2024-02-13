package com.maozi.oauth.client.api.rest.v1.platform;

import com.maozi.base.annotation.Get;
import com.maozi.base.annotation.Post;
import com.maozi.base.param.PageParam;
import com.maozi.base.result.DropDownResult;
import com.maozi.base.result.PageResult;
import com.maozi.common.result.AbstractBaseResult;
import com.maozi.oauth.client.dto.v1.platform.ClientListParam;
import com.maozi.oauth.client.dto.v1.platform.ClientSaveUpdateParam;
import com.maozi.oauth.client.vo.v1.platform.ClientInfoVo;
import com.maozi.oauth.client.vo.v1.platform.ClientListVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = RestClientServiceV1.NAME)
public interface RestClientServiceV1 {

	String NAME = "【平台】【V1】客户端模块";

	String PATH = "/client/platform/v1";

	@Post(value = PATH + "/list",description = "列表")
	@PreAuthorize("hasAuthority('system:client:list')")
	AbstractBaseResult<PageResult<ClientListVo>> restList(@RequestBody PageParam<ClientListParam> pageParam);

	@Post(value = PATH + "/save",description = "保存")
	@PreAuthorize("hasAuthority('system:client:save')")
	AbstractBaseResult<Long> restSave(@RequestBody ClientSaveUpdateParam param);

	@Get(value = PATH + "/dropDownList",description = "下拉列表")
	@PreAuthorize("hasAuthority('system:user:list')")
	AbstractBaseResult<List<DropDownResult>> dropDownListResult();

	@Tag(name = NAME)
	public interface RestClientServiceCurrentV1 {

		String CURRENT_PATH = PATH + "/{id}";

		@Get(value = CURRENT_PATH + "/get",description = "详情")
		@PreAuthorize("hasAuthority('system:client:get')")
		AbstractBaseResult<ClientInfoVo> restGet(@PathVariable Long id);

		@Post(value = CURRENT_PATH + "/update",description = "更新")
		@PreAuthorize("hasAuthority('system:client:update')")
		AbstractBaseResult<Void> restUpdate(@PathVariable Long id,@RequestBody ClientSaveUpdateParam param);

		@Post(value = CURRENT_PATH + "/updateStatus",description = "更新状态")
		@PreAuthorize("hasAuthority('system:client:update')")
		AbstractBaseResult<Void> restUpdateStatus(@PathVariable Long id);
		
	}
	
}