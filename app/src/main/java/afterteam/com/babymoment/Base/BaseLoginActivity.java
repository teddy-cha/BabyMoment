package afterteam.com.babymoment.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kakao.auth.Session;
import com.kakao.auth.SessionCallback;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;
import com.urqa.clientinterface.URQAController;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.utils.BabyMomentToast;
import afterteam.com.babymoment.utils.LogUtils;

/**
 * Login Activity Page
 * The session was opened after use to override the action.
 * @author chayongbin
 */
public class BaseLoginActivity extends Activity {

    private static final String TAG = LogUtils.makeTag(BaseLoginActivity.class);

    private BabyMomentLoginButton loginButton;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;


    /**
     * Calling super.onCreate to leave the Session processing .
     * When you click the Login button, set to request access token.
     * @param savedInstanceState Object is stored in an existing session information.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URQAController.InitializeAndStartSession(getApplicationContext(),"8E655F3E");
        setContentView(R.layout.layout_base_kakao_login);

        // Find the login button.
        loginButton = (BabyMomentLoginButton) findViewById(R.id.com_kakao_login);

        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);

        Log.d(TAG, " session.isClosed() : " + session.isClosed());

        if (session.isClosed()){
            loginButton.setVisibility(View.VISIBLE);
        } else {
            loginButton.setVisibility(View.GONE);
            if (session.implicitOpen()) {
                loginButton.setVisibility(View.GONE);
            } else {
                BabyMomentToast.makeToast(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeCallback(mySessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MySessionStatusCallback implements SessionCallback {
        /**
         * Once a session is open , and go to the signup page .
         */
        @Override
        public void onSessionOpened() {
            // Finish Loading Animation
            // If the progress bar shows , stop and go to the page after the session opened .
            Log.i(TAG, "onSessionOpened");
            BaseLoginActivity.this.onSessionOpened();
        }

        /**
         * Now that the session is deleted, the login screen should look.
         * @param exception  If an error occurs in a closed
         */
        @Override
        public void onSessionClosed(final KakaoException exception) {
            // Finish Loading Animation
            // If the progress bar shows , stop and not 've opened a session , go to the next page .
            Log.i(TAG, "onSessionClosed()");
            loginButton.setVisibility(View.VISIBLE);

            if(exception != null) {
                Log.e(TAG, LogUtils.getStackTraceString(exception));
            }
        }

        @Override
        public void onSessionOpening() {
            // Start Loading Animation
            Log.d(TAG, "onSessionOpening");
        }

    }

    protected void onSessionOpened(){
        final Intent intent = new Intent(BaseLoginActivity.this, BaseLoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected void setBackground(Drawable drawable) {
        final View root = findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackground(drawable);
        } else {
            root.setBackgroundDrawable(drawable);
        }
    }

}
