package org.beehyv.dhis2openimis.adapter.fhir.pojo.general;

public class FHIRCoding {

    private String system;
    private String version;
    private String uri;
    private String display;
    private String code;
    
    public FHIRCoding() {
    	
    }
    
    public FHIRCoding( String system, String code ) {
        this.system = system;
        this.code = code;
    }
    public FHIRCoding(String system, String code, String uri) {
        this.system = system;
        this.code = code;
        this.uri = uri;
    }   
    public FHIRCoding(String system, String code, String uri, String display) {
        this.system = system;
        this.code = code;
        this.uri = uri;
        this.display = display;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String Code) {
        this.code = Code;
    }
    public String getUri() {
        return uri;
    }

    public void setUri(String Uri) {
        this.uri = Uri;
    }
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String Display) {
        this.display = Display;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return " [system=" + system + ", code="+code+", uri="+uri+", display="+display+", version="+version+"]";
    }
}
