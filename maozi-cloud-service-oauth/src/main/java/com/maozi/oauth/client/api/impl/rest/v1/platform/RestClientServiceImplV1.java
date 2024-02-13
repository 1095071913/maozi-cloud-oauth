package com.maozi.oauth.client.api.impl.rest.v1.platform;

import com.maozi.base.annotation.RestService;
import com.maozi.base.param.PageParam;
import com.maozi.base.result.DropDownResult;
import com.maozi.base.result.PageResult;
import com.maozi.common.result.AbstractBaseResult;
import com.maozi.oauth.client.api.impl.ClientServiceImpl;
import com.maozi.oauth.client.api.rest.v1.platform.RestClientServiceV1;
import com.maozi.oauth.client.domain.ClientDo;
import com.maozi.oauth.client.dto.v1.platform.ClientListParam;
import com.maozi.oauth.client.dto.v1.platform.ClientSaveUpdateParam;
import com.maozi.oauth.client.vo.v1.platform.ClientInfoVo;
import com.maozi.oauth.client.vo.v1.platform.ClientListVo;
import java.util.List;

@RestService
public class RestClientServiceImplV1 extends ClientServiceImpl implements RestClientServiceV1 {

	@Override
	public AbstractBaseResult<PageResult<ClientListVo>> restList(PageParam<ClientListParam> pageParam) {
		return success(list(pageParam, ClientListVo::new));
	}

	@Override
	public AbstractBaseResult<Long> restSave(ClientSaveUpdateParam param) {
		return success(restSaveUpdate(null,param));
	}
	
	@Override
	public AbstractBaseResult<List<DropDownResult>> dropDownListResult(){
		return super.dropDownListResult();
	}
	
	@RestService
	public class RestClientServiceCurrentImplV1 extends ClientServiceImpl implements RestClientServiceCurrentV1 {

		@Override
		public AbstractBaseResult<ClientInfoVo> restGet(Long id) {
			return success(getByIdThrowError(id, ClientInfoVo.class,ClientDo::getClientId,ClientDo::getName,ClientDo::getAuthTypes,ClientDo::getAccessTokenValiditySeconds,ClientDo::getRefreshTokenValiditySeconds,ClientDo::getRemark,ClientDo::getStatus));
		}

		@Override
		public AbstractBaseResult<Void> restUpdate(Long id, ClientSaveUpdateParam param) {
			
			restSaveUpdate(id,param);
			
			return success(null);
			
		}

		@Override
		public AbstractBaseResult<Void> restUpdateStatus(Long id) {
			return updateStatus(id);
		}
		
	}
	

}