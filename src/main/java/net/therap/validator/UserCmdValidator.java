package net.therap.validator;

import net.therap.command.UserCmd;
import net.therap.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
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
public class UserCmdValidator implements Validator {

    private static final String USER_NAME_NOT_NULL_MESSAGE = "Must provide a username";
    private static final String PASSWORD_NOT_NULL_MESSAGE = "Must provide a password";

    @Autowired
    private MessageSourceAccessor msa;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCmd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCmd userCmd = (UserCmd) target;

        if (isNull(userCmd.getUserName())) {
            errors.rejectValue("userName",
                    "{user.userName.notNull}",
                    msa.getMessage("user.userName.notNull"));
        }

        if (isNull(userCmd.getPassword())) {
            errors.rejectValue("password",
                    "{user.password.notNull}",
                    msa.getMessage("user.password.notnull"));
        }

        if(nonNull(userCmd) &&  !authenticationUtil.isValidCredential(userCmd)) {
            errors.reject("{login.invalidCredentials}", msa.getMessage("login.invalidCredentials"));
        }
    }
}
