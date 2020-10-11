package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.Bundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.LinkDetail;

import java.util.List;

/**
 * @author Vishal
 */
public class ContractBundle extends Bundle {
    List<ContractResource> entry;

    public ContractBundle() {

    }

    public ContractBundle(String resourceType, List<LinkDetail> link, Integer total, String type, List<ContractResource> entry) {
        super(resourceType, link, total, type);
        this.entry = entry;
    }

    public List<ContractResource> getEntry() {
        return entry;
    }

    public void setEntry(List<ContractResource> entry) {
        this.entry = entry;
    }


    @Override
    public String toString() {
        return "CoverageBundle [resourceType=" + resourceType + ", entry=" + entry + ", link=" + link + ", total="
                + total + ", type=" + type + "]";
    }
}
