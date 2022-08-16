package net.therap.dms.validator;

import net.therap.dms.entity.User;
import net.therap.dms.helper.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author raian.rahman
 * @since 8/16/22
 */
@Component
public class AuthenticationValidator implements Validator {

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @Autowired
    private MessageSourceAccessor msa;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!authenticationHelper.isValidCredential(user)) {
            errors.reject("{login.invalidCredentials}", msa.getMessage("login.invalidCredentials"));
        }
    }
}
