package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.RolDao;
import com.utp.gp.inventarioSMP.entidades.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService implements IRol{

    @Autowired
    private RolDao rolDao;
    
    @Override
    public List<Rol> findAll() {
        return rolDao.findAll();
    }
    
}
