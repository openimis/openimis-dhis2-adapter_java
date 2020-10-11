package org.beehyv.dhis2openimis.adapter.fhir.pojo.claim.diagnosis;

public class DiagnosisType {

    private String text;

    public DiagnosisType() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "DiagnosisType [text=" + text + "]";
    }
}
