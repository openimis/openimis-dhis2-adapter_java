package org.beehyv.dhis2openimis.adapter.dhis.fetch;

import org.beehyv.dhis2openimis.adapter.dhis.cache.RelationshipTypeCache;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.relationship_type.RelationshipTypeBundle;
import org.beehyv.dhis2openimis.adapter.util.APIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vishal
 */
@Component
public class RelationshipTypeFetcher {
    private RestTemplate restTemplate;
    private HttpEntity<Void> request;
    private RelationshipTypeCache cache;

    @Autowired
    public RelationshipTypeFetcher(@Qualifier("Dhis2") HttpHeaders authHeaders, RelationshipTypeCache cache) {
        request = new HttpEntity<Void>(authHeaders);
        this.cache = cache;
        restTemplate = new RestTemplate();
    }

    public void fetchAndCache() {
        ResponseEntity<RelationshipTypeBundle> response = restTemplate.exchange(
                APIConfiguration.DHIS_RELATIONSHIP_TYPES_URL,
                HttpMethod.GET, request, RelationshipTypeBundle.class);

        RelationshipTypeBundle bundle = response.getBody();

        cache.save(bundle);
    }
}
