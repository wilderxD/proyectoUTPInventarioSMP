package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.Usuario_asignadoDao;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Usuario_asignadoService implements IUsuario_asignado{

    @Autowired
    private Usuario_asignadoDao asignadoDao;
    
    @Override
    public List<Usuario_asignado> findAll() {
        return asignadoDao.findAll();
    }
    
    
}
