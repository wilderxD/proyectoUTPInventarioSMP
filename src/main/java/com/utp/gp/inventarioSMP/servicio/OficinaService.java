package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.OficinaDao;
import com.utp.gp.inventarioSMP.entidades.Oficina;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OficinaService implements IOficina{
    
    @Autowired
    private OficinaDao oficinaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Oficina> findAll() {
        return oficinaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Oficina> findAll(Pageable pageable) {
        return oficinaDao.findAll(pageable);
    }
    
    @Override
    @Transactional
    public void save(Oficina oficina) {
        oficinaDao.save(oficina);
    }

    @Override
    @Transactional(readOnly = true)
    public Oficina findOne(Long id) {
        return oficinaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        oficinaDao.deleteById(id);
    }
   
}
