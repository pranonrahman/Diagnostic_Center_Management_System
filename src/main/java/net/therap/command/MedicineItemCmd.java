package net.therap.command;

import lombok.Getter;
import lombok.Setter;
import net.therap.model.Medicine;

/**
 * @author khandaker.maruf
 * @since 8/5/22
 */
@Getter
@Setter
public class MedicineItemCmd {

    private Medicine medicine;
    private int quantity;
}
