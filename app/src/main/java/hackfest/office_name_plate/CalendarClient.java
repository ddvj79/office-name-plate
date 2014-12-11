package hackfest.office_name_plate;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.*;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.microsoft.outlookservices.Event;
import com.microsoft.outlookservices.odata.OutlookClient;
import com.microsoft.services.odata.impl.DefaultDependencyResolver;

/**
 * Created by deepavr on 12/10/2014.
 */
public class CalendarClient
{
    public static String ENDPOINT_ID = "https://outlook.office365.com/api/v1.0";

    OutlookClient calendarClient;
    MeetingData userMeetingData;
    String authToken;
    DefaultDependencyResolver dependencyResolver;
    OutlookClient client;

    CalendarClient(String authTokenToUse)
    {
        this.authToken = authTokenToUse;
        dependencyResolver = new DefaultDependencyResolver(authTokenToUse);
        client = new OutlookClient(ENDPOINT_ID, dependencyResolver);
    }

    MeetingData RetrieveMeetingData(Date dateStart, Date dateEnd, final MainActivity activity)
    {
        boolean success = false;
        // fetch next batch of events and select the first only
        //ListenableFuture<java.util.List<Event>> events = client.getMe().getCalendarView().addParameter("startdatetime",dateStart).addParameter("enddatetime",dateEnd).read();
        ListenableFuture<List<Event>> events = client.getMe().getCalendar().getEvents().read();
        Futures.addCallback(events, new FutureCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> result) {
                ArrayList<Meeting> meetings = new ArrayList<Meeting>();
                for (Event e : result) {
                    Date startTime = e.getStart().getTime();
                    Date endTime = e.getEnd().getTime();
                    Calendar c = Calendar.getInstance();
                    Date currentTime = c.getTime();
                    c.setTime(currentTime);
                    c.add(Calendar.DAY_OF_WEEK,1);
                    Date boundary = c.getTime();
                    if( endTime.compareTo(currentTime) >=0 && startTime.compareTo(boundary) <=0  )
                    {
                        Meeting meeting = new Meeting(e.getStart().getTime(), e.getEnd().getTime(), e.getSubject(), e.getLocation().getDisplayName());
                        meetings.add(meeting);
                    }
                }
                Collections.sort(meetings);

                final Meeting[] meetingsArray = (Meeting[])meetings.toArray(new Meeting[meetings.size()]);

                final String nextFreeTime = GetNextFreeTime(meetingsArray);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView meetingView = (ListView) activity.findViewById(R.id.meetings);
                        ArrayAdapter<Meeting> adapter = new ArrayAdapter<Meeting>(activity, android.R.layout.simple_list_item_1, meetingsArray);
                        meetingView.setAdapter(adapter);

                        TextView nextFreeTimeView = (TextView) activity.findViewById(R.id.nextFree);
                        nextFreeTimeView.setText("Next Free at:  " + nextFreeTime);
                    }
                });



            }

            @Override
            public void onFailure(Throwable t)
            {
                userMeetingData = null;
            }
        });

        return userMeetingData;
    }

    String GetNextFreeTime(Meeting[] meetings)
    {
        Format formatter = new SimpleDateFormat("h:mm a");
        Calendar c = Calendar.getInstance();
        Date currentTime = c.getTime();
        String nextFreeTime = "";
        if (currentTime.compareTo(meetings[0].getStartDate()) < 0)
        {
            nextFreeTime = "Now";
        }
        else
        {
            Date previousMeetingDateEnd = currentTime;
            for (Meeting meeting : meetings)
            {
                if (previousMeetingDateEnd.compareTo(meeting.getStartDate()) < 0)
                {
                    nextFreeTime = formatter.format(previousMeetingDateEnd);
                    break;
                }
                if (previousMeetingDateEnd.compareTo(meeting.getEndDate()) < 0)
                {
                    previousMeetingDateEnd = meeting.getEndDate();
                }
            }
            if (nextFreeTime == "")
            {
                nextFreeTime = formatter.format(previousMeetingDateEnd);
            }
        }

        return nextFreeTime;
    }



}
