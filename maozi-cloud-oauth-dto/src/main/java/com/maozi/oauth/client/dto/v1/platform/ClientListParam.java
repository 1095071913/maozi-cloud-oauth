package com.maozi.oauth.client.dto.v1.platform;

import com.maozi.base.AbstractBaseDtomain;
import com.maozi.base.param.plugin.OrderParam;
import com.maozi.base.plugin.query.QueryBaseType;
import com.maozi.base.plugin.query.QueryPlugin;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class ClientListParam extends AbstractBaseDtomain implements OrderParam {
	
	@Schema(description = "名称")
	@QueryPlugin(value = QueryBaseType.like)
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
