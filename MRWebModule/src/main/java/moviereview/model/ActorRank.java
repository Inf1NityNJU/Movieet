package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vivian on 2017/6/8.
 */
@Entity
@Table(name = "actor_rank")
public class ActorRank {
    @Id
    private int actor_id;

    private double avg_score;

    public ActorRank() {
    }

    public int getActor_id() {
        return actor_id;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public double getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(double avg_score) {
        this.avg_score = avg_score;
    }
}
