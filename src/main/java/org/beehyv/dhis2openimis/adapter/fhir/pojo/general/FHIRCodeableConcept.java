package org.beehyv.dhis2openimis.adapter.fhir.pojo.general;
import java.util.List;

public class FHIRCodeableConcept {

    private String type;
    private String text;
    private List<FHIRCoding> coding;
    
    public FHIRCodeableConcept() {

    }
    

    public FHIRCodeableConcept(String text, List<FHIRCoding> coding ) {

        this.text = text;
        this.coding = coding;
    }
    public FHIRCodeableConcept( String text ) {

        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<FHIRCoding> getCoding() {
        return coding;
    }

    public void setCoding(List<FHIRCoding> coding) {
        this.coding = coding;
    }

    @Override
    public String toString() {
        return type + " [text=" + text + ", coding="+coding+"]";
    }
}
