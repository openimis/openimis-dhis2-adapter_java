package org.beehyv.dhis2openimis.adapter.util;


import java.util.Base64;

/**
 * @author Vishal
 */
public class APIConfiguration {

    public static String DHIS2_BASE_URL = "https://ln1.hispindia.org/openimis/api/";
    public static String DHIS2_TRACKED_ENTITY_TYPE_GET_URL = DHIS2_BASE_URL + "trackedEntityTypes?paging=false";
    public static String DHIS_ORGANISATION_UNITS_GET_URL = DHIS2_BASE_URL + "organisationUnits?paging=false&fields=id,displayName,code";
    public static String DHIS_TRACKED_ENTITY_TYPES_GET_URL = DHIS2_BASE_URL + "trackedEntityTypes";
    public static String DHIS_PROGRAMS_GET_URL = DHIS2_BASE_URL + "programs" + "?" + ParamsUtil.PAGER_PARAM + "&" + ParamsUtil.PROGRAM_FIELDS_PARAM;
    public static String DHIS_PROGRAM_STAGES_GET_URL = DHIS2_BASE_URL + "programStages" + "?" + ParamsUtil.PAGER_PARAM ;
    public static String DHIS_TRACKED_ENTITY_INSTANCES_URL = DHIS2_BASE_URL + "trackedEntityInstances";
    public static String DHIS_TRACKED_ENTITY_INSTANCES_QUERY_URL = DHIS2_BASE_URL + "trackedEntityInstances/query.json?";
    public static String DHIS_ENROLLMENT_POST_URL = DHIS2_BASE_URL + "enrollments";
    public static String DHIS_EVENTS_POST_URL = DHIS2_BASE_URL + "events.json";
    public static String DHIS_RELATIONSHIP_TYPES_URL = DHIS2_BASE_URL + "relationshipTypes" + "?" + ParamsUtil.PAGER_PARAM ;

    public static String OPENIMIS_BASE_URL = "https://links.hispindia.org/api/api_fhir/";
    public static String OPENIMIS_CLAIM_RESPONSE_GET_URL = OPENIMIS_BASE_URL + "ClaimResponse";
    public static String OPENIMIS_CLAIM_GET_URL = OPENIMIS_BASE_URL + "Claim";
    public static String OPENIMIS_PATIENT_BUNDLE = OPENIMIS_BASE_URL + "Patient/?format=json&_count=50";
    public static String OPENIMIS_CLAIM_BUNDLE = OPENIMIS_BASE_URL + "Claim/?format=json";
    public static String OPENIMIS_CLAIM_RESPONSE_BUNDLE = OPENIMIS_BASE_URL + "ClaimResponse/?format=json";
    public static String OPENIMIS_LOCATION_BUNDLE = OPENIMIS_BASE_URL + "Location/?format=json";
    public static String OPENIMIS_PRACTITIONER_BUNDLE = OPENIMIS_BASE_URL + "Practitioner/?format=json";
    public static String OPENIMIS_COVERAGE_BUNDLE = OPENIMIS_BASE_URL + "/Coverage/?format=json";

    public static String OPENIMIS_USER = "beehyv";
    public static String OPENIMIS_PASSWORD = "beehyv";
    public static String DHIS2_USER = "beehyv";
    public static String DHIS2_PASSWORD = "Dhis@123";
    
    
    
    public static String DHIS2_TRACKED_ENTITY_ATTRIBUTES_URL = APIConfiguration.DHIS2_BASE_URL + "trackedEntityAttributes?paging=false&fields=displayName,shortName,id,code,href,optionSet";
    public static String DHIS2_DATA_ELEMENT_URL = APIConfiguration.DHIS2_BASE_URL + "dataElements?paging=false&fields=displayName,shortName,id,code,href,optionSet";

    
    public static String getDhis2AuthorizationHeader() {
        return "Basic " + Base64.getEncoder().encodeToString((DHIS2_USER + ":" + DHIS2_PASSWORD).getBytes());
    }

    public static String getOpenIMISAuthorizationHeader() {
        return "Basic " + Base64.getEncoder().encodeToString((OPENIMIS_USER + ":" + OPENIMIS_PASSWORD).getBytes());
    }
    
    public static String getOptionsUrl(String optionSetId) {
    	return APIConfiguration.DHIS2_BASE_URL + "options?paging=false&fields=id,displayName,code&filter=optionSet.id:eq:" + optionSetId;
    }
    
    public static String getEventDetailsPostUrl(String eventId) {
    	return APIConfiguration.DHIS2_BASE_URL + "events/" + eventId;
    }
    
    public static String getEnrollmentIdForTrackedEntityInstance(String trackedEntityInstanceId) {
    	return APIConfiguration.DHIS2_BASE_URL + "trackedEntityInstances/" + trackedEntityInstanceId + "?fields=enrollments";
    }
    
    public static String getEventsForTrackedEntityInstanceQueryUrl(String trackedEntityInstanceId) {
    	return APIConfiguration.DHIS2_BASE_URL + "events?trackedEntityInstance=" + trackedEntityInstanceId + "&" + ParamsUtil.PAGER_PARAM;
    }
    
    public static String getEventDeleteUrl(String eventId) {
    	return APIConfiguration.DHIS2_BASE_URL + "events/" + eventId;
    }

	public static String getEnrollmentDataFromEnrollmentIdUrl(String enrollmentId) {
		return APIConfiguration.DHIS2_BASE_URL + "enrollments/" + enrollmentId;
	}
}
