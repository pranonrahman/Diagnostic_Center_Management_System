package net.therap.editor;

import net.therap.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

public class SpecialityEditor extends PropertyEditorSupport {

    @Autowired
    private SpecialityService specialityService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if(nonNull(id) && !id.isEmpty()) {
            setValue(specialityService.findById(Long.valueOf(id)));
        }
    }
}
