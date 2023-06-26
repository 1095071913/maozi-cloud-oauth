package com.maozi.sso.client.api.rpc.v1;

import java.util.Collection;
import java.util.List;

import com.maozi.base.result.DropDownResult;
import com.maozi.common.result.AbstractBaseResult;

public interface RpcClientServiceV1 {
	
	AbstractBaseResult<Void> checkAvailableResult(Long id);
	
	AbstractBaseResult<DropDownResult> dropDownResult(Long id);
	
	AbstractBaseResult<List<DropDownResult>> dropDownListResult(Collection<Long> ids);

}