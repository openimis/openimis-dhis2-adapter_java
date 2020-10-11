package org.beehyv.dhis2openimis.adapter.fhir.pojo.coverage;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.ExtensionItem;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.BillablePeriod;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifier;

import java.util.List;

/**
 * @author Vishal
 */
public class Coverage {

    private String resourceType;
    private List<FHIRReference> contract;
    private List<FHIRIdentifier> identifier;
    private FHIRReference policyHolder;
    private BillablePeriod period;
    private String status;
    private Grouping grouping;
    private List<ExtensionItem> extension;

    public Coverage() {
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public List<FHIRReference> getContract() {
        return contract;
    }

    public void setContract(List<FHIRReference> contract) {
        this.contract = contract;
    }

    public List<FHIRIdentifier> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<FHIRIdentifier> identifier) {
        this.identifier = identifier;
    }

    public FHIRReference getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(FHIRReference policyHolder) {
        this.policyHolder = policyHolder;
    }

    public BillablePeriod getPeriod() {
        return period;
    }

    public void setPeriod(BillablePeriod period) {
        this.period = period;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Grouping getGrouping() {
        return grouping;
    }

    public void setGrouping(Grouping grouping) {
        this.grouping = grouping;
    }

    public List<ExtensionItem> getExtension() {
        return extension;
    }

    public void setExtension(List<ExtensionItem> extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Coverage{" +
                "resourceType='" + resourceType + '\'' +
                ", contract=" + contract +
                ", identifier=" + identifier +
                ", policyHolder=" + policyHolder +
                ", period=" + period +
                ", status='" + status + '\'' +
                ", grouping=" + grouping +
                ", extension=" + extension +
                '}';
    }
}

