package org.beehyv.dhis2openimis.adapter.fhir.pojo.contract;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRMoney;
/**
 * @author Delcroix Patrick
 */
public class FHIRValuedItem {

    private FHIRMoney net;

    public FHIRMoney getNet() {
        return net;
    }

    public void setNet(FHIRMoney net) {
        this.net = net;
    }
}
