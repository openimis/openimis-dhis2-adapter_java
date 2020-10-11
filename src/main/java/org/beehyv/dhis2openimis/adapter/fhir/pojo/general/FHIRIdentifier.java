package org.beehyv.dhis2openimis.adapter.fhir.pojo.general;


public class FHIRIdentifier {
	private FHIRCodeableConcept type;
	private String use;
	private String value;
	
	public FHIRIdentifier() {
		
	}
	
	public FHIRCodeableConcept getType() {
		return type;
	}
	public void setType(FHIRCodeableConcept  type) {
		this.type = type;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Identifier [type=" + type + ", use=" + use + ", value=" + value + "]";
	}
	
}
