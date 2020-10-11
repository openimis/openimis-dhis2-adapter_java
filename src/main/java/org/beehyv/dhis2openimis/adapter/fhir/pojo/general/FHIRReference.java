package org.beehyv.dhis2openimis.adapter.fhir.pojo.general;


public class FHIRReference {

    private String reference;
    private String display;
    private String type;
    private FHIRIdentifier identifier;

    public FHIRReference() {

    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    public void setIdentifier(FHIRIdentifier identifier) {
        this.identifier = identifier;
    }
    public FHIRIdentifier getIdentifier() {
        return identifier;
    }

    public String getDisplay(){
        if(display != null){
            return display;
        }
        return null;
    }

    @Override
    public String toString() {
        if(type == null) {
            type = "Location";
        }
        return "Reference [type=" + type + ",reference=" + reference + ", display=" + display + ",identifier=" + identifier + "]";
    }
}
