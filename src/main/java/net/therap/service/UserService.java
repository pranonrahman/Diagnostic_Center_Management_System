package net.therap.service;

import net.therap.dao.*;
import net.therap.entity.*;
import net.therap.exception.RecordNotFoundException;
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

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public User saveOrUpdate(User user) {
        if (!user.isNew()) {
            user.setRoles(userDao.findById(user.getId()).getRoles());
        }

        return userDao.saveOrUpdate(user);
    }

    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Transactional
    public User updateRole(User user, double fee) {

        Role doctorRole = roleDao.findByName(RoleEnum.valueOf("DOCTOR"));
        Role adminRole = roleDao.findByName(RoleEnum.valueOf("ADMIN"));
        Role receptionistRole = roleDao.findByName(RoleEnum.valueOf("RECEPTIONIST"));
        Role patientRole = roleDao.findByName(RoleEnum.valueOf("PATIENT"));

        if (nonNull(user.getDoctor()) && user.getRoles().contains(doctorRole)) {
            user.getDoctor().setFee(fee);
        }

        if (isNull(user.getDoctor()) && user.getRoles().contains(doctorRole)) {
            Doctor doctor = new Doctor(fee, user);
            doctor = doctorDao.saveOrUpdate(doctor);

            user.setDoctor(doctor);
            user.getRoles().add(doctorRole);
        }

        if (nonNull(user.getDoctor()) && !user.getRoles().contains(doctorRole)) {
            doctorDao.delete(user.getDoctor());

            user.setDoctor(null);
            user.getRoles().remove(doctorRole);
        }

        if (isNull(user.getPatient()) && user.getRoles().contains(patientRole)) {
            Patient patient = new Patient(user);
            patient = patientDao.saveOrUpdate(patient);

            user.setPatient(patient);
            user.getRoles().add(patientRole);
        }

        if (nonNull(user.getPatient()) && !user.getRoles().contains(patientRole)) {
            patientDao.delete(user.getPatient());

            user.setPatient(null);
            user.getRoles().remove(patientRole);
        }

        if (isNull(user.getAdmin()) && user.getRoles().contains(adminRole)) {
            Admin admin = new Admin(user);
            admin = adminDao.saveOrUpdate(admin);

            user.setAdmin(admin);
            user.getRoles().add(adminRole);
        }

        if (nonNull(user.getAdmin()) && !user.getRoles().contains(adminRole)) {
            adminDao.delete(user.getAdmin());

            user.setAdmin(null);
            user.getRoles().remove(adminRole);
        }

        if (isNull(user.getReceptionist()) && user.getRoles().contains(receptionistRole)) {
            Receptionist receptionist = new Receptionist(user);
            receptionist = receptionistDao.saveOrUpdate(receptionist);

            user.setReceptionist(receptionist);
            user.getRoles().add(receptionistRole);
        }

        if (nonNull(user.getReceptionist()) && !user.getRoles().contains(receptionistRole)) {
            receptionistDao.delete(user.getReceptionist());

            user.setReceptionist(null);
            user.getRoles().remove(receptionistRole);
        }

        userDao.saveOrUpdate(user);

        return user;
    }

    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}
