package afterteam.com.babymoment.model;

import java.sql.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chayongbin on 15. 8. 4..
 */
public class Baby extends RealmObject {

    @PrimaryKey
    private String baby_id;

    private String baby_sex;
    private String baby_name;
    private String baby_dob;
    private String baby_profile_photo;

    private RealmList<User> users;

    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

    public String getBaby_name() {
        return baby_name;
    }

    public void setBaby_name(String baby_name) {
        this.baby_name = baby_name;
    }

    public String getBaby_dob() {
        return baby_dob;
    }

    public void setBaby_dob(String baby_dob) {
        this.baby_dob = baby_dob;
    }

    public String getBaby_profile_photo() {
        return baby_profile_photo;
    }

    public void setBaby_profile_photo(String baby_profile_photo) {
        this.baby_profile_photo = baby_profile_photo;
    }

    public RealmList<User> getUsers() {
        return users;
    }

    public void setUsers(RealmList<User> users) {
        this.users = users;
    }

    public String getBaby_sex() {
        return baby_sex;
    }

    public void setBaby_sex(String baby_sex) {
        this.baby_sex = baby_sex;
    }
}
