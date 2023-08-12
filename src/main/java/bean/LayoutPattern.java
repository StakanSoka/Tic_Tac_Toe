package bean;

import javax.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "layout_pattern")
@DiscriminatorValue("2")
public class LayoutPattern extends Item {

    @Column(nullable = false)
    private String position1;

    @Column(nullable = false)
    private String position2;

    @Column
    private int price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "layoutPattern")
    private Set<UserLayoutPatternMap> userLayoutPatternMaps;

    @ManyToMany(mappedBy = "layoutPatterns")
    private Set<Bot> bots;

    public LayoutPattern(){}

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

    public Set<UserLayoutPatternMap> getUserLayoutPatternMaps() {
        return userLayoutPatternMaps;
    }

    public void setUserLayoutPatternMaps(Set<UserLayoutPatternMap> userLayoutPatternMaps) {
        this.userLayoutPatternMaps = userLayoutPatternMaps;
    }

    public Set<Bot> getBots() {
        return bots;
    }

    public void setBots(Set<Bot> bots) {
        this.bots = bots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LayoutPattern that)) return false;
        if (!super.equals(o)) return false;
        return price == that.price && position1.equals(that.position1) && position2.equals(that.position2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position1, position2, price);
    }
}
