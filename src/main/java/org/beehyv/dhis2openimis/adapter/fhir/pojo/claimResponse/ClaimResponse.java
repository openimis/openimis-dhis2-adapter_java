package org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.claimResponseItem.ClaimResponseAmount;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.claimResponseItem.ClaimResponseItem;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.claimResponse.claimResponseItem.ClaimResponseReason;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifier;

import java.time.LocalDate;
import java.util.List;

public class ClaimResponse {
    private String resourceType;
    private LocalDate created;
    private String id;
    private List<FHIRIdentifier> identifier;
    private List<ClaimResponseItem> item;
    private ClaimResponseReason outcome;
    private FHIRReference request;
    private ClaimResponseAmount totalBenefit;
    
    
    public ClaimResponse() {
		
	}

    
    public String getResourceType() {
        return resourceType;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<FHIRIdentifier> getIdentifier() {
        return identifier;
    }
    public void setIdentifier(List<FHIRIdentifier> identifier) {
        this.identifier = identifier;
    }
    public List<ClaimResponseItem> getItem() {
		return item;
	}
	public void setItem(List<ClaimResponseItem> item) {
		this.item = item;
	}
	public ClaimResponseReason getOutcome() {
        return outcome;
    }
    public void setOutcome(ClaimResponseReason outcome) {
        this.outcome = outcome;
    }
    public FHIRReference getRequest() {
        return request;
    }
    public void setRequest(FHIRReference request) {
        this.request = request;
    }
    public ClaimResponseAmount getTotalBenefit() {
		return totalBenefit;
	}
	public void setTotalBenefit(ClaimResponseAmount totalBenefit) {
		this.totalBenefit = totalBenefit;
	}


	@Override
	public String toString() {
		return "ClaimResponse [resourceType=" + resourceType + ", created=" + created + ", id=" + id + ", identifier="
				+ identifier + ", item=" + item + ", outcome=" + outcome + ", request=" + request + ", totalBenefit="
				+ totalBenefit + "]";
	}


	
}
