package hackfest.office_name_plate;


import android.graphics.Bitmap;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.directoryservices.User;
import com.microsoft.directoryservices.odata.DirectoryClient;
import com.microsoft.directoryservices.odata.UserCollectionOperations;
import com.microsoft.directoryservices.odata.UserFetcher;
import com.microsoft.services.odata.ODataExecutable;
import com.microsoft.services.odata.impl.DefaultDependencyResolver;
import com.microsoft.services.odata.interfaces.DependencyResolver;
import com.microsoft.services.odata.interfaces.ODataResponse;
import com.microsoft.services.odata.interfaces.Request;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.Future;

/**
 * Created by mordonez on 12/11/2014.
 */
public class UserProfileClient {
    public static String ENDPOINT_ID = "https://graph.windows.net/microsoft.com";

    String authToken;
    String userId;
    String tenantId;
    DefaultDependencyResolver dependencyResolver;
    DirectoryClient client;

    UserProfileClient(String authTokenToUse, String userId, String tenantId)
    {
        this.authToken = authTokenToUse;
        this.userId = userId;
        this.tenantId = tenantId;
        dependencyResolver = new DefaultDependencyResolver(authTokenToUse);
        client = new DirectoryClient(ENDPOINT_ID, dependencyResolver);

    }

    void  RetrieveUserProfile (final MainActivity activity)
    {
        boolean success = false;


        ListenableFuture<User> user = client.getusers().getById("mordonez@microsoft.com").read();
        Futures.addCallback(user, new FutureCallback<User>() {
            @Override
            public void onSuccess(User result) {
                Bitmap pic = null;
                //pic = BitmapFactory.decodeByteArray(result.getthumbnailPhoto(), 0, result.getthumbnailPhoto().length);
                final UserProfile profile = new UserProfile(result.getphysicalDeliveryOfficeName().toString(), pic);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView room = (TextView) activity.findViewById(R.id.roomNumber);
                        room.setText(profile.getUserOffice());

                        ImageView profilePicture = (ImageView) activity.findViewById(R.id.profilePicture);
                       // profilePicture.setImageBitmap(profile.getUserImage());
                    }
                });

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}



