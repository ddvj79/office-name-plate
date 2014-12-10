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

    public static class Authenticate extends Activity {

        AuthenticationContext mAuthContext;
        public static final String RESOURCE = "https://outlook.office365.com";
        public static final String CLIENT_ID = "3d4b19fa-eeb8-4914-ba4e-5f11f69e9a68";
        public static final String REDIRECT_URI = "http://officenameplate";
        public static String ACCESS_TOKEN;
        public static String REFRESH_TOKEN;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            final String AUTHORITY = "https://login.windows.net/common";
            final boolean VALIDATE_AUTHORITY = false;
            final Context context = getApplicationContext();
            try
            {
                mAuthContext = new AuthenticationContext(context, AUTHORITY, VALIDATE_AUTHORITY);

            }
            catch(NoSuchAlgorithmException e) {
                throw new RuntimeException("Error creating authentication context", e);
            }
            catch(NoSuchPaddingException e) {
                throw new RuntimeException("Error creating authentication context", e);
            }
            startAuthentication();
            //Button mGetAccessTokenButton = (Button) findViewById(R.id.get_access_token);
            //Button mRefreshTokenButton = (Button) findViewById(R.id.refresh_access_token);


        }

        public void get_access_tokenOnClick(View v)
        {
            startAuthentication();
        }

        public void auth_refresh_tokenOnClick(View v)
        {
            startAuthenticationWithRefreshToken();
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

        private void startAuthentication()
        {
            mAuthContext.acquireToken(this, RESOURCE, CLIENT_ID, REDIRECT_URI, PromptBehavior.Always,
                    new AuthenticationCallback<AuthenticationResult>() {
                        @Override
                        public void onSuccess(AuthenticationResult authenticationResult) {
                            handleSuccess(authenticationResult);
                        }

                        @Override
                        public void onError(Exception e) {
                            handleError(e.toString());
                        }
                    }


            );
        }

        private void startAuthenticationWithRefreshToken()
        {
            if(REFRESH_TOKEN == null ||  REFRESH_TOKEN.isEmpty())
            {

                Toast.makeText(this, "No cached user ID", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuthContext.acquireTokenByRefreshToken(REFRESH_TOKEN, CLIENT_ID, RESOURCE,
                    new AuthenticationCallback<AuthenticationResult>() {
                        @Override
                        public void onSuccess(AuthenticationResult authenticationResult) {
                            handleSuccess(authenticationResult);
                        }

                        @Override
                        public void onError(Exception e) {
                            handleError(e.toString());
                        }
                    }

            );
        }

        private void handleSuccess(AuthenticationResult authenticationResult)
        {
            String message = String.format("User Name: %1$s\nExpires on %2$s\nAccess Token:%3$s\nRefresh Token:%4$s....",
                    authenticationResult.getUserInfo().getGivenName().toString(),
                    authenticationResult.getExpiresOn().toString(),
                    authenticationResult.getAccessToken().substring(0,10),
                    authenticationResult.getRefreshToken().substring(0,10)

            );
            new AlertDialog.Builder(this).setTitle("Success").setMessage(message).setPositiveButton("OK", null).show();

            ACCESS_TOKEN = authenticationResult.getAccessToken();
            REFRESH_TOKEN = authenticationResult.getRefreshToken();
        }

        private void handleError(String message)
        {
            new AlertDialog.Builder(this).setTitle("Error").setMessage(message).setPositiveButton("OK", null).show();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            //TO-DO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
            mAuthContext.onActivityResult(requestCode, resultCode, data);
        }


    }

    public static class FreeBusyData
    {
        Date startOfFreeTime;
    }

    /**
     * Created by deepavr on 12/10/2014.
     */
    public static class CalendarClient
    {
    }
}
