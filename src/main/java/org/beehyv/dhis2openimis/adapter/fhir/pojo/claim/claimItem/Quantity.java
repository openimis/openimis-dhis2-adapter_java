package org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.claimItem;

public class Quantity {

    private Integer value;

    public Quantity() {

    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quantity [value=" + value + "]";
    }
}
