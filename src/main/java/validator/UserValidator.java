package validator;

import dto.UserDTO;
import manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserManager userManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (userManager.findByLogin(userDTO.getLogin()) != null) {
            errors.rejectValue("login", "DUPLICATE","This login is already used");
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmedPassword())) {
            errors.rejectValue("confirmedPassword", "DIFFERENT", "The passwords are not equal");
        }
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}