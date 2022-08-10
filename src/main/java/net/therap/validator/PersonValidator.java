package net.therap.validator;

import net.therap.model.Person;
import net.therap.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.Assert.notNull;

/**
 * @author raian.rahman
 * @since 8/10/22
 */
@Component
public class PersonValidator implements Validator {

    private static final String USER_NAME_ALREADY_EXISTS_MESSAGE = "username already exists";
    private static final String USER_NAME_NOT_EXIST_MESSAGE = "username does not exist";

    @Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(person.isNew() && nonNull(personService.findByUserName(person.getUserName()))) {
            errors.rejectValue("userName", "{userName.exist}", USER_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if(!person.isNew() && isNull(personService.findByUserName(person.getUserName()))) {
            errors.rejectValue("userName", "{userName.notExist}", USER_NAME_NOT_EXIST_MESSAGE);
        }
    }
}
