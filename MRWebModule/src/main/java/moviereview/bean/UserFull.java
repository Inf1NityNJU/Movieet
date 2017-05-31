package moviereview.bean;

import moviereview.model.User;

/**
 * Created by vivian on 2017/5/31.
 */
public class UserFull {
    private int id;

    private String userName;

    private int level;

    private String introduction;

    public UserFull() {
    }

    public UserFull(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.level = user.getLevel();
        this.introduction = user.getIntroduction();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
