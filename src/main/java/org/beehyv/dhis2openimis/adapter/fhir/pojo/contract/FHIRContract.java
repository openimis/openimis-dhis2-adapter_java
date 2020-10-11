package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import java.util.List;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRCodeableConcept;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifier;

/**
 * @author Delcroix Patrick
 */
public class FHIRContract {
    private List<FHIRIdentifier> identifier;
    private List<FHIRSigner> signer;
    private List<FHIRTerm> term;
    private String status;
    private FHIRCodeableConcept legalState;

    public List<FHIRSigner> getSigner() {
        return signer;
    }

    public void setSigner(List<FHIRSigner> signer) {
        this.signer = signer;
    }

    public List<FHIRTerm> getTerm() {
        return term;
    }

    public void setTerm(List<FHIRTerm> term) {
        this.term = term;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public FHIRCodeableConcept getLegalState() {
        return legalState;
    }
    public void setLegalState(FHIRCodeableConcept legalState) {
        this.legalState = legalState;
    }
    public List<FHIRIdentifier> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<FHIRIdentifier> identifier) {
        this.identifier = identifier;
    }
}
