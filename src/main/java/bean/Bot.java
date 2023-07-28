package bean;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Bot {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String difficulty;

    @Column
    private int coin;

    @OneToMany(mappedBy = "bot")
    Set<UserBotMap> userBotMaps = new HashSet<>();

    public Bot(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bot bot)) return false;
        return id == bot.id && coin == bot.coin && name.equals(bot.name) && image.equals(bot.image) && difficulty.equals(bot.difficulty) && userBotMaps.equals(bot.userBotMaps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, difficulty, coin, userBotMaps);
    }
}
