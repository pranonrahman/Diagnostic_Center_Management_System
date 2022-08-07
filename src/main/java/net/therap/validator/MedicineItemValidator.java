package net.therap.validator;

import net.therap.viewModel.MedicineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

/**
 * @author khandaker.maruf
 * @since 8/6/22
 */
@Component
public class MedicineItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MedicineItem.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MedicineItem medicineItem = (MedicineItem) target;

        if(isNull(medicineItem.getMedicine())){
            errors.rejectValue("medicine", "", "Must select a medicine");
            return;
        }

        if(medicineItem.getQuantity() < 1) {
            errors.rejectValue("quantity", "", "Must be a positive value");
            return;
        }

        int availableUnits = medicineItem.getMedicine().getAvailableUnits();
        if(medicineItem.getQuantity() > availableUnits) {
            errors.rejectValue("quantity", "", "Not enough items available. Only "
                    + availableUnits + " left in stock.");
        }

    }
}
