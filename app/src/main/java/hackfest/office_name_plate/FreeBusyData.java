package hackfest.office_name_plate;

/**
 * Created by deepavr on 12/10/2014.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class FreeBusyData
{
    Date nextFree;
    ArrayList<MeetingData> meetingsList;

    FreeBusyData()
    {
        meetingsList = new ArrayList<MeetingData>();
    }

    ArrayList<MeetingData> GetCurrentMeetings()
    {
        return meetingsList;
    }

    Date GetNextFree()
    {
        return  nextFree;
    }
}
