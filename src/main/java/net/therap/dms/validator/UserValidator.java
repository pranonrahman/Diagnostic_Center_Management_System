package net.therap.dms.validator;

import net.therap.dms.entity.User;
import net.therap.dms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/10/22
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSourceAccessor msa;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.isNew() && nonNull(userService.findByUserName(user.getUserName()))) {
            errors.rejectValue("userName", "{userName.exist}", msa.getMessage("userName.exist"));
        }

        if (!user.isNew() && isNull(userService.findByUserName(user.getUserName()))) {
            errors.rejectValue("userName", "{userName.notExist}", msa.getMessage("userName.notExist"));
        }

        if (nonNull(user.getDoctor()) && user.getDoctor().getFee() < 0) {
            errors.rejectValue("doctor.fee", "{fee.notNegative}", msa.getMessage("fee.notNegative"));
        }

        if (!user.isNew() && !userService.findById(user.getId()).getUserName().equals(user.getUserName())) {
            errors.rejectValue("userName", "{userName.notChange}", msa.getMessage("userName.notChange"));
        }
    }
}
