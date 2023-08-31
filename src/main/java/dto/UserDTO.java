package dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "login cannot be empty")
    @Size(min = 3, max = 50, message = "login must be between 3 and 50")
    private String login;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 8, message = "password must be at least 8 length")
    private String password;

    @NotBlank(message = "confirm the password")
    private String confirmedPassword;

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

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}
