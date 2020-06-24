package org.beehyv.dhis2openimis.adapter.dhis.relationship.util;

import org.beehyv.dhis2openimis.adapter.dhis.relationship.pojo.TrackedEntityRequestComplete;
import org.beehyv.dhis2openimis.adapter.util.APIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RelationshipPoster {
	private static final Logger logger = LoggerFactory.getLogger(RelationshipPoster.class);
	private RestTemplate restTemplate;
	private HttpHeaders authHeaders;
	
	@Autowired
	public RelationshipPoster(RestTemplate restTemplate, @Qualifier("Dhis2")HttpHeaders authHeaders) {
		this.restTemplate = restTemplate;
		this.authHeaders = authHeaders;
	}
	
	
	public void post(String claimTei, TrackedEntityRequestComplete terc) {
		String url = getUrl(claimTei);
		makeApiCall(url, terc);
	}

	private String getUrl(String claimTei) {
		String url = APIConfiguration.DHIS_TRACKED_ENTITY_INSTANCES_URL + "/" + claimTei; 
		return url;
	}
	
	private void makeApiCall(String url, TrackedEntityRequestComplete terc) {
		HttpEntity<TrackedEntityRequestComplete> request = new HttpEntity<>(terc, authHeaders);
		logger.debug("Adding complete TEI with relationship :" + terc.toString() + " using HTTP PUT at: " + url);
		restTemplate.put(url, request);
	}
}
