package bean;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column
    private int coin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserBotMap> userBotMaps;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserSymbolMap> userSymbolMaps;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<UserOrder> userOrders;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserLayoutPatternMap> userLayoutPatternMaps;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
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

    public Set<UserSymbolMap> getUserSymbolMaps() {
        return userSymbolMaps;
    }

    public void setUserSymbolMaps(Set<UserSymbolMap> userSymbolMaps) {
        this.userSymbolMaps = userSymbolMaps;
    }

    public Set<UserOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Set<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public Set<UserLayoutPatternMap> getUserLayoutPatternMaps() {
        return userLayoutPatternMaps;
    }

    public void setUserLayoutPatternMaps(Set<UserLayoutPatternMap> userLayoutPatternMaps) {
        this.userLayoutPatternMaps = userLayoutPatternMaps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && coin == user.coin && login.equals(user.login) && password.equals(user.password) && role.equals(user.role) && image.equals(user.image) && nickname.equals(user.nickname) && registrationDate.equals(user.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, image, nickname, registrationDate, coin);
    }
}