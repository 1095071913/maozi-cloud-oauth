package com.maozi.sso.client.api.impl.rest.v1.platform;

import java.util.List;

import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.maozi.base.annotation.RestService;
import com.maozi.base.param.PageParam;
import com.maozi.base.result.DropDownResult;
import com.maozi.base.result.PageResult;
import com.maozi.common.result.AbstractBaseResult;
import com.maozi.sso.client.api.impl.ClientServiceImpl;
import com.maozi.sso.client.api.rest.v1.platform.RestClientServiceV1;
import com.maozi.sso.client.domain.ClientDo;
import com.maozi.sso.client.dto.v1.platform.ListParam;
import com.maozi.sso.client.dto.v1.platform.SaveUpdateParam;
import com.maozi.sso.client.vo.v1.platform.InfoVo;
import com.maozi.sso.client.vo.v1.platform.ListVo;

@RestService
public class RestClientServiceImplV1 extends ClientServiceImpl implements RestClientServiceV1 {

	@Override
	public AbstractBaseResult<PageResult<ListVo>> restList(PageParam<ListParam> pageParam) {
		
		MPJLambdaWrapper<ClientDo> wrapper = MPJWrappers.lambdaJoin();
		
		wrapper.select(ClientDo::getId,ClientDo::getClientId,ClientDo::getName,ClientDo::getAccessTokenValiditySeconds,ClientDo::getRefreshTokenValiditySeconds,ClientDo::getStatus);
		
		ListParam param = pageParam.getData();
		if(isNotNull(param)) {
			
			wrapper.likeRight(isNotNull(param.getName()), ClientDo::getName,param.getName());
			
			param.initOrderParam();
            
            List<String> asc = param.getOrderAscFieldsMap().get("t");
            
            List<String> desc = param.getOrderDescFieldsMap().get("t");
            
            wrapper.orderByAscStr(asc.size() > 0 , asc );
            
            wrapper.orderByDescStr(desc.size() > 0 , desc );
			
		}
		
		return success(list(pageParam,wrapper,ListVo::new));
		
	}

	@Override
	public AbstractBaseResult<Long> restSave(SaveUpdateParam param) {
		return success(restSaveUpdate(null,param));
	}
	
	@Override
	public AbstractBaseResult<List<DropDownResult>> dropDownListResult(){
		return super.dropDownListResult();
	}
	
	@RestService
	public class RestClientServiceCurrentImplV1 extends ClientServiceImpl implements RestClientServiceCurrentV1 {

		@Override
		public AbstractBaseResult<InfoVo> restGet(Long id) {
			return success(getByIdThrowError(id,InfoVo.class,ClientDo::getClientId,ClientDo::getName,ClientDo::getAuthTypes,ClientDo::getAccessTokenValiditySeconds,ClientDo::getRefreshTokenValiditySeconds,ClientDo::getRemark,ClientDo::getStatus));
		}

		@Override
		public AbstractBaseResult<Void> restUpdate(Long id, SaveUpdateParam param) {
			
			restSaveUpdate(id,param);
			
			return success(null);
			
		}

		@Override
		public AbstractBaseResult<Void> restUpdateStatus(Long id) {
			return updateStatus(id);
		}
		
	}
	

}