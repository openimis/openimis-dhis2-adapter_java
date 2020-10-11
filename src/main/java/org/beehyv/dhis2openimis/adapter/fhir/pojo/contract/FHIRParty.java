package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRCodeableConcept;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;

/**
 * @author Delcroix Patrick
 */
public class FHIRParty {

    private FHIRCodeableConcept role;
    private FHIRReference reference;

    public FHIRCodeableConcept getRole() {
        return role;
    }
    public void setRole(FHIRCodeableConcept role) {
        this.role = role;
    }

    public FHIRReference getReference() {
        return reference;
    }
    public void setParty(FHIRReference reference) {
        this.reference = reference;
    }
}
