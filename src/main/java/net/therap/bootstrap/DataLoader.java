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
    private PersonService personService;

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
        createSeedDoctor();
        createSeedPatient();
        createSeedReceptionist();
        createSeedMedicine();
        createSeedPrescription();
        createSeedParticular();
        createSeedInvoice();
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
        Person person1 = new Person();
        person1.setName("Abul Mia");
        person1.setUserName("abul");
        person1.setPassword("abul");
        personService.saveOrUpdate(person1);

        Person person2 = new Person();
        person2.setName("Abdul Kuddus");
        person2.setUserName("abdul");
        person2.setPassword("abdul");
        personService.saveOrUpdate(person2);

        Person person3 = new Person();
        person3.setName("Abdul Khalek");
        person3.setUserName("khalek");
        person3.setPassword("khalek");
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

    private void createSeedReceptionist() {
        Person person = new Person();
        person.setName("Rupban");
        person.setUserName("rupban");
        person.setPassword("rupban");
        Role receptionistRole = null;
        for (Role role : roleService.findAll()) {
            if(role.getName().equals(RECEPTIONIST)){
                receptionistRole = role;
            }
        }
        person.getRoles().add(receptionistRole);
        Person savedPerson = personService.saveOrUpdate(person);
        Receptionist receptionist = new Receptionist(savedPerson);
        receptionistService.saveOrUpdate(receptionist);

        Person person2 = new Person();
        person2.setName("Fulbanu");
        person2.setUserName("fulbanu");
        person2.setPassword("fulbanu");
        person2.getRoles().add(receptionistRole);
        Person savedPerson2 = personService.saveOrUpdate(person2);
        receptionistService.saveOrUpdate(new Receptionist(savedPerson2));
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

        invoice.setId(1L);
        invoice.setInvoiceId(1001L);
        invoice.setPatient(patient);
        invoice.setGeneratedBy(receptionist);
        invoice.setTotalCost(totalCost);

        Invoice i = invoiceService.saveOrUpdate(invoice);
        System.out.println(i);
    }
}
