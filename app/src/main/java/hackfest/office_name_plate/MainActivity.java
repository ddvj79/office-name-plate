package hackfest.office_name_plate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private String accessToken;
    private CalendarClient calenderClient;

    private AlarmManager alarmManager;
    private PendingIntent refreshIntent;

    private UserProfileClient userProfileClient;
    private String userID;
    private String tenantID;
    private String dirAccessToken;

    Date currentStartTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTime();
    }

    Date currentEndTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,3);
        return calendar.getTime();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String name = intent.getStringExtra("USER_NAME");
        TextView userNameView = (TextView) findViewById(R.id.userName);
        userNameView.setText(name);

        accessToken = intent.getStringExtra("ACCESS_TOKEN");
        calenderClient = new CalendarClient(accessToken);
        final Date startTime = currentStartTime();
        final Date endTime = currentEndTime();

        calenderClient.RetrieveMeetingData(startTime,endTime, this);

        alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        final MainActivity mainActivity = this;

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive( Context context, Intent intent )
            {
                calenderClient.RetrieveMeetingData(startTime, endTime, mainActivity);
            }
        };
        this.registerReceiver( receiver, new IntentFilter("refresh") );

        PendingIntent refreshIntent = PendingIntent.getBroadcast( this, 0, new Intent("refresh"), 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, refreshIntent);

        /*//Getting user profile information
        dirAccessToken = intent.getStringExtra("DIR_ACCESS_TOKEN");
        userID = intent.getStringExtra("USER_OBJECT_ID");
        tenantID = intent.getStringExtra("TENANT_ID");
        userProfileClient = new UserProfileClient(dirAccessToken,userID,tenantID);
        userProfileClient.RetrieveUserProfile(this);*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
