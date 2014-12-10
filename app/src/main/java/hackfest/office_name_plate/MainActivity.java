package hackfest.office_name_plate;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Meeting[] meetings = new Meeting[] {
                new Meeting(new Date(114, 11, 9, 11, 0), new Date(2014, 12, 9, 12, 0), "Scrum meeting", "34/3806"),
                new Meeting(new Date(114, 11, 9, 13, 0), new Date(2014, 12, 9, 14, 0), "Triage", "34/3561"),
                new Meeting(new Date(114, 11, 9, 16, 0), new Date(2014, 12, 9, 17, 0), "Design review", "36/1108")};

        ListView listview = (ListView) findViewById(R.id.meetings);
        ArrayAdapter<Meeting> adapter = new ArrayAdapter<Meeting>(this, android.R.layout.simple_list_item_1, meetings);
        listview.setAdapter(adapter);
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
