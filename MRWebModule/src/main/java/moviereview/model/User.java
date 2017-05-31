package moviereview.model;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by vivian on 2017/5/12.
 */
@Entity
@Table(name = "user")
public class User {
    /**
     * 用户Id
     */
    @Id
    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 用户简介
     */
    private String introduction;
    /**
     * 类型因子
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    //下面这个注释指的是在多的一方加外键，否则会当成many2many处理，生成中间表
    //@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, insertable = true)
    private Set<GenreFactor> genreFactors;

    /**
     * 导演因子
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    //下面这个注释指的是在多的一方加外键，否则会当成many2many处理，生成中间表
    //@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, insertable = true)
    private Set<DirectorFactor> directorFactors;

    /**
     * 主演因子
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    //下面这个注释指的是在多的一方加外键，否则会当成many2many处理，生成中间表
    //@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, insertable = true)
    private Set<ActorFactor> actorFactors;

    public User(int id, String username, String password, int level, String introduction, Set<GenreFactor> genreFactors, Set<DirectorFactor> directorFactors, Set<ActorFactor> actorFactors) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.introduction = introduction;
        this.genreFactors = genreFactors;
        this.directorFactors = directorFactors;
        this.actorFactors = actorFactors;
    }

    public User() {

    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
    public Set<GenreFactor> getGenreFactors() {
        return genreFactors;
    }

    public void setGenreFactors(Set<GenreFactor> genreFactors) {
        this.genreFactors = genreFactors;
    }

    public Set<DirectorFactor> getDirectorFactors() {
        return directorFactors;
    }

    public void setDirectorFactors(Set<DirectorFactor> directorFactors) {
        this.directorFactors = directorFactors;
    }

    public Set<ActorFactor> getActorFactors() {
        return actorFactors;
    }

    public void setActorFactors(Set<ActorFactor> actorFactors) {
        this.actorFactors = actorFactors;
    }

    public int getLevel() {
        if (level==null){
            return 0;
        }
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIntroduction() {
        if (introduction==null) {
            return "";
        }
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator", "\n");

        StringBuilder result = new StringBuilder();
        result.append("----------")
                .append(this.getClass().getName())
                .append("----------")
                .append(lineSeparator);
        //
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                result.append(field.getName());
                if (field.get(this) == null) {
                    result.append(": null    ");
                } else {
                    result.append(": ").append(field.get(this).toString()).append("    ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append(lineSeparator).append("--------------------").append(lineSeparator);

        return result.toString();
    }
}
