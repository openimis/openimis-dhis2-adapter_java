package org.beehyv.dhis2openimis.adapter.fhir.cacheService;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifier;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifierTypeCodename;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.location.Location;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.util.exception.ObjectNotFoundException;
import org.beehyv.dhis2openimis.adapter.util.exception.InternalException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class LocationCacheService {

    private Map<String, Location> map;
    private Map<String, Location> map_l2; // Region
    private Map<String, Location> map_l3; // District
    private Map<String, Location> map_l4; // Ward and HF
    private Map<String, Location> map_l5; // Village
    private static final Logger logger = LoggerFactory.getLogger(LocationCacheService.class);
    

    public LocationCacheService() {
        map = new HashMap<String, Location>();
        map_l2 = new HashMap<String, Location>();
        map_l3 = new HashMap<String, Location>();
        map_l4 = new HashMap<String, Location>();
        map_l5 = new HashMap<String, Location>();
    }

    public void save(List<Location> locations) {

        for (Location location : locations) {
            //logger.info("Location parsed:" + location.toString());
            map.put(location.getId(), location);
            
        }

    }

    public void clear(){
        map.clear();
        map_l2.clear();
        map_l3.clear();
        map_l5.clear();
        map_l5.clear();
    }

    public Location getLocationById(String id) {
        return map.get(id);
    }
    
    public String getCodeById(String id) throws ObjectNotFoundException {
    	Location location = map.get(id);
    	if(location != null) {
    		List<FHIRIdentifier> identifiers = location.getIdentifier();
    		for(FHIRIdentifier identifier : identifiers) {
    			if(identifier.getType().getCoding().get(0).getCode() == FHIRIdentifierTypeCodename.FI.name()) {
    				return identifier.getValue();
    			}
    		}
    		throw new ObjectNotFoundException("No identifier in Location with FHIRIdentifierTypeCodename.FI");
    	} else {
    		throw new ObjectNotFoundException("No location with given id: "+ id);
    	}
    	
    }
    public Collection<Location> getAllLocations(){
    	return map.values();
    }
    public Collection<Location> getL2Locations(){
    	return map_l2.values();
    }
    public Collection<Location> getL3Locations(){
    	return map_l3.values();
    }
    public Collection<Location> getL4Locations(){
    	return map_l4.values();
    }
    public Collection<Location> getL5Locations(){
    	return map_l5.values();
    }


    public void assignLevel(){
        // assign L2
        HashMap<String, Location> map_temp = new HashMap<String, Location>();
        Iterator<Map.Entry<String, Location>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Location> entry = it.next();
            Location location = entry.getValue();
            FHIRReference partOf = location.getPartOf();
            if( partOf == null){
                map_l2.put(location.getCode(),location);
            }else if(location.getName() != "Funding"){
                map_temp.put(location.getCode(),location);
            }
        }
        logger.info("Number of Location to assign at 2nd step (L3):" + map_temp.size() );
        // assign L3
        it = map_temp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Location> entry = it.next();
            Location location = entry.getValue();
            String partOfDisplay = location.getPartOf().getDisplay();
            if(partOfDisplay == null){
                logger.info("Parent id not defined for the Location " + location.getId() + ", partOf" + location.getPartOf().toString() );
                it.remove();
            }else if(map_l2.get(partOfDisplay) != null){
                map_l3.put(location.getCode(),location);
                it.remove();
            }else if (map_temp.get(partOfDisplay) == null){
                logger.info("Parent " + partOfDisplay + " defined for the Location " + location.getId() + " but not found" );
            }
        }
        logger.info("Number of Location to assign at 3rd step (L4):" + map_temp.size() );
        // assign L4
        it = map_temp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Location> entry = it.next();
             Location location = entry.getValue();
             String partOfDisplay = location.getPartOf().getDisplay();
            if(partOfDisplay != null && map_l3.get(partOfDisplay) != null){
                map_l4.put(location.getCode(),location);
                it.remove();
            }
        }
        logger.info("Number of Location to assign at 4th step (L5):" + map_temp.size() );
        // assign L5
        it = map_temp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Location> entry = it.next();
            Location location = entry.getValue();
            String partOfDisplay = location.getPartOf().getDisplay();
            if(partOfDisplay != null && map_l4.get(partOfDisplay) != null){
                map_l5.put(location.getCode(),location);
                it.remove();
            }
         }

         if (map_temp.size() > 0){
            logger.info("there is Location(s) with parent not found:" + map_temp.size()  );
         }
    }
}
