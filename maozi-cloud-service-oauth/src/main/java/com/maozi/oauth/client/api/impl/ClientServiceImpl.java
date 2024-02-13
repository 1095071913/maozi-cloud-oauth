package com.maozi.oauth.client.api.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.maozi.base.api.impl.BaseServiceImpl;
import com.maozi.oauth.client.api.ClientService;
import com.maozi.oauth.client.domain.ClientDo;
import com.maozi.oauth.client.dto.v1.platform.ClientSaveUpdateParam;
import com.maozi.oauth.client.mapper.ClientMapper;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper,ClientDo,Void> implements ClientService {

	@Override
	protected String getAbbreviationModelName() {return "【客户端】";}
	
	protected PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("MD5");

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		
		MPJLambdaWrapper<ClientDo> wrapper = MPJWrappers.lambdaJoin(ClientDo.builder().clientId(clientId).build());
		
		wrapper.selectAll(ClientDo.class);
		
		return getAvailableByParam(wrapper);
		
	}
	
	protected Long restSaveUpdate(Long id, ClientSaveUpdateParam param) {
		
		if(isNotNull(id)) {
			param.setClientId(null);
		}else {
			checkNotHas(Wrappers.lambdaQuery(ClientDo.builder().clientId(param.getClientId()).build()));
		}
		
		if(isNotNull(param.getClientSecret())) {
			param.setClientSecret("{MD5}"+passwordEncoder.encode(param.getClientSecret()));
		}
		
		id = saveUpdate(null, param);
		
		return id;
		
	}

	@Override
	public String getClientId(Long id) {
		return getByIdThrowError(id, ClientDo::getClientId).getClientId();
	}

	@Override
	public Map<Long,String> getClientIdsMap(Collection<Long> ids) {
		
		MPJLambdaWrapper<ClientDo> wrapper = MPJWrappers.lambdaJoin();
		
		wrapper.select(ClientDo::getId,ClientDo::getClientId);
		
		wrapper.in(ClientDo::getId, ids);
		
		return list(wrapper).stream().collect(Collectors.toMap(ClientDo::getId, ClientDo::getClientId));
		
	}

}