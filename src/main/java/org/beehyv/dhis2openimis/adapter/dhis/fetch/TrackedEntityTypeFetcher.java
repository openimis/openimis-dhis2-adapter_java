package org.beehyv.dhis2openimis.adapter.dhis.fetch;

import org.beehyv.dhis2openimis.adapter.dhis.cache.TrackedEntityTypeCache;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.type.TrackedEntityTypeBundle;
import org.beehyv.dhis2openimis.adapter.util.APIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrackedEntityTypeFetcher {
	
	private RestTemplate restTemplate;
	private HttpEntity<Void> request;
	private TrackedEntityTypeCache cache;
	
	@Autowired
	public TrackedEntityTypeFetcher(@Qualifier("Dhis2") HttpHeaders authHeaders, TrackedEntityTypeCache cache) {
		request = new HttpEntity<Void>(authHeaders);
		this.cache = cache;
		restTemplate = new RestTemplate();
	}
	
	public void fetchAndCache() {
		ResponseEntity<TrackedEntityTypeBundle> response = restTemplate.exchange(
														APIConfiguration.DHIS_TRACKED_ENTITY_TYPES_GET_URL, 
														HttpMethod.GET, request, TrackedEntityTypeBundle.class);
		
		TrackedEntityTypeBundle bundle = response.getBody();
		
		cache.save(bundle);
	}
	
	
}
