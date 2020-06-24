package org.beehyv.dhis2openimis.adapter;

import org.beehyv.dhis2openimis.adapter.dhis.fetch.*;
import org.beehyv.dhis2openimis.adapter.openimis.APICaller;

import org.beehyv.dhis2openimis.adapter.util.APIConfiguration;
import org.beehyv.dhis2openimis.adapter.util.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Tester implements ApplicationRunner{

	@Autowired
	APICaller apiCaller;
	
	@Autowired
	TrackedEntityAttributeAndOptionsFetcher entityAndOptionsFetcher;
	
	@Autowired
	TrackedEntityTypeFetcher entityTypeFetcher;
	
	@Autowired
	ProgramFetcher programFetcher;
	
	@Autowired
	ProgramStageFetcher programStageFetcher;
	
	@Autowired
	DataElementFetcher dataElementFetcher;
	
	@Autowired
	APICaller openImisApiCaller;

	@Autowired
	OrganisationUnitFetcher organisationUnitFetcher;
	
	@Autowired
	RelationshipTypeFetcher relationshipTypeFetcher;
	/**
	 * Currently acts as the main function.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		final Logger logger = LoggerFactory.getLogger(Tester.class);
		
		entityAndOptionsFetcher.fetchAndCache();
		entityTypeFetcher.fetchAndCache();
		programFetcher.fetchAndCache();
		programStageFetcher.fetchAndCache();
		dataElementFetcher.fetchAndCache();
		organisationUnitFetcher.fetchAndCache();
		relationshipTypeFetcher.fetchAndCache();
		
		apiCaller.getLocationBundle(APIConfiguration.OPENIMIS_LOCATION_BUNDLE);
		
		apiCaller.getPatientBundleAndPostToDhis(APIConfiguration.OPENIMIS_PATIENT_BUNDLE + "&" + ParamsUtil.REF_DATE_PARAM);
		apiCaller.getCoverageBundle(APIConfiguration.OPENIMIS_COVERAGE_BUNDLE + "&" + ParamsUtil.REF_DATE_PARAM);	
		
		apiCaller.getClaimAndClaimResponseBundleAndPostToDhis(APIConfiguration.OPENIMIS_CLAIM_BUNDLE + "&" + ParamsUtil.REF_DATE_PARAM + "&" + ParamsUtil.CLAIM_PAGE_SIZE, 
				APIConfiguration.OPENIMIS_CLAIM_RESPONSE_BUNDLE + "&" + ParamsUtil.REF_DATE_PARAM+ "&" + ParamsUtil.CLAIM_PAGE_SIZE, 105);
		
		
		logger.info("----- Program execution finished! -----");
	}

}
