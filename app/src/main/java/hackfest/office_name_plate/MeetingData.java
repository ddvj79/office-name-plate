package hackfest.office_name_plate;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by deepavr on 12/10/2014.
 */
public class MeetingData
{
    Date startTime;
    Date endTime;
    String location;

    MeetingData()
    {

    }

    MeetingData(Date start, Date end, String meetingLocation)
    {
        this.location = meetingLocation;
        this.startTime = start;
        this.endTime = end;
    }

    Date GetStartTime()
    {
        return startTime;
    }

    Date GetEndTime()
    {
        return endTime;
    }

    String GetLocation()
    {
        return  location;
    }
}
