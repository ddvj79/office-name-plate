package hackfest.office_name_plate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;

/**
 * Created by dmcallis on 12/10/2014.
 */
public class Meeting implements Comparable<Meeting>{

    private Date startDate;
    private Date endDate;
    private String subject;
    private String location;
    private String category;

    public Meeting (Date start, Date end, String subject, String location, String category)
    {
        startDate = start;
        endDate = end;
        this.subject = subject;
        this.location = location;
        this.category = category;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getLocation()
    {
        return location;
    }

    @Override
    public int compareTo(Meeting o) {
        return getStartDate().compareTo(o.getStartDate());
    }

    @Override
    public String toString() {
        Format formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(startDate) + "   " + subject + "   " + location;
    }
}
