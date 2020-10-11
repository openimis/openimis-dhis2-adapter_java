package org.beehyv.dhis2openimis.adapter.fhir.pojo.general;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRCodeableConcept;
import org.beehyv.dhis2openimis.adapter.fhir.pojo.general.FHIRReference;

/**
 * @author Delcroix Patrick
 */
public class FHIRPeriod {

    private Date start;
    private Date end;

    public String getStart() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(start); 
    }
    public void setStart(String start) throws ParseException {
        this.start = new SimpleDateFormat("yyyy-MM-dd").parse(start);
    }
    public String getEnd() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(end); 
    }
    public void setEnd(String end) throws ParseException{
        this.end = new SimpleDateFormat("yyyy-MM-dd").parse(end);
    }
}
