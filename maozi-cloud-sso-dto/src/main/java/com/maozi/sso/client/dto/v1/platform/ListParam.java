package com.maozi.sso.client.dto.v1.platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maozi.base.AbstractBaseDtomain;
import com.maozi.base.param.plugin.OrderParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListParam extends AbstractBaseDtomain implements OrderParam {
	
	@ApiModelProperty("名称")
	private String name;
	
	private Map<String,Boolean> orderFieldMap;
    
    private Map<String,List<String>> orderAscFieldsMap;
    
    private Map<String,List<String>> orderDescFieldsMap;
    
    private Map<String, Map<String, Boolean>> orderMainFieldsMap = new HashMap<>() {{

        put("t", new HashMap<String, Boolean>() {{
            put("createTime", true);
        }});

    }};

}
