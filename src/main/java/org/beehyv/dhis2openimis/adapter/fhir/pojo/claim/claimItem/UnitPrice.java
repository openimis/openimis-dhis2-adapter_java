package org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.claimItem;

public class UnitPrice {
    private Double value;
    
    
    public UnitPrice() {
    	
    }
    

    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "UnitPrice [value=" + value + "]";
    }
}
