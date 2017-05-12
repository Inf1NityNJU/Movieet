package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/5/5.
 * This class is used to test hibernate helper
 */
@Entity
public class Card {
    @Id
    private int No;
    private String name;
    private LocalDate date;

    public Card(int no, String name, LocalDate date) {
        No = no;
        this.name = name;
        this.date = date;
    }

    public Card() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
