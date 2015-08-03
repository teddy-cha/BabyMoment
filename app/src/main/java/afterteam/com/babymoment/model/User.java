package afterteam.com.babymoment.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chayongbin on 15. 8. 4..
 */
public class User extends RealmObject {

    @PrimaryKey
    private String user_id;

    private String user_profile_photo;
    private String user_name;

    private RealmList<Baby> babies;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public void setUser_profile_photo(String user_profile_photo) {
        this.user_profile_photo = user_profile_photo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public RealmList<Baby> getBabies() {
        return babies;
    }

    public void setBabies(RealmList<Baby> babies) {
        this.babies = babies;
    }
}
