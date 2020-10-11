package org.beehyv.dhis2openimis.adapter.fhir.pojo.coverage;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;

import java.util.List;

/**
 * @author Vishal
 */
public class Agent {

    private List<Role> role;
    private FHIRReference actor;

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public FHIRReference getActor() {
        return actor;
    }

    public void setActor(FHIRReference actor) {
        this.actor = actor;
    }
}
