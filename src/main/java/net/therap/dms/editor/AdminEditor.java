package net.therap.dms.editor;

import net.therap.dms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 */
@Component
public class AdminEditor extends PropertyEditorSupport {

    @Autowired
    private AdminService adminService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        if (nonNull(id) && !id.isEmpty()) {
            setValue(adminService.findById(Long.parseLong(id)));
        }
    }
}
