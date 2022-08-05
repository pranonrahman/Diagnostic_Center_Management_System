package net.therap.viewModel;

import lombok.Getter;
import lombok.Setter;
import net.therap.model.Facility;

/**
 * @author khandaker.maruf
 * @since 8/5/22
 */
@Getter
@Setter
public class FacilityItem {

    private Facility facility;
    private int quantity;
}
