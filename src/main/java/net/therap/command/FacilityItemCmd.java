package net.therap.command;

import lombok.Getter;
import lombok.Setter;
import net.therap.model.Facility;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author khandaker.maruf
 * @since 8/5/22
 */
@Getter
@Setter
public class FacilityItemCmd {

    @NotNull
    private Facility facility;

    @Min(1)
    private int quantity;
}
