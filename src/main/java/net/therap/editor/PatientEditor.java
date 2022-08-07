package net.therap.editor;

import net.therap.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

/**
 * @author khandaker.maruf
 * @since 8/4/22
 */
@Component
public class PatientEditor extends PropertyEditorSupport {

    @Autowired
    private PatientService patientService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (nonNull(id) && !id.isEmpty()) {
            setValue(patientService.findById(Long.parseLong(id)));
        }
    }
}
