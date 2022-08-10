package net.therap.bootstrap;

import net.therap.model.*;
import net.therap.service.*;
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
    private PersonService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ReceptionistService receptionistService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private ParticularService particularService;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public void run(String... args) throws Exception {
        createSeedRole();
        createSeedFacility();
        createSeedPerson();
//        createSeedDoctor();
//        createSeedPatient();
//        createSeedReceptionist();
        createSeedMedicine();
//        createSeedPrescription();
//        createSeedParticular();
//        createSeedInvoice();
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

        user1 = userService.saveOrUpdate(user1);
        user2 = userService.saveOrUpdate(user2);
        user3 = userService.saveOrUpdate(user3);
    }

    private void createSeedPatient() {
        List<User> users = userService.findAll();

        for (User user : users) {
            Patient patient = new Patient(user);
            patient = patientService.saveOrUpdate(patient);
            user.setPatient(patient);
            userService.saveOrUpdate(user);
        }
    }

    private void createSeedDoctor() {
        List<User> users = userService.findAll();

        for (User user : users) {
            Doctor doctor = new Doctor(1000.0, user);
            doctor = doctorService.saveOrUpdate(doctor);
            user.setDoctor(doctor);
            userService.saveOrUpdate(user);
        }
    }

    private void createSeedReceptionist() {
        User user = new User();
        user.setName("Rupban");
        user.setUserName("rupban");
        user.setPassword("rupban");
        user.setDateOfBirth(new Date());
        user.setEmail("abdul@abdul");
        user.setPhone("01234567890");
        user.setGender(Gender.FEMALE);
        Role receptionistRole = null;
        for (Role role : roleService.findAll()) {
            if(role.getName().equals(RECEPTIONIST)){
                receptionistRole = role;
            }
        }
        user.getRoles().add(receptionistRole);
        user = userService.saveOrUpdate(user);
        Receptionist receptionist = new Receptionist(user);
        receptionist = receptionistService.saveOrUpdate(receptionist);
        user.setReceptionist(receptionist);
        userService.saveOrUpdate(user);

        User user2 = new User();
        user2.setName("Fulbanu");
        user2.setUserName("fulbanu");
        user2.setPassword("fulbanu");
        user2.setDateOfBirth(new Date());
        user2.setEmail("abdul@abdul");
        user2.setPhone("01234567890");
        user2.setGender(Gender.FEMALE);
        user2.getRoles().add(receptionistRole);
        user2 = userService.saveOrUpdate(user2);
        Receptionist receptionist2 = new Receptionist(user2);
        receptionist2 = receptionistService.saveOrUpdate(receptionist2);
        user2.setReceptionist(receptionist2);
        userService.saveOrUpdate(user2);
    }

    private void createSeedMedicine() {
        Medicine napa = new Medicine("Napa", "Peracetamol", 10.5, 100);
        medicineService.saveOrUpdate(napa);

        Medicine dermovet =  new Medicine("Dermovet", "deracetamol", 17.0, 10);
        medicineService.saveOrUpdate(dermovet);

        Medicine reset =  new Medicine("Reset", "reset", 17.0, 20);
        medicineService.saveOrUpdate(reset);
    }

    private void createSeedPrescription() {
        List<Facility> facilities = facilityService.findAll();
        List<Patient> patients = patientService.findAll();
        List<Doctor> doctors = doctorService.findAll();
        Calendar c = Calendar.getInstance();

        Prescription prescription1 = new Prescription();
        prescription1.setSymptoms("cough, fever, headache");
        prescription1.setDiagnosis("seasonal cold");
        prescription1.setMedicines("napa\t500mg\t1+0+1;");
        prescription1.getFacilities().add(facilities.get(0));

        c.set(2022, Calendar.JULY, 25);
        Date d1 = c.getTime();
        prescription1.setDateOfVisit(d1);

        prescription1.setPatient(patients.get(0));
        prescription1.setDoctor(doctors.get(0));

        prescription1 = prescriptionService.saveOrUpdate(prescription1);

        patients.get(0).getPrescriptions().add(prescription1);
        patientService.saveOrUpdate(patients.get(0));

        doctors.get(0).getPrescriptions().add(prescription1);
        doctorService.saveOrUpdate(doctors.get(0));

        Prescription prescription2 = new Prescription();
        prescription2.setSymptoms("back pain, difficulty in movement");
        prescription2.setDiagnosis("muscle pull");
        prescription2.setMedicines("napa\t500mg\tif pain occurs;");
        prescription2.getFacilities().add(facilities.get(1));

        c.set(2022, Calendar.JULY, 30);
        Date d2 = c.getTime();
        prescription2.setDateOfVisit(d2);

        prescription2.setPatient(patients.get(1));
        prescription2.setDoctor(doctors.get(1));

        prescription2 = prescriptionService.saveOrUpdate(prescription2);

        patients.get(1).getPrescriptions().add(prescription2);
        patientService.saveOrUpdate(patients.get(1));

        doctors.get(1).getPrescriptions().add(prescription2);
        doctorService.saveOrUpdate(doctors.get(1));

        Prescription prescription3 = new Prescription();
        prescription3.setSymptoms("rash, sneezing, itching");
        prescription3.setDiagnosis("allergy");
        prescription3.setMedicines("dermovet ointment;\nnebanol powder;");
        prescription3.getFacilities().add(facilities.get(2));

        c.set(2022, Calendar.JULY, 20);
        Date d3 = c.getTime();
        prescription3.setDateOfVisit(d3);

        prescription3.setPatient(patients.get(2));
        prescription3.setDoctor(doctors.get(2));

        prescription3 = prescriptionService.saveOrUpdate(prescription3);

        patients.get(2).getPrescriptions().add(prescription3);
        patientService.saveOrUpdate(patients.get(2));

        doctors.get(2).getPrescriptions().add(prescription3);
        doctorService.saveOrUpdate(doctors.get(2));

        Prescription prescription4 = new Prescription();
        prescription4.setSymptoms("pain near appendix");
        prescription4.setDiagnosis("appendicitis");
        prescription4.setMedicines("appendoset 200 mg;");
        prescription4.getFacilities().add(facilities.get(0));
        prescription4.getFacilities().add(facilities.get(2));

        c.set(2022, Calendar.AUGUST, 5);
        Date d4 = c.getTime();
        prescription4.setDateOfVisit(d4);

        prescription4.setPatient(patients.get(0));
        prescription4.setDoctor(doctors.get(1));

        prescription4 = prescriptionService.saveOrUpdate(prescription4);

        patients.get(0).getPrescriptions().add(prescription4);
        patientService.saveOrUpdate(patients.get(0));

        doctors.get(1).getPrescriptions().add(prescription4);
        doctorService.saveOrUpdate(doctors.get(1));
    }

    private void createSeedParticular() {
        Particular particular = new Particular("Dr. Visit 1", 1000.0, 1);
        particularService.saveOrUpdate(particular);

        Particular particular2 = new Particular("Napa", 10.0, 5);
        particularService.saveOrUpdate(particular2);

        Particular particular3 = new Particular("X-Ray", 5000.0, 2);
        particularService.saveOrUpdate(particular3);

        Particular particular4 = new Particular("Safi", 300.0, 5);
        particularService.saveOrUpdate(particular4);
    }
    private void createSeedInvoice() {
        Invoice invoice = new Invoice();

        Receptionist receptionist = receptionistService.findAll().get(0);
        Patient patient = patientService.findAll().get(0);
        double totalCost = 0;
        for (Particular particular : particularService.findAll()) {
            invoice.getParticulars().add(particular);
            totalCost += particular.getUnitPrice() * particular.getUnits();
        }
        invoice.setPatient(patient);
        invoice.setGeneratedBy(receptionist.getUser());
        invoice.setTotalCost(totalCost);

        invoiceService.saveOrUpdate(invoice);
    }
}
