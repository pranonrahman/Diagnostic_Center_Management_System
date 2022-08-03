package net.therap.bootstrap;

import net.therap.model.Role;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    public void createRole() {
        Role adminRole = new Role("admin");
        roleService.saveOrUpdate(adminRole);

        Role doctorRole = new Role("doctor");
        roleService.saveOrUpdate(doctorRole);

        Role patientRole = new Role("patient");
        roleService.saveOrUpdate(patientRole);

        Role receptionistRole = new Role("receptionist");
        roleService.saveOrUpdate(receptionistRole);
    }

    @Override
    public void run(String... args) throws Exception {
        createRole();
    }
}
