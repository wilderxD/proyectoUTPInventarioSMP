package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.RolDao;
import com.utp.gp.inventarioSMP.entidades.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService implements IRol{

    @Autowired
    private RolDao rolDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return rolDao.findAll();
    }
    
}
