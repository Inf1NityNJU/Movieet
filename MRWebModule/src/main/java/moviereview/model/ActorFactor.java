package moviereview.model;

import javax.persistence.*;

/**
 * Created by SilverNarcissus on 2017/5/8.
 */
@Entity
@Table(name = "user_actor_factor")
public class ActorFactor implements Comparable<ActorFactor> {
    /**
     * id
     */
    @Id
    private int id;
    /**
     * 潜在因子
     */
    private double factor;
    /**
     * 主演姓名
     */
    private String name;
    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActorFactor() {
    }
    public ActorFactor(double factor, String name) {
        this.factor = factor;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(ActorFactor o) {
        if (factor > o.factor) {
            return -1;
        } else if (factor == o.factor) {
            return 0;
        }
        return 1;
    }
}
