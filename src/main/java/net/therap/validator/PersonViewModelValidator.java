package net.therap.validator;

import net.therap.viewModel.PersonViewModel;
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
        return PersonViewModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonViewModel personViewModel = (PersonViewModel) target;

        if(isNull(personViewModel.getUserName())) {
            errors.rejectValue("userName", "{person.userName.notNull}", "{person.userName.notNull}");
        } else if(nonNull(personViewModel.getUserName()) && personViewModel.getUserName().strip().length() == 0) {
            errors.rejectValue("userName", "{person.userName.notBlank}", "{person.userName.notBlank}");
        }

        if(isNull(personViewModel.getPassword())) {
            errors.rejectValue("password", "{person.password.notNull}", "{person.password.notNull}");
        } else if(nonNull(personViewModel.getUserName()) && personViewModel.getUserName().strip().length() == 0) {
            errors.rejectValue("password", "{person.password.notBlank}", "{person.password.notBlank}");
        }

        if(isNull(personViewModel.getUserName())) {
            errors.rejectValue("role", "{person.role.notNull}", "{person.role.notNull}");
        } else if(nonNull(personViewModel.getUserName()) && personViewModel.getUserName().strip().length() == 0) {
            errors.rejectValue("role", "{person.role.notBlank}", "{person.role.notBlank}");
        }
    }
}
