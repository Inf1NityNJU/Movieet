package moviereview.bean;

import javafx.scene.image.Image;

/**
 * Created by vivian on 2017/5/7.
 */
public class User {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private Image avatar;

    public User(String userId, String userName, Image avatar) {
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
}
