package org.beehyv.dhis2openimis.adapter.dhis.util;

import org.beehyv.dhis2openimis.adapter.dhis.pojo.poster.enrollment.EnrollmentUpdateBody;
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
public class EnrollmentPoster {
	private static final Logger logger = LoggerFactory.getLogger(EnrollmentPoster.class);
	private RestTemplate restTemplate;
	private HttpHeaders authHeader;
	
	@Autowired
	public EnrollmentPoster(RestTemplate restTemplate, @Qualifier("Dhis2")HttpHeaders authHeader) {
		this.restTemplate = restTemplate;
		this.authHeader = authHeader;
	}
	
	public void updateEnrollment(String enrollmentId, EnrollmentUpdateBody updateBody) {
		String url = APIConfiguration.getEnrollmentDataFromEnrollmentIdUrl(enrollmentId);
		HttpEntity<EnrollmentUpdateBody> httpEntity = new HttpEntity<>(updateBody, authHeader);
		restTemplate.put(url, httpEntity);
	}
	
}
