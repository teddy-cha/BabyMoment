package afterteam.com.babymoment.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;

import afterteam.com.babymoment.MainActivity;
import afterteam.com.babymoment.R;
import afterteam.com.babymoment.utils.BabyMomentToast;
import afterteam.com.babymoment.utils.LogUtils;

/**
 * After verifying that a valid session
 * It calls the ME, Check whether your subscription is drawn to the signup page or move to the Main page.
 * @author chayongbin
 */
public class BaseSignupActivity extends Activity {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());

    /**
     * me calls to determine how to draw the sign-up page or go to Main page.
     * @param savedInstanceState Existing objects are stored session information.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    /**
     * If it's Automatic Join app, It is the user unclean Sign out error conditions.
     */
    protected void showSignup() {
        Log.d(TAG, "not registered user");
        redirectLoginActivity();
    }

    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, afterteam.com.babymoment.base.BaseLoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * To find out your status, Call me API.
     */
    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onSuccess(final UserProfile userProfile) {
                Log.d(TAG, "UserProfile : " + userProfile);
                userProfile.saveUserToCache();
                redirectMainActivity();
            }

            @Override
            public void onNotSignedUp() {
                showSignup();
            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d(TAG, message);

                if (errorResult.getErrorCodeInt() == -777) {
                    BabyMomentToast.makeToast(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }
        });
    }
}
