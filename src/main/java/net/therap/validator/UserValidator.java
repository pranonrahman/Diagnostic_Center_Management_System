package net.therap.validator;

import net.therap.entity.User;
import net.therap.service.UserService;
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

    private static final String USER_NAME_ALREADY_EXISTS_MESSAGE = "username already exists";
    private static final String USER_NAME_NOT_EXIST_MESSAGE = "username does not exist";

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

        if(user.isNew() && nonNull(userService.findByUserName(user.getUserName()))) {
            errors.rejectValue("userName", "{userName.exist}", USER_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if(!user.isNew() && isNull(userService.findByUserName(user.getUserName()))) {
            errors.rejectValue("userName", "{userName.notExist}", USER_NAME_NOT_EXIST_MESSAGE);
        }

        if(nonNull(user.getDoctor()) && user.getDoctor().getFee()<0) {
            errors.rejectValue("doctor.fee", "{fee.notNegative}", msa.getMessage("fee.notNegative"));
        }

        if(!user.isNew() && !userService.findById(user.getId()).getUserName().equals(user.getUserName())) {
            errors.rejectValue("userName", "{userName.notChange}", msa.getMessage("userName.notChange"));
        }
    }
}
