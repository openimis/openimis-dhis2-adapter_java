package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRCodeableConcept;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import java.util.List;
/**
 * @author Delcroix Patrick
 */
public class FHIRContext {

    private List<FHIRCodeableConcept> code;
    private FHIRReference reference;

    public List<FHIRCodeableConcept> getCode() {
        return code;
    }
    public void setCode(List<FHIRCodeableConcept> code) {
        this.code = code;
    }

    public FHIRReference getReference() {
        return reference;
    }
    public void setParty(FHIRReference reference) {
        this.reference = reference;
    }
}
