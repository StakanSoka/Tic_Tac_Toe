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
    Set<UserBotMap> userBotMaps = new HashSet<>();

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", image='" + image + '\'' +
                ", nickname='" + nickname + '\'' +
                ", registration_date=" + registrationDate +
                ", coin=" + coin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && coin == user.coin && login.equals(user.login) && password.equals(user.password) && role.equals(user.role) && image.equals(user.image) && nickname.equals(user.nickname) && registrationDate.equals(user.registrationDate) && userBotMaps.equals(user.userBotMaps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, image, nickname, registrationDate, coin, userBotMaps);
    }
}
