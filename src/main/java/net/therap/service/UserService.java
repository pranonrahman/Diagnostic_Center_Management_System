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

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(long id) {
        return userDao.findById(id);
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
    public User updateRole(User user, RoleUpdateViewModel roleUpdateViewModel) {

        Role doctorRole = roleDao.findByRole(RoleEnum.valueOf("DOCTOR"));
        Role adminRole = roleDao.findByRole(RoleEnum.valueOf("ADMIN"));
        Role receptionistRole = roleDao.findByRole(RoleEnum.valueOf("RECEPTIONIST"));
        Role patientRole = roleDao.findByRole(RoleEnum.valueOf("PATIENT"));

        if (nonNull(user.getDoctor()) && roleUpdateViewModel.getDoctor()) {
            user.getDoctor().setFee(roleUpdateViewModel.getFee());
        }

        if (isNull(user.getDoctor()) && roleUpdateViewModel.getDoctor()) {
            Doctor doctor = new Doctor(roleUpdateViewModel.getFee(), user);
            doctor = doctorDao.saveOrUpdate(doctor);

            user.setDoctor(doctor);
            user.getRoles().add(doctorRole);
        }

        if (nonNull(user.getDoctor()) && !roleUpdateViewModel.getDoctor()) {
            doctorDao.delete(user.getDoctor());

            user.setDoctor(null);
            user.getRoles().remove(doctorRole);
        }

        if (isNull(user.getPatient()) && roleUpdateViewModel.getPatient()) {
            Patient patient = new Patient(user);
            patient = patientDao.saveOrUpdate(patient);

            user.setPatient(patient);
            user.getRoles().add(patientRole);
        }

        if (nonNull(user.getPatient()) && !roleUpdateViewModel.getPatient()) {
            patientDao.delete(user.getPatient());

            user.setPatient(null);
            user.getRoles().remove(patientRole);
        }

        if (isNull(user.getAdmin()) && roleUpdateViewModel.getAdmin()) {
            Admin admin = new Admin(user);
            admin = adminDao.saveOrUpdate(admin);

            user.setAdmin(admin);
            user.getRoles().add(adminRole);
        }

        if (nonNull(user.getAdmin()) && !roleUpdateViewModel.getAdmin()) {
            adminDao.delete(user.getAdmin());

            user.setAdmin(null);
            user.getRoles().remove(adminRole);
        }

        if (isNull(user.getReceptionist()) && roleUpdateViewModel.getReceptionist()) {
            Receptionist receptionist = new Receptionist(user);
            receptionist = receptionistDao.saveOrUpdate(receptionist);

            user.setReceptionist(receptionist);
            user.getRoles().add(receptionistRole);
        }

        if (nonNull(user.getReceptionist()) && !roleUpdateViewModel.getReceptionist()) {
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
