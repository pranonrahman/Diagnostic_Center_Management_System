package net.therap.validator;

import net.therap.command.MedicineItemCmd;
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

    @Override
    public boolean supports(Class<?> clazz) {
        return MedicineItemCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MedicineItemCmd medicineItemCmd = (MedicineItemCmd) target;

        if(isNull(medicineItemCmd.getMedicine())){
            errors.rejectValue("medicine", "", "Must select a medicine");
            return;
        }

        if(medicineItemCmd.getQuantity() < 1) {
            errors.rejectValue("quantity", "", "Must be a positive value");
            return;
        }

        int availableUnits = medicineItemCmd.getMedicine().getAvailableUnits();
        if(medicineItemCmd.getQuantity() > availableUnits) {
            errors.rejectValue("quantity", "", "Not enough items available. Only "
                    + availableUnits + " left in stock.");
        }

    }
}
