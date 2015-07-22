package afterteam.com.babymoment.base;

import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;

import afterteam.com.babymoment.R;

/**
 * The default UserProfile (user ID, nickname, profile images) to draw Layout.
 * 1. Declares the {@link afterteam.com.babymoment.base.ProfileLayout} in the layout to expose your profile.
 * 2. By using a {@link  afterteam.com.babymoment.base.ProfileLayout#setMeResponseCallback(com.kakao.usermgmt.MeResponseCallback)} to set the callback according to the request result user information
 * @author chayongbin
 * @Reference Kakao Sample
 */
public class ProfileLayout extends FrameLayout {
    private MeResponseCallback meResponseCallback;

    private String nickname;
    private String userId;
    private String birthDay;
    private String countryIso;
    private NetworkImageView profile;
    private NetworkImageView background;
    private TextView profileDescription;

    public ProfileLayout(Context context) {
        super(context);
        initView();
    }

    public ProfileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProfileLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * Set a callback results based on your request for information.
     * @param callback Callback results based on your request for information.
     */
    public void setMeResponseCallback(final MeResponseCallback callback){
        this.meResponseCallback = callback;
    }
    /**
     * Update the view for UserProfile with param.
     * @param userProfile User information to reflect on your screen.
     */
    public void setUserProfile(final UserProfile userProfile) {
        setProfileURL(userProfile.getProfileImagePath());
        setNickname(userProfile.getNickname());
        setUserId(String.valueOf(userProfile.getId()));
    }

    /**
     * To update the view for a profile image.
     * @param profileImageURL Profile image to be reflected on the screen
     */
    public void setProfileURL(final String profileImageURL) {
        if (profile != null && profileImageURL != null) {
            Application app = GlobalApplication.getGlobalApplicationContext();
            if (app == null)
                throw new UnsupportedOperationException("afterteam.com.babymoment.Base.GlobalApplication in order to use ImageLoader");
            profile.setImageUrl(profileImageURL, ((GlobalApplication) app).getImageLoader());
        }
    }

    public void setBgImageURL(String bgImageURL) {
        if (bgImageURL != null && bgImageURL != null ) {
            Application app = GlobalApplication.getGlobalApplicationContext();
            if (app == null)
                throw new UnsupportedOperationException("afterteam.com.babymoment.Base.GlobalApplication in order to use ImageLoader");
            background.setImageUrl(bgImageURL, ((GlobalApplication) app).getImageLoader());
        }
    }

    public void setDefaultBgImage(int imageResId) {
        if (background != null) {
            background.setBackgroundResource(imageResId);
        }
    }

    public void setDefaultProfileImage(int imageResId) {
        if (profile != null) {
            profile.setBackgroundResource(imageResId);
        }
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
        updateLayout();
    }

    /**
     * Update your nickname view.
     * @param nickname Nickname reflect on the screen
     */
    public void setNickname(final String nickname) {
        this.nickname = nickname;
        updateLayout();
    }

    public void setBirthDay(final String birthDay) {
        this.birthDay = birthDay;
        updateLayout();
    }

    /**
     * Updateq your userId view.
     * @param userId UserId reflect on the screen
     */
    public void setUserId(final String userId) {
        this.userId = userId;
        updateLayout();
    }

    private void updateLayout() {
        StringBuilder builder = new StringBuilder();
        if (nickname != null && nickname.length() > 0) {
            builder.append(getResources().getString(R.string.com_kakao_profile_nickname) + "\n").append(nickname + "\n");
        }

        if (userId != null && userId.length() > 0) {
            builder.append(getResources().getString(R.string.com_kakao_profile_userId) + "\n").append(userId+ "\n");
        }

        if (birthDay != null && birthDay.length() > 0) {
            builder.append(getResources().getString(R.string.com_kakao_profile_userId) + "\n").append(birthDay);
        }

        if (countryIso != null) {
            builder.append(getResources().getString(R.string.kakaotalk_country_label) + "\n").append(countryIso);
        }

        if (profileDescription != null) {
            profileDescription.setText(builder.toString());
        }
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.layout_base_kakao_profile, this);

        profile = (NetworkImageView) view.findViewById(R.id.com_kakao_profile_image);
        background = (NetworkImageView) view.findViewById(R.id.background);
        profileDescription = (TextView) view.findViewById(R.id.profile_description);
    }

    /**
     * requests the user information.
     */
    public void requestMe() {
        UserManagement.requestMe(meResponseCallback);
    }
}

