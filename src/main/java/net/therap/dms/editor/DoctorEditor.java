package net.therap.dms.editor;

import net.therap.dms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

/**
 * @author khandaker.maruf
 * @since 8/4/22
 */
@Component
public class DoctorEditor extends PropertyEditorSupport {

    @Autowired
    private DoctorService doctorService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (nonNull(id) && !id.isEmpty()) {
            setValue(doctorService.findById(Long.parseLong(id)));
        }
    }
}
