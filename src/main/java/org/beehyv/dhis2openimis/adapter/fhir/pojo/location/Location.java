package org.beehyv.dhis2openimis.adapter.fhir.pojo.location;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.Address;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.TelecomDetail;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifier;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRCoding;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifierTypeCodename;
import java.util.List;

/**
 * @author HISP India  & Delcroix Patrick
 */

public class Location {
    private String resourceType;
    private Address address;
    private String id;
    private List<FHIRIdentifier> identifier;
    private String name;
    private List<TelecomDetail> telecom;
    private List<LocationType> type;
    private LocationType physicalType;
    private FHIRReference partOf;
    private int level;
    
    public Location() {
		
	}
    
    
    public String getResourceType() {
        return resourceType;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<TelecomDetail> getTelecom() {
        return telecom;
    }
    public void setTelecom(List<TelecomDetail> telecom) {
        this.telecom = telecom;
    }
    public List<LocationType> getType() {
        return type;
    }
    public void setType(List<LocationType> type) {
        this.type = type;
    }

    public FHIRReference getPartOf(){
        return partOf;
    }

    public void setPartOf(FHIRReference partOf){
        this.partOf = partOf;
    }

    public String getParentId(){
        if(this.partOf != null ){
            return this.partOf.getIdentifier().getValue();
        }else{
            return null;
        }

    }

    public String getParentCode(){
        if(this.partOf != null ){
            return this.partOf.getDisplay();
        }else{
            return null;
        }

    }
    
    public String getCode(){ 
	    for (FHIRIdentifier id : identifier) {
            for (FHIRCoding coding : id.getType().getCoding()){
                if (coding.getCode().equals(FHIRIdentifierTypeCodename.LC.name())){
                    return id.getValue();
                }
	        }
	    }
	    return null;
    }

    @Override
    public String toString() {
        return "Location [resourceType=" + resourceType + ", address=" + address + ", id=" + id
                + ", identifiers=" + identifier + ", name=" + name + ", telecom=" + telecom +", type=" + type+ ", partOf=" + partOf+ "]\n";
    }
}
