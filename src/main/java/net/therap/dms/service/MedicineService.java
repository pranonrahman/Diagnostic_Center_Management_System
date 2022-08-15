package net.therap.dms.service;

import net.therap.dms.dao.MedicineDao;
import net.therap.dms.entity.Medicine;
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

    public Medicine findById(long id) {
        return medicineDao.findById(id);
    }

    public List<Medicine> findAll() {
        return medicineDao.findAll();
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
