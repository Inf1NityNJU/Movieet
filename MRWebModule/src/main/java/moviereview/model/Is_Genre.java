package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SilverNarcissus on 2017/5/14.
 */
@Entity
@Table(name = "is_genre")
public class Is_Genre {
    @Id
    private String idmovie;

    private String idgenre;

    public String getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(String idmovie) {
        this.idmovie = idmovie;
    }

    public String getIdgenre() {
        return idgenre;
    }

    public void setIdgenre(String idgenre) {
        this.idgenre = idgenre;
    }

    public Is_Genre() {
    }

    public Is_Genre(String idmovie, String idgenre) {
        this.idmovie = idmovie;
        this.idgenre = idgenre;
    }
}
