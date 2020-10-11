package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import java.util.List;

/**
 * @author Delcroix Patrick
 */
public class FHIROffer {
    private List<FHIRParty> party;


    public List<FHIRParty> getParty() {
        return party;
    }

    public void setParty(List<FHIRParty> party) {
        this.party = party;
    }

}
