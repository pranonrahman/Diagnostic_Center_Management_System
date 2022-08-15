package net.therap.dms.validator;

import net.therap.dms.command.UserCmd;
import net.therap.dms.helper.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
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

    @Autowired
    private MessageSourceAccessor msa;

    @Autowired
    private AuthenticationHelper authenticationHelper;

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

        if(!authenticationHelper.isValidCredential(userCmd)) {
            errors.reject("{login.invalidCredentials}", msa.getMessage("login.invalidCredentials"));
        }
    }
}
