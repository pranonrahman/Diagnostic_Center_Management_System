package net.therap.validator;

import net.therap.command.MedicineItemCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

/**
 * @author khandaker.maruf
 * @since 8/6/22
 */
@Component
public class MedicineItemCmdValidator implements Validator {

    @Autowired
    MessageSourceAccessor msa;

    @Override
    public boolean supports(Class<?> clazz) {
        return MedicineItemCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MedicineItemCmd medicineItemCmd = (MedicineItemCmd) target;

        if (isNull(medicineItemCmd.getMedicine())) {
            errors.rejectValue("medicine", "", msa.getMessage("error.selectMedicine"));
            return;
        }

        if (medicineItemCmd.getQuantity() < 1) {
            errors.rejectValue("quantity", "", msa.getMessage("error.positiveValue"));
            return;
        }

        int availableUnits = medicineItemCmd.getMedicine().getAvailableUnits();

        if (medicineItemCmd.getQuantity() > availableUnits) {
            errors.rejectValue("quantity",
                    "",
                    msa.getMessage("error.notEnoughItem", new String[]{String.valueOf(availableUnits)}));
        }

    }
}
