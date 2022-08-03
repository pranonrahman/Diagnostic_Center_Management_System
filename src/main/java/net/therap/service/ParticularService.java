package net.therap.service;

import net.therap.dao.ParticularDao;
import net.therap.model.Particular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Service
public class ParticularService {

    @Autowired
    private ParticularDao particularDao;

    public List<Particular> findAll() {
        return particularDao.findAll();
    }

    public Particular findById(Long id) {
        return particularDao.findById(id);
    }

    @Transactional
    public Particular saveOrUpdate(Particular particular) {
        return particularDao.saveOrUpdate(particular);
    }

    @Transactional
    public void delete(Particular particular) {
        particularDao.delete(particular);
    }
}
