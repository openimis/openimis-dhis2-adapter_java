package org.beehyv.dhis2openimis.adapter.dhis.cache;

import org.beehyv.dhis2openimis.adapter.dhis.pojo.relationship_type.RelationshipType;
import org.beehyv.dhis2openimis.adapter.dhis.pojo.relationship_type.RelationshipTypeBundle;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vishal
 */
@Service
public class RelationshipTypeCache {

    private Map<String, String> cache;

    public RelationshipTypeCache() {
        cache = new HashMap<String, String>();
    }


    public void save(RelationshipTypeBundle bundle) {
        List<RelationshipType> types = bundle.getRelationshipTypes();
        for(RelationshipType type : types) {
            cache.put(type.getDisplayName(), type.getId());
        }
    }

    public String getByDisplayName(String displayName) {
        return cache.get(displayName);
    }
}
