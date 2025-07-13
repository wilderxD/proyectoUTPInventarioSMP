package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.AsignadoDao;
import com.utp.gp.inventarioSMP.entidades.Asignado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsignadoService implements IAsignado{

    @Autowired
    private AsignadoDao asignadoDao;
    
    @Override
    public List<Asignado> findAll() {
        return asignadoDao.findAll();
    }
    
}
