package afterteam.com.babymoment.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kakao.usermgmt.UserProfile;

import afterteam.com.babymoment.Base.ProfileLayout;

/**
 * The main page is the page that users see subscription.
 * This page is created to test the "Importing User Information/update,Logout, withdrawal function" function.
 * @author chayongbin
 * @Reference Kakao Sample App
 */
public class UsermgmtMainActivity extends Activity {
    
    private UserProfile userProfile;
    private ProfileLayout profileLayout;


    /**
     * If User information passed by the sign is present, save
     * @param savedInstanceState Existing objects are stored session information.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        userProfile = UserProfile.loadFromCache();
        if(userProfile != null) {
            showProfile();
        }
    }

    private void redirectLoginActivity() {
        Intent intent = new Intent(this, UserMgmtLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectSignupActivity() {
        Intent intent = new Intent(this, UsermgmtSignupActivity.class);
        startActivity(intent);
        finish();
    }

    private void initializeView() {
    }

    private void showProfile() {
        if (profileLayout != null) {
            profileLayout.setUserProfile(userProfile);
        }
    }

}
