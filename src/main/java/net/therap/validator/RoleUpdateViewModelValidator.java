package net.therap.validator;

import net.therap.viewModel.RoleUpdateViewModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Component
public class RoleUpdateViewModelValidator implements Validator {

    private static final String DOCTOR_NULL_AND_FEE_PROVIDED_MESSAGE = "Fees must have a doctor";
    private static final String FEE_NOT_PROVIDED_MESSAGE = "Doctor must have a fee";

    @Override
    public boolean supports(Class<?> clazz) {
        return RoleUpdateViewModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleUpdateViewModel roleUpdateViewModel = (RoleUpdateViewModel) target;

        if (roleUpdateViewModel.getDoctor()
                && (isNull(roleUpdateViewModel.getFee()) || roleUpdateViewModel.getFee() == 0)) {

            errors.rejectValue("fee",
                    "{roleUpdateViewModel.doctor.feeNull}",
                    FEE_NOT_PROVIDED_MESSAGE);
        }
    }
}
