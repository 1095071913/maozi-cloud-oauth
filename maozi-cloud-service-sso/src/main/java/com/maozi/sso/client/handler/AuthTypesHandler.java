package com.maozi.sso.client.handler;

import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import com.maozi.base.IEnum;
import com.maozi.common.BaseCommon;
import com.maozi.mybatisplus.config.ListTypeHandler;
import com.maozi.sso.client.enums.AuthType;

public class AuthTypesHandler extends ListTypeHandler<Set<AuthType>>{
	
	private TypeReference typeReference = new TypeReference<Set<AuthType>>() {};
	
	@Override
    protected Object parse(String json) {
		
        try {return objectMapper.readValue(json, typeReference);} catch (Exception e) {
        	
            BaseCommon.throwSystemError(e);
            
            return null;
            
        }
        
    }
	
	@Override
    protected String toJson(Object obj) {
    	
		Set<IEnum> iEnums =  (Set<IEnum>) obj;
		
		Set<Integer> datas = Sets.newHashSet();
		
		for(IEnum iEnum : iEnums) {
			datas.add(iEnum.getValue());
		}
		
		try {return objectMapper.writeValueAsString(datas);} catch (Exception e) {
			 
			BaseCommon.throwSystemError(e);
			
	        return null;
	        
        }
		
    }

}
