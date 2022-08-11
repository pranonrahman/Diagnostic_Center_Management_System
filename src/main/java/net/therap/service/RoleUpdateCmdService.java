package net.therap.service;

import net.therap.model.User;
import net.therap.command.RoleUpdateCmd;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Service
public class RoleUpdateCmdService {

    public RoleUpdateCmd getRoleUpdateCmd(User user) {

        RoleUpdateCmd roleUpdateCmd = new RoleUpdateCmd();

        roleUpdateCmd.setDoctor(nonNull(user.getDoctor()));
        roleUpdateCmd.setAdmin(nonNull(user.getAdmin()));
        roleUpdateCmd.setPatient(nonNull(user.getPatient()));
        roleUpdateCmd.setReceptionist(nonNull(user.getReceptionist()));

        if (nonNull(user.getDoctor())) {
            roleUpdateCmd.setFee(user.getDoctor().getFee());
        }

        return roleUpdateCmd;
    }
}
