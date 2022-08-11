package net.therap.validator;

import net.therap.viewModel.UserViewModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class PersonViewModelValidator implements Validator {

    private static final String USER_NAME_NOT_NULL_MESSAGE = "Must provide a username";
    private static final String PASSWORD_NOT_NULL_MESSAGE = "Must provide a password";

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println(UserViewModel.class.equals(clazz));
        return UserViewModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserViewModel userViewModel = (UserViewModel) target;

        if (isNull(userViewModel.getUserName())) {
            errors.rejectValue("userName", "{user.userName.notNull}", USER_NAME_NOT_NULL_MESSAGE);
        }

        if (isNull(userViewModel.getPassword())) {
            errors.rejectValue("password", "{user.password.notNull}", PASSWORD_NOT_NULL_MESSAGE);
        }
    }
}
