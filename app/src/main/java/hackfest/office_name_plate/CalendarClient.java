package hackfest.office_name_plate;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Collection;
import java.util.List;

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

    MeetingData RetrieveMeetingData(Date dateStart, Date dateEnd)
    {
        boolean success = false;
        // fetch next batch of events and select the first only
        ListenableFuture<java.util.List<Event>> events = client.getMe().getCalendarView().addParameter("startdatetime",dateStart).addParameter("enddatetime",dateEnd).read();
        Futures.addCallback(events, new FutureCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> result) {
                userMeetingData = new MeetingData();
                //fill this up
            }

            @Override
            public void onFailure(Throwable t) {
                userMeetingData = null;
            }
        });
        return userMeetingData;
    }

}
