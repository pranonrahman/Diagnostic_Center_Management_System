package net.therap.service;

import net.therap.dao.*;
import net.therap.entity.*;
import net.therap.command.RoleUpdateCmd;
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
        return userDao.findById(id);
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
    public User updateRole(User user, RoleUpdateCmd roleUpdateCmd) {

        Role doctorRole = roleDao.findByName(RoleEnum.valueOf("DOCTOR"));
        Role adminRole = roleDao.findByName(RoleEnum.valueOf("ADMIN"));
        Role receptionistRole = roleDao.findByName(RoleEnum.valueOf("RECEPTIONIST"));
        Role patientRole = roleDao.findByName(RoleEnum.valueOf("PATIENT"));

        if (nonNull(user.getDoctor()) && roleUpdateCmd.getDoctor()) {
            user.getDoctor().setFee(roleUpdateCmd.getFee());
        }

        if (isNull(user.getDoctor()) && roleUpdateCmd.getDoctor()) {
            Doctor doctor = new Doctor(roleUpdateCmd.getFee(), user);
            doctor = doctorDao.saveOrUpdate(doctor);

            user.setDoctor(doctor);
            user.getRoles().add(doctorRole);
        }

        if (nonNull(user.getDoctor()) && !roleUpdateCmd.getDoctor()) {
            doctorDao.delete(user.getDoctor());

            user.setDoctor(null);
            user.getRoles().remove(doctorRole);
        }

        if (isNull(user.getPatient()) && roleUpdateCmd.getPatient()) {
            Patient patient = new Patient(user);
            patient = patientDao.saveOrUpdate(patient);

            user.setPatient(patient);
            user.getRoles().add(patientRole);
        }

        if (nonNull(user.getPatient()) && !roleUpdateCmd.getPatient()) {
            patientDao.delete(user.getPatient());

            user.setPatient(null);
            user.getRoles().remove(patientRole);
        }

        if (isNull(user.getAdmin()) && roleUpdateCmd.getAdmin()) {
            Admin admin = new Admin(user);
            admin = adminDao.saveOrUpdate(admin);

            user.setAdmin(admin);
            user.getRoles().add(adminRole);
        }

        if (nonNull(user.getAdmin()) && !roleUpdateCmd.getAdmin()) {
            adminDao.delete(user.getAdmin());

            user.setAdmin(null);
            user.getRoles().remove(adminRole);
        }

        if (isNull(user.getReceptionist()) && roleUpdateCmd.getReceptionist()) {
            Receptionist receptionist = new Receptionist(user);
            receptionist = receptionistDao.saveOrUpdate(receptionist);

            user.setReceptionist(receptionist);
            user.getRoles().add(receptionistRole);
        }

        if (nonNull(user.getReceptionist()) && !roleUpdateCmd.getReceptionist()) {
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
