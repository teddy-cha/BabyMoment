package afterteam.com.babymoment.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chayongbin on 15. 8. 4..
 * Modified by eunjoo Im on 15. 8. 13
 */
public class Action extends RealmObject{

    @PrimaryKey
    private int         action_id;

    private int         type;
    private int         count;
    private Date        time;
    private String      detail;
    private String      photo;
    // 1:1 관계 테스트
    private String      baby_id;


    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }
}
