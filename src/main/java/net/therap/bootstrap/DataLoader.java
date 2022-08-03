package net.therap.bootstrap;

import net.therap.model.Facility;
import net.therap.model.Role;
import net.therap.service.FacilityService;
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

    @Autowired
    private FacilityService facilityService;

    @Override
    public void run(String... args) throws Exception {
        createSeedRole();
        createSeedFacility();
    }

    private void createSeedFacility() {
        Facility MRI = new Facility("MRI", 2000.0);
        facilityService.saveOrUpdate(MRI);

        Facility ECG = new Facility("ECG", 2500.0);
        facilityService.saveOrUpdate(ECG);

        Facility bloodTest = new Facility("Blood test", 500.0);
        facilityService.saveOrUpdate(bloodTest);
    }

    private void createSeedRole() {
        Role adminRole = new Role("admin");
        roleService.saveOrUpdate(adminRole);

        Role doctorRole = new Role("doctor");
        roleService.saveOrUpdate(doctorRole);

        Role patientRole = new Role("patient");
        roleService.saveOrUpdate(patientRole);

        Role receptionistRole = new Role("receptionist");
        roleService.saveOrUpdate(receptionistRole);
    }
}
