package org.beehyv.dhis2openimis.adapter.fhir;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.beehyv.dhis2openimis.adapter.dhis.claim.ClaimFacade;
import org.beehyv.dhis2openimis.adapter.dhis.insuree.InsureeFacade;
import org.beehyv.dhis2openimis.adapter.dhis.insuree.policy_details.PolicyDetailsFacade;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitsDhis2Bundle;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitAttribute;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitAttributeRequest;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitAttributeValue;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitAttributeValueRequest;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitDhis2;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitDhis2Request;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitParentRequest;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.organisation.OrganisationUnitPostResponse;
import org.beehyv.dhis2openimis.adapter.dhis.cache.org_unit.OrganisationUnitCacheService;
import org.beehyv.dhis2openimis.adapter.dhis.fetch.OrganisationUnitFetcher;


import org.beehyv.dhis2openimis.adapter.fhir.cacheService.*;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.Bundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.LinkDetail;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.Claim;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.ClaimBundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.ClaimResource;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.ClaimResponse;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.ClaimResponseBundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.ClaimResponseResource;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.FHIRContract;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.ContractBundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.contract.ContractResource;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.location.Location;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.location.LocationBundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.location.LocationResource;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.patient.Patient;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.patient.PatientBundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.patient.PatientResource;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.practitioner.Practitioner;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.practitioner.PractitionerBundle;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.practitioner.PractitionerResource;
import org.beehyv.dhis2openimis.adapter.util.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

/**
 * @author HISP India  & Delcroix Patrick
 */

@Component
public class APICaller {
	
	@Autowired
	OrganisationUnitFetcher organisationUnitFetcher;

	@Autowired
    private OrganisationUnitCacheService orgUnitCache;

	private static final Logger logger = LoggerFactory.getLogger(APICaller.class);
	
	private PatientCacheService patientCacheService;
	private ClaimCacheService claimCacheService;
	private ClaimResponseCacheService claimResponseCacheService;
	private LocationCacheService locationCacheService;
	private PractitionerCacheService practitionerCacheService;
	private FHIRContractCacheService FHIRContractCacheService;
	private HttpEntity<Void> request;
	private InsureeFacade insureeFacade;
	private PolicyDetailsFacade policyDetailsFacade;
	private ClaimFacade claimFacade;
	@Value("${app.dhis2.api.OrganisationUnits}")
    String dhis2GetURL;
	@Value("${app.dhis2.username}")
	String DHIS2Username;
	@Value("${app.dhis2.password}")
	String DHIS2Password;

        
	private RestTemplate restTemplate;  
	private HttpHeaders authHeadersDHIS2;
	
	@Autowired
	public APICaller(PatientCacheService patientCacheService, ClaimCacheService claimCacheService,
					 ClaimResponseCacheService claimResponseCacheService, LocationCacheService locationCacheService,
					 PractitionerCacheService practitionerCacheService, FHIRContractCacheService FHIRContractCacheService,
					 @Qualifier("OpenImis") HttpEntity<Void> request, InsureeFacade insureeFacade, 
					 PolicyDetailsFacade policyDetailsFacade, ClaimFacade claimFacade,@Qualifier("Dhis2")HttpHeaders authHeadersDHIS2) {
		this.patientCacheService = patientCacheService;
		this.claimCacheService = claimCacheService;
		this.claimResponseCacheService = claimResponseCacheService;
		this.locationCacheService = locationCacheService;
		this.practitionerCacheService = practitionerCacheService;
		this.FHIRContractCacheService = FHIRContractCacheService;
		this.request = request;
		this.insureeFacade = insureeFacade;
		this.policyDetailsFacade = policyDetailsFacade;
		this.claimFacade = claimFacade;
		this.authHeadersDHIS2 = authHeadersDHIS2;
		this.authHeadersDHIS2.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((this.DHIS2Username + ":" + this.DHIS2Password).getBytes()));
	}
	
	public void getContractBundle(String url) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<ContractBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, ContractBundle.class);
		ContractBundle contractBundle = response.getBody();

		FHIRContractCacheService.clear();
		fillInCacheContract(contractBundle);
		postConractToDhis();
		
		try {
			String nextPageUrl = getNextPageUrl(contractBundle);
			logger.info("Fetching Coverage bundle from url :" + nextPageUrl);
			getContractBundle(nextPageUrl);
		} catch (NoSuchElementException e) {
			logger.info("---- Imis Coverage data export completed ----");
		}

	}

	public void getPatientBundleAndPostToDhis(String url) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<PatientBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, PatientBundle.class);
		PatientBundle patientBundle = response.getBody();
		
		patientCacheService.clear();
		fillInCachePatient(patientBundle);
		//new Thread(() -> {

			postPatientsToDhis();
		//}).start();
		
		
		
		try {
			String nextPageUrl = getNextPageUrl(patientBundle);
			logger.info("Fetching Patient bundle from url :" + nextPageUrl);
			getPatientBundleAndPostToDhis(nextPageUrl);
		} catch (NoSuchElementException e) {
			logger.info("--- Imis Patients data export completed ---");
		}
		
	}

//	public void getClaimBundle(String url) {
//		RestTemplate restTemplate = new RestTemplate();
//
//		ResponseEntity<ClaimBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, ClaimBundle.class);
//		ClaimBundle claimBundle = response.getBody();
//		fillInCacheClaim(claimBundle);
//		
//		try {
//			String nextPageUrl = getNextPageUrl(claimBundle);
//			getClaimBundle(nextPageUrl);
//		} catch (NoSuchElementException e) {
//			System.out.println("Claim data extraction finished!");
//		}
//
//	}
//
//	public void getClaimResponseBundle(String url) {
//		RestTemplate restTemplate = new RestTemplate();
//
//		ResponseEntity<ClaimResponseBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, ClaimResponseBundle.class);
//		ClaimResponseBundle claimResponseBundle = response.getBody();
//		fillInCacheClaimResponse(claimResponseBundle);
//		
//		try {
//			String nextPageUrl = getNextPageUrl(claimResponseBundle);
//			getClaimResponseBundle(nextPageUrl);
//		} catch (NoSuchElementException e) {
//			System.out.println("Claim Response data extraction finished!");
//		}
//
//	}
	
	public void getClaimBundle(String url) {
			
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<ClaimBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, ClaimBundle.class);
		ClaimBundle claimBundle = response.getBody();
		//ClaimCacheService.clear();
		fillInCacheClaim(claimBundle);

		try {
			String nextPageUrl = getNextPageUrl(claimBundle);
			logger.info("Fetching Coverage bundle from url :" + nextPageUrl);
			getClaimBundle(nextPageUrl);
		} catch (NoSuchElementException e) {
			logger.info("---- Imis claim data export completed ----");
		}

	}
	
	public void getClaimResponseBundle(String url) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<ClaimResponseBundle> crResponse = restTemplate.exchange(url, HttpMethod.GET, request, ClaimResponseBundle.class);
		ClaimResponseBundle claimResponseBundle = crResponse.getBody();
		//claimResponseCacheService.clear();
		fillInCacheClaimResponse(claimResponseBundle);
		try {
			String nextPageUrl = getNextPageUrl(claimResponseBundle);
			logger.info("Fetching Coverage bundle from url :" + nextPageUrl);
			getClaimResponseBundle(nextPageUrl);
		} catch (NoSuchElementException e) {
			logger.info("---- Imis ClaimResponse data export completed ----");
		}

	}
	public void getClaimAndClaimResponseBundleAndPostToDhis(String claimUrl, String claimResponseUrl) {
		//RestTemplate restTemplate = new RestTemplate();
		
		claimResponseCacheService.clear();
		claimCacheService.clear();
		try {
			String currentClaimUrl = claimUrl ;
			String currentClaimResponseUrl = claimResponseUrl;
			logger.info("Fetching Claim and ClaimResponse bundle from url :" + currentClaimUrl);
			getClaimResponseBundle(currentClaimUrl);
			getClaimBundle(currentClaimResponseUrl);
			postClaimsToDhis();
		} catch(Exception e) {
			logger.error(e.getMessage());
			logger.error("Claim/ClaimResponse fetch failed");
		}
	
	}
	public void getLocationBundleAndPostToDHIS2(String url, String url2){
		locationCacheService.clear();
		getLocationBundle(url);
		getLocationBundle(url + "&physicalType=si");
		postOrgUnitToDhis2(url2);
	}



	public void getLocationBundle(String url) {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<LocationBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, LocationBundle.class);
		LocationBundle locationBundle = response.getBody();

		fillInCacheLocation(locationBundle);
		try {
			String nextPageUrl = getNextPageUrl(locationBundle);
			getLocationBundle(nextPageUrl);
		} catch (NoSuchElementException e) {
			logger.info("Location Response data extraction finished!");
		}

	}

	public void getPractitionerBundle(String url) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<PractitionerBundle> response = restTemplate.exchange(url, HttpMethod.GET, request, PractitionerBundle.class);
		PractitionerBundle practitionerBundle = response.getBody();

		fillInCachePractitioner(practitionerBundle);

		try {
			String nextPageUrl = getNextPageUrl(practitionerBundle);
			getPractitionerBundle(nextPageUrl);
		} catch (NoSuchElementException e) {
			System.out.println("Practitioner Response data extraction finished!");
		}

	}


	
	private void fillInCachePatient(PatientBundle patientBundle) {
		List<PatientResource> patientResources = patientBundle.getEntry();
		if( patientResources != null && !patientResources.isEmpty()){
			List<Patient> patients = patientResources.stream().map(p -> p.getResource()).collect(Collectors.toList());
			patientCacheService.save(patients);
		}
	}

	private void fillInCacheContract(ContractBundle contractBundle) {
		List<ContractResource> contractResources = contractBundle.getEntry();
		if( contractResources != null && !contractResources.isEmpty()){
			List<FHIRContract> contracts = contractResources.stream().map(p -> p.getResource()).collect(Collectors.toList());

			FHIRContractCacheService.save(contracts);
		}
	}

	private void fillInCacheClaim(ClaimBundle claimBundle) {
		List<ClaimResource> claimResources = claimBundle.getEntry();
		if(claimResources != null && !claimResources.isEmpty()){
			List<Claim> claims = claimResources.stream().map(p -> p.getResource()).collect(Collectors.toList());
			claimCacheService.save(claims);
		}
	}

	private void fillInCacheClaimResponse(ClaimResponseBundle claimResponseBundle) {
		List<ClaimResponseResource> claimResponseResources = claimResponseBundle.getEntry();
		if(claimResponseResources != null && !claimResponseResources.isEmpty()){
			List<ClaimResponse> claimResponses = claimResponseResources.stream().map(p -> p.getResource()).collect(Collectors.toList());
			claimResponseCacheService.save(claimResponses);
		}
	}

	private void fillInCacheLocation(LocationBundle locationBundle) {
		List<LocationResource> locationResources = locationBundle.getEntry();
		if(locationResources != null && !locationResources.isEmpty()){
			List<Location> locations = locationResources.stream().map(p -> p.getResource()).collect(Collectors.toList());
			locationCacheService.save(locations);
		}
	}

	private void fillInCachePractitioner(PractitionerBundle practitionerBundle) {
		List<PractitionerResource> practitionerResources = practitionerBundle.getEntry();
		if(practitionerResources != null && !practitionerResources.isEmpty()){
			List<Practitioner> practitioners = practitionerResources.stream().map(p -> p.getResource()).collect(Collectors.toList());
			practitionerCacheService.save(practitioners);
		}
	}
	
	
	private String getNextPageUrl(Bundle bundle) {
		List<LinkDetail> links = bundle.getLink();
		for(LinkDetail link : links) {
			if(link.getRelation().equals("next")) {
				return link.getUrl();
			}
		}
		throw new NoSuchElementException();
	}
	
	private void postPatientsToDhis() {
		Collection<Patient> patients = patientCacheService.getAllPatients();
		Iterator<Patient> iterator = patients.iterator();
				// while loop
		while (iterator.hasNext()) {
		//for(Patient patient : patients) {
			Patient patient = iterator.next();
			try {
				
				//logger.info("InsureeFacade.adaptAndPost called for Patient: " + patient.getId());
				insureeFacade.adaptAndPost(patient);
				//logger.info("InsureeFacade.adaptAndPost successfully finished Patient: " + patient.getId());
			} catch(Exception e) {
				logger.error("Error occured during adding data for Patient: '" + patient.getId() + "'. Reason: " + e.getMessage());
			}
		}
	}
	
	private void postConractToDhis() {
		Collection<FHIRContract> contracts = FHIRContractCacheService.getAllContracts();
		//for(FHIRContract contract : contracts) {
		//	try {
		Iterator<FHIRContract> iterator = contracts.iterator();
				// while loop
		while (iterator.hasNext()) {
		//for(Patient patient : patients) {
			FHIRContract contract = iterator.next();
			try {
				
				//logger.info("PolicyDetailsFacade.adaptAndPost called for Coverage: " + coverage.getIdentifier().get(0).getValue());
				policyDetailsFacade.adaptAndPost(contract);
				//logger.info("PolicyDetailsFacade.adaptAndPost successfully finished for Coverage: " + coverage.getIdentifier().get(0).getValue());
			} catch(Exception e) {
				logger.error("Error occured during adding data for Contract: '" + contract.getIdentifier().get(0).getValue() + "'. Reason: " + e.getMessage());//+ ", Stack: " +getStackTrace(e)); 
			}
		}
	}
	
	private void postClaimsToDhis() {
		Collection<ClaimResponse> crs = claimResponseCacheService.getAllClaimResponses();
		Iterator<ClaimResponse> iterator = crs.iterator();
		//for(ClaimResponse cr: crs) {
		while (iterator.hasNext()) {
			ClaimResponse cr = iterator.next();
			try {
				
				logger.info("ClaimFacade.adaptAndPost called for Claim: '" + cr.getId() + "'");
				claimFacade.adaptAndPost(cr);
				logger.info("ClaimFacade.adaptAndPost successfully finished for Claim: '" + cr.getId() + "'");
			} catch(Exception e) {
				logger.error("Error occured during adding data for Claim: '" + cr.getId() + "' . Reason: " + e.getMessage() + ", Stack: " +getStackTrace(e));
			}
		}
	}

   public void postOrgUnitToDhis2(String url){

            //System.out.println( "imisClaimResponseUrl -- " + url );
			try{
				 locationCacheService.assignLevel();
			} catch(Exception e) {
				logger.error("Error assigning the Location level");
			}
           
            Collection<Location> level2OrgUnits = locationCacheService.getL2Locations();
            Collection<Location> level3OrgUnits = locationCacheService.getL3Locations();
            Collection<Location> level4OrgUnits = locationCacheService.getL4Locations();
            Collection<Location> level5OrgUnits = locationCacheService.getL5Locations();
            //System.out.println( legacyOpenIMISLocationArray.length );
           
            logger.info( level2OrgUnits.size() + " -- " + level3OrgUnits.size() + " -- " + level4OrgUnits.size() + " -- " + level5OrgUnits.size() );
            
            String dhis2PostURL = dhis2GetURL;
            
            //System.out.println( "dhis2URL -- " + dhis2PostURL );
            
            String dhis2URL = dhis2GetURL + "?" + ParamsUtil.ORG_UNITS_SYNC_PARAM;


            // for level-2
           // logger.info("Posting Level - 2 -- " + + level2OrgUnits.size() );
            createOrgObjectAndPostToDhis2( 2,  dhis2PostURL, level2OrgUnits  );
            organisationUnitFetcher.fetchAndCache();             
            //// for level-3
            //logger.info("Posting Level - 3 -- " + + level3OrgUnits.size() );
            createOrgObjectAndPostToDhis2( 3,  dhis2PostURL, level3OrgUnits  );
            organisationUnitFetcher.fetchAndCache(); 
            // level -4
            //   logger.info("Posting Level - 4 -- " + level4OrgUnits.size() );
            createOrgObjectAndPostToDhis2( 4,  dhis2PostURL, level4OrgUnits );
            organisationUnitFetcher.fetchAndCache(); 
            // level -5
            //logger.info("Posting Level - 5 -- " + level5OrgUnits.size());
		   createOrgObjectAndPostToDhis2( 5,  dhis2PostURL, level5OrgUnits );
		   organisationUnitFetcher.fetchAndCache(); 
           
	}

	private void createOrgObjectAndPostToDhis2( int level, String dhis2PostURL, Collection<Location> openIMISLocationList) {
		try {	
			
			if( openIMISLocationList !=null && openIMISLocationList.size() > 0 ){
				Iterator<Location> iterator = openIMISLocationList.iterator();
				// while loop
				while (iterator.hasNext()) {
				//System.out.println( leval2ParentId + " --  " + locationIdCodeId + " -- " + locationTypeCodeId + " -- " + dhis2OrgUnitsCodeList.size() + " -- " + orgUnitParentIdMap.size() );
				//for(LegacyOpenIMISLocation openIMISLocation : openIMISLocationList ) {
					Location openIMISLocation = iterator.next();
					// check if the org unit is not known in DHIS2
					if(orgUnitCache.getByCodeSilent(openIMISLocation.getCode()) == null){
						OrganisationUnitDhis2Request organisationUnitDhis2Request = new OrganisationUnitDhis2Request();
						organisationUnitDhis2Request.setName(openIMISLocation.getName());
						organisationUnitDhis2Request.setCode(openIMISLocation.getCode());
						organisationUnitDhis2Request.setShortName(openIMISLocation.getName());
						//organisationUnitDhis2Request.setOpeningDate(LocalDate.now().toString());
						//LocalDate date = LocalDate.parse("1990-01-01");
						organisationUnitDhis2Request.setOpeningDate(LocalDate.of(1990, 1, 1).toString());
						OrganisationUnitParentRequest orgUnitParent = new OrganisationUnitParentRequest();

						if( openIMISLocation.getParentCode() == null ){
							orgUnitParent.setId(orgUnitCache.getByCodeSilent(ParamsUtil.ORG_UNITS_ROOT_CODE));
						}else{
							// FIXME error handling in case the parent is not found
							orgUnitParent.setId(orgUnitCache.getByCodeSilent(openIMISLocation.getParentCode()));
						}
						organisationUnitDhis2Request.setParent( orgUnitParent );
						List<OrganisationUnitAttributeValueRequest> attributeValues = new ArrayList<>();
						fillLocationOrgAttrValue(attributeValues, ParamsUtil.ORG_UNITS_ATT_LOCID_CODE, openIMISLocation.getCode());
						fillLocationOrgAttrValue(attributeValues, ParamsUtil.ORG_UNITS_ATT_TYPE_CODE, openIMISLocation.getType().get(0).getCoding().get(0).getCode());
						organisationUnitDhis2Request.setAttributeValues(attributeValues);
						if( organisationUnitDhis2Request.getParent().getId() != null ){
							//logger.info(" Pushing at Level " + level + " to dhis2 with code - " + openIMISLocation.getCode() );
							OrganisationUnitPostResponse postOrgResponse = postToDhis2(dhis2PostURL, organisationUnitDhis2Request );
							//fixme, need to add the OrgUnit on the cache service
							///logger.info( "post response -- " + postOrgResponse.getResponse() );
							//System.out.println( "post response -- " + postOrgResponse.getResponse() );

						}
						else{
								logger.info(" Parent not present in openIMIS  at Level " + level + " with code - " + openIMISLocation.getCode() );
						}
					}
					else{
						//logger.info("Location at Level " + level + " present in dhis2 with code - " + openIMISLocation.getCode() );
					}
					}
			}
			else{
					logger.info("Location at Level " + level + " not present " );
			}
		} catch(NullPointerException e) {
				//Do nothing as no proper mapping was found.
		}
	}  

	private OrganisationUnitPostResponse postToDhis2(String dhis2PostURL, OrganisationUnitDhis2Request organisationUnitDhis2RequestList) {

		restTemplate = new RestTemplate();

		//final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		//final HttpClient httpClient = HttpClientBuilder.create()
		//											   .setRedirectStrategy(new LaxRedirectStrategy())
		//											   .build();
		//factory.setHttpClient(httpClient);
		//restTemplate.setRequestFactory(factory);


		HttpEntity<OrganisationUnitDhis2Request> postRRequestDHIS2 = new HttpEntity<>(organisationUnitDhis2RequestList, this.authHeadersDHIS2);
		
		//logger.debug("Posting orgUnit: " + organisationUnitDhis2RequestList.toString() + " to url: " + dhis2PostURL );
		ResponseEntity<OrganisationUnitPostResponse> response = restTemplate.exchange(dhis2PostURL, HttpMethod.POST, postRRequestDHIS2, OrganisationUnitPostResponse.class);

		return response.getBody();
	}
        
	private void fillLocationOrgAttrValue(List<OrganisationUnitAttributeValueRequest> attributeValues, String locationIdCodeId, String attributeValue) {
		try {
				OrganisationUnitAttributeRequest attribute = new OrganisationUnitAttributeRequest();
				attribute.setId(locationIdCodeId);
				
				OrganisationUnitAttributeValueRequest orgAttributeValue = new OrganisationUnitAttributeValueRequest(attribute, attributeValue);

				attributeValues.add(orgAttributeValue);
		} catch(NullPointerException e) {
				//Do nothing as no proper mapping was found.
		}
	}

	private static String getStackTrace(Exception ex) {
		StringBuffer sb = new StringBuffer(500);
		StackTraceElement[] st = ex.getStackTrace();
		sb.append(ex.getClass().getName() + ": " + ex.getMessage() + "\n");
		for (int i = 0; i < st.length; i++) {
		  sb.append("\t at " + st[i].toString() + "\n");
		}
		return sb.toString();
	}
}

