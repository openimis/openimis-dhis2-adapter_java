package org.beehyv.dhis2openimis.adapter.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@Configuration
public class AuthorizationHeaderConfig {
	
	@Bean("OpenImis")
	public HttpEntity<Void> getOpenImisConfiguredRequest() {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", APIConfiguration.getOpenIMISAuthorizationHeader());

	    HttpEntity<Void> request = new HttpEntity<>(headers);
	    return request;
	}
	
	
	@Bean("Dhis2")
	public HttpHeaders getDhis2ConfiguredRequest(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", APIConfiguration.getDhis2AuthorizationHeader());
		return headers;
	}
}
