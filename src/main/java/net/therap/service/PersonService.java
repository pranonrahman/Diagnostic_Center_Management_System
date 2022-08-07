package net.therap.service;

import net.therap.dao.*;
import net.therap.model.*;
import net.therap.viewModel.RoleUpdateViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private ReceptionistDao receptionistDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private RoleDao roleDao;

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public Person findById(long id) {
        return personDao.findById(id);
    }

    @Transactional
    public Person saveOrUpdate(Person person) {
        return personDao.saveOrUpdate(person);
    }

    @Transactional
    public void delete(Person person) {
        personDao.delete(person);
    }

    @Transactional
    public Person updateRole(Person person, RoleUpdateViewModel roleUpdateViewModel) {

        Role doctorRole = roleDao.findByRole(RoleEnum.valueOf("DOCTOR"));
        Role adminRole = roleDao.findByRole(RoleEnum.valueOf("ADMIN"));
        Role receptionistRole = roleDao.findByRole(RoleEnum.valueOf("RECEPTIONIST"));
        Role patientRole = roleDao.findByRole(RoleEnum.valueOf("PATIENT"));

        if (isNull(person.getDoctor()) && roleUpdateViewModel.getDoctor()) {
            Doctor doctor = new Doctor(roleUpdateViewModel.getFee(), person);
            doctor = doctorDao.saveOrUpdate(doctor);

            person.setDoctor(doctor);
            person.getRoles().add(doctorRole);
        }

        if (nonNull(person.getDoctor()) && !roleUpdateViewModel.getDoctor()) {
            doctorDao.delete(person.getDoctor());
            person.setDoctor(null);
            person.getRoles().remove(doctorRole);
        }

        if (isNull(person.getPatient()) && roleUpdateViewModel.getPatient()) {
            Patient patient = new Patient(person);
            patient = patientDao.saveOrUpdate(patient);

            person.setPatient(patient);
            person.getRoles().add(patientRole);
        }

        if (nonNull(person.getPatient()) && !roleUpdateViewModel.getPatient()) {
            patientDao.delete(person.getPatient());

            person.setPatient(null);
            person.getRoles().remove(patientRole);
        }

        if (isNull(person.getAdmin()) && roleUpdateViewModel.getAdmin()) {
            Admin admin = new Admin(person);
            admin = adminDao.saveOrUpdate(admin);

            person.setAdmin(admin);
            person.getRoles().add(adminRole);
        }

        if (nonNull(person.getAdmin()) && !roleUpdateViewModel.getAdmin()) {
            adminDao.delete(person.getAdmin());

            person.setAdmin(null);
            person.getRoles().remove(adminRole);
        }

        if (isNull(person.getReceptionist()) && roleUpdateViewModel.getReceptionist()) {
            Receptionist receptionist = new Receptionist(person);
            receptionist = receptionistDao.saveOrUpdate(receptionist);

            person.setReceptionist(receptionist);
            person.getRoles().add(receptionistRole);
        }

        if (nonNull(person.getReceptionist()) && !roleUpdateViewModel.getReceptionist()) {
            receptionistDao.delete(person.getReceptionist());

            person.setReceptionist(null);
            person.getRoles().remove(receptionistRole);
        }

        personDao.saveOrUpdate(person);

        return person;
    }
}
