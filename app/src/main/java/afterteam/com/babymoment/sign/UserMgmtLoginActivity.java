package afterteam.com.babymoment.sign;

import android.content.Intent;

import afterteam.com.babymoment.base.BaseLoginActivity;

/**
 * User Management API is a sign-in base.
 * After the opening session, passing a sign-up page.
 * @author chayongbin
 */
public class UserMgmtLoginActivity extends BaseLoginActivity {
    /**
     * Once a session is open, go to the sign-up page.
     */
    @Override
    protected void onSessionOpened() {
        final Intent intent = new Intent(UserMgmtLoginActivity.this, UsermgmtSignupActivity.class);
        startActivity(intent);
        finish();
    }
}
