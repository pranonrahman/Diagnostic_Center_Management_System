package net.therap.dms.editor;

import net.therap.dms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Component
public class RoleEditor extends PropertyEditorSupport {

    @Autowired
    private RoleService roleService;

    @Override
    public void setAsText(String roleId) throws IllegalArgumentException {
        if (nonNull(roleId) && !roleId.isEmpty()) {
            setValue(roleService.findById(Long.parseLong(roleId)));
        }
    }
}
