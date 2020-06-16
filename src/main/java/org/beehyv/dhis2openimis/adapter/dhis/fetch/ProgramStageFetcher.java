package org.beehyv.dhis2openimis.adapter.dhis.fetch;

import org.beehyv.dhis2openimis.adapter.dhis.cache.program.ProgramStageCache;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.program.ProgramStageBundle;
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
public class ProgramStageFetcher {
    private RestTemplate restTemplate;
    private HttpEntity<Void> request;
    private ProgramStageCache cache;

    @Autowired
    public ProgramStageFetcher(@Qualifier("Dhis2") HttpHeaders authHeaders, ProgramStageCache cache) {
        request = new HttpEntity<Void>(authHeaders);
        this.cache = cache;
        restTemplate = new RestTemplate();
    }

    public void fetchAndCache() {
        ResponseEntity<ProgramStageBundle> response = restTemplate.exchange(
                APIConfiguration.DHIS_PROGRAM_STAGES_GET_URL,
                HttpMethod.GET, request, ProgramStageBundle.class);
        ProgramStageBundle bundle = response.getBody();
        cache.save(bundle);
    }
}
