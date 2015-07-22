package afterteam.com.babymoment.login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.SignupResponseCallback;
import com.kakao.usermgmt.UserManagement;

import java.util.HashMap;

import afterteam.com.babymoment.base.BaseSignupActivity;
import afterteam.com.babymoment.utils.BabyMomentToast;
import afterteam.com.babymoment.utils.LogUtils;

/**
 * After verifying that a valid session
 * It calls the ME, Check whether your subscription is drawn to the signup page or move to the Main page.
 * @author chayongbin
 */
public class UsermgmtSignupActivity extends BaseSignupActivity {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());

    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, UserMgmtLoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, UsermgmtMainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void showSignup() {
        // Signup Method
    }

    /**
     * After collecting the information of Sign-up Window, Call the Sign-Up API
     */
    private void onClickSignup(final HashMap<String, String> properties) {
        UserManagement.requestSignup(new SignupResponseCallback() {
            @Override
            public void onSuccess(final long userId) {
                redirectMainActivity();
            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                String message = "failed to sign up. msg=" + errorResult;
                Log.e(TAG, message);
                BabyMomentToast.makeToast(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }, properties);
    }

}
