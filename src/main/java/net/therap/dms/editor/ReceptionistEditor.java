package net.therap.dms.editor;

import net.therap.dms.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class ReceptionistEditor extends PropertyEditorSupport {

    @Autowired
    private ReceptionistService receptionistService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (nonNull(id) && !id.isEmpty()) {
            setValue(receptionistService.findById(Long.parseLong(id)));
        }
    }
}
