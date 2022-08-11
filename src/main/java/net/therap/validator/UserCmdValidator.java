package net.therap.validator;

import net.therap.command.UserCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class UserCmdValidator implements Validator {

    private static final String USER_NAME_NOT_NULL_MESSAGE = "Must provide a username";
    private static final String PASSWORD_NOT_NULL_MESSAGE = "Must provide a password";

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCmd userCmd = (UserCmd) target;

        if (isNull(userCmd.getUserName())) {
            errors.rejectValue("userName", "{user.userName.notNull}", USER_NAME_NOT_NULL_MESSAGE);
        }

        if (isNull(userCmd.getPassword())) {
            errors.rejectValue("password", "{user.password.notNull}", PASSWORD_NOT_NULL_MESSAGE);
        }
    }
}
