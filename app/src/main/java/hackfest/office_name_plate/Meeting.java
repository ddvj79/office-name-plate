package hackfest.office_name_plate;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dmcallis on 12/10/2014.
 */
public class Meeting {

    private Date startDate;
    private Date endDate;
    private String subject;
    private String location;

    public Meeting (Date start, Date end, String subject, String location)
    {
        startDate = start;
        endDate = end;
        this.subject = subject;
        this.location = location;
    }

    @Override
    public String toString() {
        Format formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(startDate) + "   " + subject + "   " + location;
    }
}
