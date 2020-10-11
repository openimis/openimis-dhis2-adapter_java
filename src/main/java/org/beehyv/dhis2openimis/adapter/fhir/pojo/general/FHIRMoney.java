package org.beehyv.dhis2openimis.adapter.fhir.pojo.general;


/**
 * @author Delcroix Patrick
 */
public class FHIRMoney {

    private Double value;
    private String currency;

    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
