package hackfest.office_name_plate;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OutlookBackGroundService extends Service {
    public OutlookBackGroundService() {
    }

    public static int CurrentCountForService = 0;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                 handleCommand(intent);
                // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void handleCommand(Intent intent) {
        CurrentCountForService++;
        String message = "The current count for the service is  " + CurrentCountForService;
        new AlertDialog.Builder(this).setTitle("Service is running").setMessage(message).setPositiveButton("OK", null).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
