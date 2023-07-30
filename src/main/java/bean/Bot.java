package bean;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
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

    @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL)
    private Set<UserBotMap> userBotMaps;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bot_symbol_map",
            joinColumns = @JoinColumn(name = "bot_id"),
            inverseJoinColumns = @JoinColumn(name = "symbol_id")
    )
    private Set<Symbol> symbols;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bot_layout_pattern_map",
            joinColumns = @JoinColumn(name = "bot_id"),
            inverseJoinColumns = @JoinColumn(name = "layout_pattern_id")
    )
    private Set<LayoutPattern> layoutPatterns;

    public Bot() {}

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

    public Set<UserBotMap> getUserBotMaps() {
        return userBotMaps;
    }

    public void setUserBotMaps(Set<UserBotMap> userBotMaps) {
        this.userBotMaps = userBotMaps;
    }

    public Set<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Set<Symbol> symbols) {
        this.symbols = symbols;
    }

    public Set<LayoutPattern> getLayoutPatterns() {
        return layoutPatterns;
    }

    public void setLayoutPatterns(Set<LayoutPattern> layoutPatterns) {
        this.layoutPatterns = layoutPatterns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bot bot)) return false;
        return id == bot.id && coin == bot.coin && name.equals(bot.name) && image.equals(bot.image) && difficulty.equals(bot.difficulty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, difficulty, coin);
    }
}