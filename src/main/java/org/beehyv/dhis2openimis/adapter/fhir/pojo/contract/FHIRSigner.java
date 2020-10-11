package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRCoding;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;

/**
 * @author Delcroix Patrick
 */
public class FHIRSigner {

    private FHIRCoding coding;
    private FHIRReference party;

    public FHIRCoding getCoding() {
        return coding;
    }
    public void setCoding(FHIRCoding coding) {
        this.coding = coding;
    }

    public FHIRReference getParty() {
        return party;
    }
    public void setParty(FHIRReference party) {
        this.party = party;
    }
}
