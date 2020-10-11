package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

/**
 * @author Vishal
 */
public class ContractResource {
    private FHIRContract resource;
    private String fullUrl;

    public ContractResource(FHIRContract resource, String fullUrl) {
        this.resource = resource;
        this.fullUrl = fullUrl;
    }

    public ContractResource() {
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public FHIRContract getResource() {
        return resource;
    }

    public void setResource(FHIRContract resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "CoverageResource [fullUrl=" + fullUrl + ", resource=" + resource + "]";
    }
}
