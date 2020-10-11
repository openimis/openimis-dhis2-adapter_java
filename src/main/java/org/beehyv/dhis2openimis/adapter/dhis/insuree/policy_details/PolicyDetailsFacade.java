package org.beehyv.dhis2openimis.adapter.dhis.insuree.policy_details;

import java.util.ArrayList;
import java.util.List;

import org.beehyv.dhis2openimis.adapter.dhis.cache.org_unit.OrganisationUnitCacheService;
import org.beehyv.dhis2openimis.adapter.dhis.insuree.ExistingInsureeFinder;
import org.beehyv.dhis2openimis.adapter.dhis.insuree.InsureeUtil;
import org.beehyv.dhis2openimis.adapter.dhis.insuree.PolicyDetailsAdapter;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.poster.DetailsJson;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.poster.event.Event;
import org.beehyv.dhis2openimis.adapter.dhis.util.ProgramStagePoster;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.ExtensionItem;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRContract;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRAsset;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRContext;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.coverage.Coverage;
import org.beehyv.dhis2openimis.adapter.util.ExtensionUrlUtil;
import org.beehyv.dhis2openimis.adapter.util.exception.InternalException;
import org.beehyv.dhis2openimis.adapter.util.exception.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyDetailsFacade {
	private static Logger logger = LoggerFactory.getLogger(PolicyDetailsFacade.class);
	
	@Autowired
	private ExistingInsureeFinder existingInsureeFinder;
	@Autowired
	private PolicyDetailsAdapter policyDetailsAdapter;
	@Autowired
	private InsureeUtil insureeUtil;
	@Autowired
	private ProgramStagePoster programStagePoster;
	@Autowired
	private OrganisationUnitCacheService orgUnitCache;
	
	public PolicyDetailsFacade() {
		
	}
	
	public void adaptAndPost(FHIRContract contract) throws ObjectNotFoundException, InternalException{

		FHIRAsset contractAsset = contract.getTerm().get(0).getAsset().get(0);
		if(contractAsset == null ) throw  new ObjectNotFoundException("No Asset found in the first Term of contract");
		DetailsJson policyDetailJson = policyDetailsAdapter.adaptContractToPolicyDetails(contract);
		List<InsureeIdAndOrgUnitPojo> insureesDetails = getInsureesDetails(contractAsset);
    	for(InsureeIdAndOrgUnitPojo insureeDetail : insureesDetails) {
			Integer step = 0;
    		String orgUnit = orgUnitCache.getByCode(insureeDetail.getOrgUnitId());
    		try {
				String teiId = existingInsureeFinder.getInsureeTrackedEntityId(orgUnit, insureeDetail.getInsureeId());
				String enrollmentId = existingInsureeFinder.getEnrollmentIdForTeiId(teiId);
				Event policyDetailEvent = convertToEvent(orgUnit, teiId, enrollmentId);
    			programStagePoster.postProgramStageData(policyDetailEvent, policyDetailJson);
    		} catch(Exception e) {
    			//This means we are unable to find one of the insuree in the insuree list of Coverage. 
    			//So, skip that it for that insuree and continue with the remaining list.
				throw new InternalException("Exception parsing List InsureeDetails, Reason:" +e.getMessage());
				//e.setMessage("Exception parsing List InsureeDetails at step" + step);
    		}
    	}
    }
	

	private List<InsureeIdAndOrgUnitPojo> getInsureesDetails(FHIRAsset contractAsset) throws ObjectNotFoundException{
    	List<InsureeIdAndOrgUnitPojo> insureesDetails = new ArrayList<>();
    	List<FHIRContext> insurees= contractAsset.getContext();
    	//String[] insurees = insureesString.split(";");
    	if(insurees.size() == 0) throw  new ObjectNotFoundException("No Insuree Found as context in Asset");
    	for(FHIRContext insuree : insurees) {
    		String[] insureeIdAndOrgUnitSplit = insuree.getReference().getDisplay().split(":");
    		InsureeIdAndOrgUnitPojo insureeDetail = new InsureeIdAndOrgUnitPojo(insureeIdAndOrgUnitSplit[0], insureeIdAndOrgUnitSplit[1]);
    		insureesDetails.add(insureeDetail);
    	}
    	
    	return insureesDetails;
    }
    /*
    private String getInsureesDetailString(FHIRAsset contractAsset) throws ObjectNotFoundException {
		List<FHIRReference> extensions = contractAsset.getContext();
		
    	for(ExtensionItem extension : extensions) {
    		if(extension.getUrl().equals(ExtensionUrlUtil.INSUREE_URL)) {
    			return extension.getValueString();
    		}
    	}
    	throw new ObjectNotFoundException("No insuree extension found in coverage.");
    }*/
	
    private Event convertToEvent(String orgUnit, String teiId, String enrollmentId) {
    	Event event = new Event();
    	
    	event.setEnrollment(enrollmentId);
    	event.setOrgUnit(orgUnit);
    	event.setTrackedEntityInstance(teiId);
    	
    	String programId = insureeUtil.getProgramId();
    	event.setProgram(programId);
    	String programStageId = insureeUtil.getProgramStageId();
    	event.setProgramStage(programStageId);
    	event.setStatus("SCHEDULE");
    	
    	return event;
    }
    
}
