package org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.claimResponseItem;

public class ClaimResponseCoding {
    private String code;
    
    
    public ClaimResponseCoding() {
		
	}

    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Coding [code=" + code + "]";
    }
}
