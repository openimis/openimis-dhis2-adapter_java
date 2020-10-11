package org.beehyv.dhis2openimis.adapter.fhir.cacheService;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRContract;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FHIRContractCacheService {
    private Map<String, FHIRContract> map;


    public FHIRContractCacheService() {
        map = new HashMap<String, FHIRContract>();
    }

    public void save(List<FHIRContract> contracts) {
        Map<String, FHIRContract> newFHIRContracts = contracts.stream()
                .collect(Collectors.toMap(contract -> contract.getIdentifier().get(0).getValue(), contract->contract));

        map.putAll(newFHIRContracts);
    }

    public FHIRContract getById(String id) {
        return map.get(id);
    }
    
    public Collection<FHIRContract> getAllContracts(){
    	return map.values();
    }
    
    public void clear() {
    	map.clear();
    }
}
