package net.therap.dms.command;

import lombok.Getter;
import lombok.Setter;
import net.therap.dms.entity.Facility;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author khandaker.maruf
 * @since 8/5/22
 */
@Getter
@Setter
public class FacilityItemCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Facility facility;

    @Min(1)
    private int quantity;
}
