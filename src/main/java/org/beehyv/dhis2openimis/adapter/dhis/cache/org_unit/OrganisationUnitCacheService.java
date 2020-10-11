package org.beehyv.dhis2openimis.adapter.dhis.cache.org_unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnit;
import org.beehyv.dhis2openimis.adapter.util.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrganisationUnitCacheService {
	
	/**
	 * code as key
	 * id as value
	 */
	private Map<String, String> cache;
	
	public OrganisationUnitCacheService() {
		cache = new HashMap<String, String>();
	}
	
	public void save(List<OrganisationUnit> organisations) {
		cache.clear();
		for (OrganisationUnit organisation : organisations) {
            if(organisation.getCode() != null){
            	cache.put(organisation.getCode(), organisation.getId());
			}
        }		
	}

	public void add(String Code, String Id) {
		if(Code!= null){
			cache.put(Code, Id);
		}
	}

	
	public String getByCode(String code) throws ObjectNotFoundException {
		String output = cache.get(code);
		if(output != null) {
			return output;
		} else {
			throw new ObjectNotFoundException("No orgUnit found with code :" + code);
		}
	}

	public String getByCodeSilent(String code){
		return cache.get(code);
	}
}
