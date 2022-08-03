package net.therap.property_editor;

import net.therap.model.Facility;
import net.therap.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Component
public class FacilityEditor extends PropertyEditorSupport {

    @Autowired
    private FacilityService facilityService;

    @Override
    public void setAsText(String facilityId) throws IllegalArgumentException {
        Facility facility = facilityService.findById(Long.valueOf(facilityId));

        setValue(facility);
    }
}
