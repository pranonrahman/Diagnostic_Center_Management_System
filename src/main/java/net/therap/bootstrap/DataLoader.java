package net.therap.bootstrap;

import net.therap.model.*;
import net.therap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private PersonService personService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicineService medicineService;

    @Override
    public void run(String... args) throws Exception {
        createSeedRole();
        createSeedFacility();
        createSeedPerson();
        createSeedDoctor();
        createSeedPatient();
        createSeedMedicine();
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

    private void createSeedPerson() {
        Person person1 = new Person();
        person1.setName("Abul Mia");
        personService.saveOrUpdate(person1);

        Person person2 = new Person();
        person2.setName("Abdul Kuddus");
        personService.saveOrUpdate(person2);

        Person person3 = new Person();
        person3.setName("Abdul Khalek");
        personService.saveOrUpdate(person3);

        List<Role> roles = roleService.findAll();
        for (Role role : roles) {
            person1.getRoles().add(role);
            person2.getRoles().add(role);
            person3.getRoles().add(role);
        }
    }

    private void createSeedPatient() {
        List<Person> persons = personService.findAll();

        for (Person person : persons) {
            Patient patient = new Patient(person);
            patientService.saveOrUpdate(patient);
        }
    }

    private void createSeedDoctor() {
        List<Person> persons = personService.findAll();

        for (Person person : persons) {
            Doctor doctor = new Doctor(1000.0, person);
            doctorService.saveOrUpdate(doctor);
        }
    }

    private void createSeedMedicine() {
        Medicine napa = new Medicine();
        napa.setName("Napa");
        napa.setUnitPrice(5.8);
        medicineService.saveOrUpdate(napa);

        Medicine dermovet = new Medicine();
        dermovet.setName("Dermovet");
        dermovet.setUnitPrice(15.3);
        medicineService.saveOrUpdate(dermovet);

        Medicine reset = new Medicine();
        reset.setName("Reset");
        reset.setUnitPrice(5.6);
        medicineService.saveOrUpdate(reset);
    }
}
