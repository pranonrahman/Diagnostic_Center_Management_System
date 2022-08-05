package net.therap.viewModel;

import lombok.Getter;
import lombok.Setter;
import net.therap.model.Medicine;

/**
 * @author khandaker.maruf
 * @since 8/5/22
 */
@Getter
@Setter
public class MedicineItem {

    private Medicine medicine;
    private int quantity;
}
