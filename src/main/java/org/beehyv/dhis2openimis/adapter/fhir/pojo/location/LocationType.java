package org.beehyv.dhis2openimis.adapter.fhir.pojo.location;

import java.util.List;

public class LocationType {
    private List<LocationCoding> coding;
    private String text;
    
    public LocationType() {
    	
    }

    
    public List<LocationCoding> getCoding() {
        return coding;
    }
    public void setCoding(List<LocationCoding> coding) {
        this.coding = coding;
    }

    @Override
    public String toString() {
        return "Locationtype [coding=" + coding + ",text=" + text + "]";
    }
}
