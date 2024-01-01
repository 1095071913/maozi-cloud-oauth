package com.maozi.oauth.client.api;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClientService extends ClientDetailsService {
	
	String getClientId(Long id);
	
	Map<Long,String> getClientIdsMap(Collection<Long> ids);
	
}