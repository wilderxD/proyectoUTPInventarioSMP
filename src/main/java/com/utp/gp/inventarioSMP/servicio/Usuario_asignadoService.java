package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.Usuario_asignadoDao;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Usuario_asignadoService implements IUsuario_asignado{

    @Autowired
    private Usuario_asignadoDao asignadoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario_asignado> findAll() {
        return asignadoDao.findAll();
    }

    @Override
    public Page<Usuario_asignado> findAll(Pageable pageable) {
        return asignadoDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Usuario_asignado usuario_asignado) {
        asignadoDao.save(usuario_asignado);
    }
    
    @Override
    @Transactional
    public Usuario_asignado save1(Usuario_asignado usuario_asignado) {
        return asignadoDao.save(usuario_asignado);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario_asignado findOne(Long id) {
        return asignadoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        asignadoDao.deleteById(id);
    }
    
    
}
