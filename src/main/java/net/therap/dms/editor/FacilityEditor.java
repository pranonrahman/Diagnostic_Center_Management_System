package net.therap.dms.editor;

import net.therap.dms.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

/**
 * @author amimul.ehsan
 * @since 03/08/2022
 */
@Component
public class FacilityEditor extends PropertyEditorSupport {

    @Autowired
    private FacilityService facilityService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (nonNull(id) && !id.isEmpty()) {
            setValue(facilityService.findById(Long.parseLong(id)));
        }
    }
}
