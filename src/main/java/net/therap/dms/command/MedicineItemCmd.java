package net.therap.dms.command;

import lombok.Getter;
import lombok.Setter;
import net.therap.dms.entity.Medicine;

import java.io.Serializable;

/**
 * @author khandaker.maruf
 * @since 8/5/22
 */
@Getter
@Setter
public class MedicineItemCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    private Medicine medicine;
    private int quantity;
}
