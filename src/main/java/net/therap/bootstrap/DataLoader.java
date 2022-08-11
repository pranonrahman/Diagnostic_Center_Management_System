package net.therap.bootstrap;

import net.therap.model.*;
import net.therap.service.FacilityService;
import net.therap.service.MedicineService;
import net.therap.service.UserService;
import net.therap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static net.therap.model.RoleEnum.*;

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

    @Autowired
    private UserService userService;

    @Autowired
    private MedicineService medicineService;

    @Override
    public void run(String... args) {
//        createSeedRole();
//        createSeedFacility();
//        createSeedPerson();
//        createSeedMedicine();
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
        Role adminRole = new Role(ADMIN);
        roleService.saveOrUpdate(adminRole);

        Role doctorRole = new Role(DOCTOR);
        roleService.saveOrUpdate(doctorRole);

        Role patientRole = new Role(PATIENT);
        roleService.saveOrUpdate(patientRole);

        Role receptionistRole = new Role(RECEPTIONIST);
        roleService.saveOrUpdate(receptionistRole);
    }

    private void createSeedPerson() {
        Calendar c = Calendar.getInstance();

        User user1 = new User();
        user1.setName("Abul Mia");
        user1.setUserName("abul");
        user1.setPassword("abul");
        user1.setDateOfBirth(new Date());
        user1.setEmail("abdul@abdul");
        user1.setPhone("01234567890");
        user1.setGender(Gender.MALE);
        c.set(1970, Calendar.JULY, 25);
        Date d1 = c.getTime();
        user1.setDateOfBirth(d1);

        User user2 = new User();
        user2.setName("Abdul Kuddus");
        user2.setUserName("abdul");
        user2.setPassword("abdul");
        user2.setDateOfBirth(new Date());
        user2.setEmail("abdul@abdul");
        user2.setPhone("01234567890");
        user2.setGender(Gender.MALE);
        c.set(1968, Calendar.JULY, 25);
        d1 = c.getTime();
        user2.setDateOfBirth(d1);

        User user3 = new User();
        user3.setName("Abdul Khalek");
        user3.setUserName("khalek");
        user3.setPassword("khalek");
        user3.setDateOfBirth(new Date());
        user3.setEmail("abdul@abdul");
        user3.setPhone("01234567890");
        user3.setGender(Gender.MALE);
        c.set(1975, Calendar.MARCH, 20);
        d1 = c.getTime();
        user3.setDateOfBirth(d1);

        List<Role> roles = roleService.findAll();
        for (Role role : roles) {
            if(role.getName().equals(ADMIN)){
                user1.getRoles().add(role);
                user2.getRoles().add(role);
                user3.getRoles().add(role);
            }
        }

        userService.saveOrUpdate(user1);
        userService.saveOrUpdate(user2);
        userService.saveOrUpdate(user3);
    }

    private void createSeedMedicine() {
        Medicine napa = new Medicine("Napa", "Peracetamol", 10.5, 100);
        medicineService.saveOrUpdate(napa);

        Medicine dermovet =  new Medicine("Dermovet", "deracetamol", 17.0, 10);
        medicineService.saveOrUpdate(dermovet);

        Medicine reset =  new Medicine("Reset", "reset", 17.0, 20);
        medicineService.saveOrUpdate(reset);
    }
}
