package com.maozi.oauth.client.api.rpc.v1;

import com.maozi.base.result.DropDownResult;
import com.maozi.common.result.AbstractBaseResult;
import java.util.Collection;
import java.util.List;

public interface RpcClientServiceV1 {
	
	AbstractBaseResult<Void> checkAvailableResult(Long id);
	
	AbstractBaseResult<DropDownResult> dropDownResult(Long id);
	
	AbstractBaseResult<List<DropDownResult>> dropDownListResult(Collection<Long> ids);

}