package net.therap.service;

import net.therap.dao.MedicineDao;
import net.therap.model.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    public List<Medicine> findAll() {
        return medicineDao.findAll();
    }

    public Medicine findById(long id) {
        return medicineDao.findById(id);
    }

    @Transactional
    public Medicine saveOrUpdate(Medicine medicine) {
        return medicineDao.saveOrUpdate(medicine);
    }

    @Transactional
    public void delete(Medicine medicine) {
        medicineDao.delete(medicine);
    }
}
