package net.therap.editor;

import net.therap.model.Role;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

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
        if (!roleId.isEmpty()) {
            Role role = roleService.findById(Long.parseLong(roleId));
            setValue(role);
        }
    }
}
