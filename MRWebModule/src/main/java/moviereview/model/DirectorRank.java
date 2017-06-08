package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by vivian on 2017/6/8.
 */
@Entity
@Table(name = "director_rank")
public class DirectorRank  implements Serializable {
    @Id
    private int director_id;

    private double avg_score;

    public DirectorRank() {
    }

    public int getDirector_id() {
        return director_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }

    public double getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(double avg_score) {
        this.avg_score = avg_score;
    }
}
