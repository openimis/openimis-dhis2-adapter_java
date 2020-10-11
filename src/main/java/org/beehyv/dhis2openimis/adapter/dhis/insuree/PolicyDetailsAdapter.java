package org.beehyv.dhis2openimis.adapter.dhis.insuree;

import org.beehyv.dhis2openimis.adapter.dhis.cache.attribute_options.PolicyStatusCache;
import org.beehyv.dhis2openimis.adapter.dhis.cache.data_element.DataElementCache;
import org.beehyv.dhis2openimis.adapter.dhis.cache.program.ProgramCache;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.poster.DataValue;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.poster.DetailsJson;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.ExtensionItem;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRContract;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRValuedItem;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRAsset;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRValuedItem;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRPeriod;
import org.beehyv.dhis2openimis.adapter.util.ExtensionUrlUtil;
import org.beehyv.dhis2openimis.adapter.util.exception.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
@Component
public class PolicyDetailsAdapter {
	private static final Logger logger = LoggerFactory.getLogger(PolicyDetailsAdapter.class);
	
    private DataElementCache dataElementCache;
    private ProgramCache programCache;
    private PolicyStatusCache policyStatusCache;
    
    @Autowired
    public PolicyDetailsAdapter(DataElementCache dataElementCache, ProgramCache programCache, PolicyStatusCache policyStatusCache) {
        this.dataElementCache = dataElementCache;
        this.programCache = programCache;
        this.policyStatusCache = policyStatusCache;
    }

    
    public DetailsJson adaptContractToPolicyDetails(FHIRContract contract) throws ObjectNotFoundException {
    	DetailsJson json  = new DetailsJson();
        String programId = programCache.getByDisplayName("Family/Insuree").getId();
        FHIRAsset contractAsset = contract.getTerm().get(0).getAsset().get(0);
        json.setProgram(programId);
        json.setStatus("COMPLETED");
        json.setCompletedDate(LocalDate.now().toString());
        
    	String enrollDateString = getEnrollDateString(contractAsset);
    	json.setEventDate(enrollDateString);
        
		List<DataValue> dataValues = new ArrayList<>();
        

        fillPolicyId(dataValues, contract);
        fillProduct(dataValues, contractAsset);
        fillPolicyStage(dataValues, contract);
        fillPolicyStatus(dataValues, contract);
        fillPolicyValue(dataValues, contractAsset);

        json.setDataValues(dataValues);
        return json;
    }
    
    public String getEnrollDateString(FHIRAsset contractAsset) throws ObjectNotFoundException {
        List<FHIRPeriod> period = contractAsset.getPeriod();
        if ( period != null && period.get(0)!= null ) {
            return period.get(0).getStart();
        }else{
            throw new ObjectNotFoundException("No EnrollDate found in contract ");
        } 
    }
    
    private void fillPolicyId(List<DataValue> dataValues, FHIRContract contract) {
    	try {
    		String value = contract.getIdentifier().get(0).getValue();
            String dataElementId = dataElementCache.get("Policy ID");
	        DataValue<String> dataValue = new DataValue<>(dataElementId, value);
	        dataValues.add(dataValue);
    	} catch(NullPointerException | IndexOutOfBoundsException e) {
    		//Do nothing as no proper mapping was found.
    	}
    }

    private void fillProduct(List<DataValue> dataValues, FHIRAsset contractAsset) {
    	try {
    		String dataElementId = dataElementCache.get("Product");
    		String value = contractAsset.getTypeReference().get(0).getDisplay();
    		DataValue<String> dataValue = new DataValue<>(dataElementId, value);
    		dataValues.add(dataValue);
    	} catch(NullPointerException | IndexOutOfBoundsException e) {
    		//Do nothing, as no proper mapping was found.
    	}
    }

    private void fillPolicyStage(List<DataValue> dataValues, FHIRContract contract) {
        String dataElementId = dataElementCache.get("Policy stage");
        String value = "";
        if (contract.getLegalState() != null) {
            value = contract.getLegalState().getText();
        } else {
            value = "";
        }

        if ("Offered".equals(value)) {
            value = "New policy";
        } else if ("Renewed".equals(value)) {
            value = "Renewed  policy";
        }

        DataValue<String> dataValue = new DataValue<>(dataElementId, value);
        dataValues.add(dataValue);
    }

    private void fillPolicyStatus(List<DataValue> dataValues, FHIRContract contract){
        //TODO get from openImisClaim then use OptionsCache.
        try {
            String value = contract.getStatus();
            if ("Offered".equals(value)) {
                value = "Idle";
            } else if ("Terminated".equals(value)) {
                value = "Expired";
            } else if ("Disputed".equals(value)) {
                value = "Suspended";
            } else if ("Executable".equals(value)) {
                value = "Ready";
            }else if ("Policy".equals(value)) {
                value = "Active";
            }
        	//String upperCaseImisvalue = imisValue.substring(0,1).toUpperCase() + imisValue.substring(1);
        	//String value = policyStatusCache.getCodeFor(upperCaseImisvalue);
        	String dataElementId = dataElementCache.get("Policy status");
        	DataValue<String> dataValue = new DataValue<>(dataElementId, value);
            dataValues.add(dataValue);
        } catch(NullPointerException e) {
            //Do nothing, as no proper mapping was found.
            logger.error(e.getMessage());
        } 
    }

    private void fillPolicyValue(List<DataValue> dataValues, FHIRAsset contractAsset) {
        String dataElementId = dataElementCache.get("Policy Value ");
        
        FHIRValuedItem valuedItems = contractAsset.getValuedItem().get(0) ;
        Double value = valuedItems.getNet().getValue();
       
        //if (contract.getContract() != null) {
        //    value = contract.getContract().get(0).getValuedItem().get(0).getNet().getValue();
        //}
        DataValue<Double> dataValue = new DataValue<>(dataElementId, value);
        if(value != null) {
        	dataValues.add(dataValue);
        }
    }
}
