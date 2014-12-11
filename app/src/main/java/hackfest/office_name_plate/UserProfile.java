package hackfest.office_name_plate;

import android.graphics.Bitmap;
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
import javax.crypto.NoSuchPaddingException;


/**
 * Created by deepavr on 12/10/2014.
 */
public class UserProfile
{
    private Bitmap userImage;
    private String userOffice;

    UserProfile (String office, Bitmap image)
    {
        userOffice = office;
        userImage = image;
    }

    public Bitmap getUserImage()
    {
        return userImage;
    }
    public String getUserOffice()
    {
        return userOffice;
    }


}
