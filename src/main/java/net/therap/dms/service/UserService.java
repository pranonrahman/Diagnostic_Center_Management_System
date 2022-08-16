package net.therap.dms.service;

import net.therap.dms.dao.*;
import net.therap.dms.entity.*;
import net.therap.dms.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/3/22
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

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

    public User findById(long id) {
        User user = userDao.findById(id);

        if (isNull(user)) {
            throw new RecordNotFoundException();
        }

        return user;
    }

    public List<User> findAll(String filterBy) {

        if (isNull(filterBy)) {
            return userDao.findAll();
        }

        Role role = roleDao.findByName(RoleEnum.valueOf(filterBy));
        List<User> userList = new ArrayList<>();

        userDao.findAll().stream().filter(user -> user.getRoles().contains(role)).forEach(userList::add);

        return userList;
    }

    @Transactional
    public User saveOrUpdate(User user) {
        if (user.isNew()) {
            user = userDao.saveOrUpdate(user);
        }

        updateRole(user);

        return user;
    }

    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    private void updateRole(User user) {
        Role doctorRole = roleDao.findByName(RoleEnum.valueOf("DOCTOR"));
        Role adminRole = roleDao.findByName(RoleEnum.valueOf("ADMIN"));
        Role receptionistRole = roleDao.findByName(RoleEnum.valueOf("RECEPTIONIST"));
        Role patientRole = roleDao.findByName(RoleEnum.valueOf("PATIENT"));

        User existingUser = userDao.findById(user.getId());

        if (isNull(user.getDoctor()) && user.getRoles().contains(doctorRole)) {
            Doctor doctor = new Doctor(existingUser);
            doctor = doctorDao.saveOrUpdate(doctor);

            user.setDoctor(doctor);
            user.getRoles().add(doctorRole);
        }

        if (nonNull(user.getDoctor()) && !user.getRoles().contains(doctorRole)) {
            doctorDao.delete(existingUser.getDoctor());

            user.setDoctor(null);
            user.getRoles().remove(doctorRole);
        }

        if (isNull(user.getPatient()) && user.getRoles().contains(patientRole)) {
            Patient patient = new Patient(existingUser);
            patient = patientDao.saveOrUpdate(patient);

            user.setPatient(patient);
            user.getRoles().add(patientRole);
        }

        if (nonNull(user.getPatient()) && !user.getRoles().contains(patientRole)) {
            patientDao.delete(existingUser.getPatient());

            user.setPatient(null);
            user.getRoles().remove(patientRole);
        }

        if (isNull(user.getAdmin()) && user.getRoles().contains(adminRole)) {
            Admin admin = new Admin(existingUser);
            admin = adminDao.saveOrUpdate(admin);

            user.setAdmin(admin);
            user.getRoles().add(adminRole);
        }

        if (nonNull(user.getAdmin()) && !user.getRoles().contains(adminRole)) {
            adminDao.delete(existingUser.getAdmin());

            user.setAdmin(null);
            user.getRoles().remove(adminRole);
        }

        if (isNull(user.getReceptionist()) && user.getRoles().contains(receptionistRole)) {
            Receptionist receptionist = new Receptionist(existingUser);
            receptionist = receptionistDao.saveOrUpdate(receptionist);

            user.setReceptionist(receptionist);
            user.getRoles().add(receptionistRole);
        }

        if (nonNull(user.getReceptionist()) && !user.getRoles().contains(receptionistRole)) {
            receptionistDao.delete(existingUser.getReceptionist());

            user.setReceptionist(null);
            user.getRoles().remove(receptionistRole);
        }

        userDao.saveOrUpdate(user);

    }
}
