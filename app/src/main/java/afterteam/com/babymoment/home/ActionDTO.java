package afterteam.com.babymoment.home;

/**
 * Created by Garam on 2015-07-27.
 */
public class ActionDTO {
    private int id;
    private int type;
    private int count;
    private String time;
    private String detail;
    private String photo;

    public ActionDTO(int id, int type, int count, String time, String detail, String photo) {
        this.id = id;
        this.type = type;
        this.count = count;
        this.time = time;
        this.detail = detail;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public String getTime() {
        return time;
    }

    public String getDetail() {
        return detail;
    }

    public String getPhoto() {
        return photo;
    }
}
