package afterteam.com.babymoment.sign;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.LogoutResponseCallback;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UnlinkResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;

import afterteam.com.babymoment.base.ProfileLayout;
import afterteam.com.babymoment.R;
import afterteam.com.babymoment.home.ActionListActivity;
import afterteam.com.babymoment.utils.BabyMomentToast;
import afterteam.com.babymoment.utils.LogUtils;

/**
 * The main page is the page that users see subscription.
 * This page is created to test the "Importing User Information/update,Logout, withdrawal function" function.
 * @author chayongbin
 * @Reference Kakao Sample App
 */
public class UsermgmtMainActivity extends Activity {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());
    
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

        redirectHomeActivity();
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

    // added by eunjoo for home main show up
    protected void redirectHomeActivity() {
        final Intent intent = new Intent(this, ActionListActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onSuccess(final long userId) {
                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult apiErrorResult) {
                Log.d(TAG, "failed to sign up. msg=" + apiErrorResult);
                redirectLoginActivity();
            }
        });
    }

    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.requestUnlink(new UnlinkResponseCallback() {
                                    @Override
                                    public void onSuccess(final long userId) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onSessionClosedFailure(final APIErrorResult errorResult) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onFailure(final APIErrorResult errorResult) {
                                        Log.d(TAG, "failure to unlink. msg = " + errorResult);
                                        redirectLoginActivity();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }


    private void showProfile() {
        if (profileLayout != null) {
            profileLayout.setUserProfile(userProfile);
        }
    }

    private void initializeView() {
        setContentView(R.layout.layout_usermgmt_main);
        ((TextView)findViewById(R.id.text_title)).setText(getString(R.string.text_usermgmt));
        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initializeButtons();
        initializeProfileView();
    }

    private void initializeButtons() {
        final Button buttonMe = (Button) findViewById(R.id.buttonMe);
        buttonMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                profileLayout.requestMe();
            }
        });

        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickLogout();
            }
        });

        final Button unlinkButton = (Button) findViewById(R.id.unlink_button);
        unlinkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickUnlink();
            }
        });
    }

    private void initializeProfileView() {
        profileLayout = (ProfileLayout) findViewById(R.id.com_kakao_user_profile);
        profileLayout.setMeResponseCallback(new MeResponseCallback() {
            @Override
            public void onSuccess(final UserProfile userProfile) {
                BabyMomentToast.makeToast(getApplicationContext(), "succeeded to get user profile", Toast.LENGTH_SHORT).show();
                if (userProfile != null) {
                    UsermgmtMainActivity.this.userProfile = userProfile;
                    userProfile.saveUserToCache();
                    showProfile();
                }
            }

            @Override
            public void onNotSignedUp() {
                redirectSignupActivity();
            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.e(TAG, message);
                BabyMomentToast.makeToast(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
