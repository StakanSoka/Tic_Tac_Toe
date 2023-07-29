package bean;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "layout_pattern")
public class LayoutPattern {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    private String position1;

    @Column
    private String position2;

    @Column
    private int price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "layoutPattern")
    Set<UserLayoutPatternMap> userLayoutPatternMaps;

    @ManyToMany(mappedBy = "layoutPatterns")
    Set<Bot> bots;

    public LayoutPattern(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
