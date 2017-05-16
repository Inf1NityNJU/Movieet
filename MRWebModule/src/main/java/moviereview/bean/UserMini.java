package moviereview.bean;

import javax.persistence.Id;

/**
 * Created by Sorumi on 17/5/16.
 */
public class UserMini {

    /**
     * 用户id
     */
    @Id
    private int id;

    /**
     * 用户名
     */
    private String username;

    public UserMini() {
    }

    public UserMini(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
