package net.therap.validator;

import net.therap.viewModel.UserViewModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class PersonViewModelValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println(UserViewModel.class.equals(clazz));
        return UserViewModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserViewModel userViewModel = (UserViewModel) target;

        if (isNull(userViewModel.getUserName())) {
            errors.rejectValue("userName", "{user.userName.notNull}", "{user.userName.notNull}");
        } else if (nonNull(userViewModel.getUserName()) && userViewModel.getUserName().isBlank()) {
            errors.rejectValue("userName", "{user.userName.notBlank}", "{user.userName.notBlank}");
        }

        if (isNull(userViewModel.getPassword())) {
            errors.rejectValue("password", "{user.password.notNull}", "{user.password.notNull}");
        } else if (nonNull(userViewModel.getUserName()) && userViewModel.getUserName().isBlank()) {
            errors.rejectValue("password", "{user.password.notBlank}", "{user.password.notBlank}");
        }

        if (isNull(userViewModel.getUserName())) {
            errors.rejectValue("role", "{user.role.notNull}", "{user.role.notNull}");
        } else if (nonNull(userViewModel.getUserName()) && userViewModel.getUserName().isBlank()) {
            errors.rejectValue("role", "{user.role.notBlank}", "{user.role.notBlank}");
        }
    }
}
