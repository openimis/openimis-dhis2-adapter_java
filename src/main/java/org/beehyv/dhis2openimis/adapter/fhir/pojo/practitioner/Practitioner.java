package org.beehyv.dhis2openimis.adapter.fhir.pojo.practitioner;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.NameDetail;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRIdentifier;

import java.util.List;

public class Practitioner {

    private String resourceType;
    private String birthDate;
    private String id;
    private List<FHIRIdentifier> identifier;
    private NameDetail name;

    public Practitioner(String resourceType, String birthDate, String id, List<FHIRIdentifier> identifier, NameDetail name) {
        this.resourceType = resourceType;
        this.birthDate = birthDate;
        this.id = id;
        this.identifier = identifier;
        this.name = name;
    }

    public Practitioner() {
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public NameDetail getName() {
        return name;
    }

    public void setName(NameDetail name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Practitioner [resourceType=" + resourceType + ", birthdate=" + birthDate + ", id=" + id
                + ", identifiers=" + identifier + ", name=" + name + "]\n";
    }
}
