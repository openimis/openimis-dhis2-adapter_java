package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRPeriod;
import java.util.List;

/**
 * @author Delcroix Patrick
 */
public class FHIRAsset {
    private List<FHIRValuedItem> valuedItem;
    private List<FHIRReference> typeReference;
    private List<FHIRPeriod> usePeriod;
    private List<FHIRPeriod> period;
    private List<FHIRContext> context;

    public List<FHIRValuedItem> getValuedItem() {
        return valuedItem;
    }
    public void setValuedItem(List<FHIRValuedItem> valuedItem) {
        this.valuedItem = valuedItem;
    }
    public List<FHIRReference> getTypeReference() {
        return typeReference;
    }
    public void setTypeReference(List<FHIRReference> typeReference) {
        this.typeReference = typeReference;
    }
    public List<FHIRPeriod> getPeriod() {
        return period;
    }
    public void setPeriod(List<FHIRPeriod> period) {
        this.period = period;
    }
    public List<FHIRPeriod> getUsePeriod() {
        return usePeriod;
    }
    public void setUsePeriod(List<FHIRPeriod> usePeriod) {
        this.usePeriod = usePeriod;
    }
    public List<FHIRContext> getContext() {
        return context;
    }
    public void setContext(List<FHIRContext> context) {
        this.context = context;
    }
}
