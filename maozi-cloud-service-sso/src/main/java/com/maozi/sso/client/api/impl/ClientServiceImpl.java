package com.maozi.sso.client.api.impl;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.maozi.base.api.impl.BaseServiceImpl;
import com.maozi.sso.client.api.ClientService;
import com.maozi.sso.client.domain.ClientDo;
import com.maozi.sso.client.dto.v1.platform.SaveUpdateParam;
import com.maozi.sso.client.mapper.ClientMapper;

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
	
	protected Long restSaveUpdate(Long id,SaveUpdateParam param) {
		
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