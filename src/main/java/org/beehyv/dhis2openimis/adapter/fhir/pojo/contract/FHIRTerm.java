package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import java.util.List;

/**
 * @author Delcroix Patrick
 */
public class FHIRTerm {
    private List<FHIRAsset> asset;
    private List<FHIROffer> offer;


    public List<FHIRAsset> getAsset() {
        return asset;
    }
    public void setAsset(List<FHIRAsset> Asset) {
        this.asset = Asset;
    }

    public List<FHIROffer> getOffer() {
        return offer;
    }
    public void setOffer(List<FHIROffer> Offer) {
        this.offer = Offer;
    }
    

    
}
