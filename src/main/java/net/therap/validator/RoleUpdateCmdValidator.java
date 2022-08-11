package net.therap.validator;

import net.therap.command.RoleUpdateCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Component
public class RoleUpdateCmdValidator implements Validator {

    private static final String FEE_NOT_PROVIDED_MESSAGE = "Doctor must have a fee";

    @Override
    public boolean supports(Class<?> clazz) {
        return RoleUpdateCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleUpdateCmd roleUpdateCmd = (RoleUpdateCmd) target;

        if (roleUpdateCmd.getDoctor()
                && (isNull(roleUpdateCmd.getFee()) || roleUpdateCmd.getFee() == 0)) {

            errors.rejectValue("fee",
                    "{roleUpdateCmd.doctor.feeNull}",
                    FEE_NOT_PROVIDED_MESSAGE);
        }
    }
}
