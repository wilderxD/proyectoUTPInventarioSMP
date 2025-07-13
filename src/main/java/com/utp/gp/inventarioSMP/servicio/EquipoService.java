package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.EquipoDao;
import com.utp.gp.inventarioSMP.entidades.Equipo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoService implements IEquipo{

    @Autowired
    private EquipoDao equipoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return equipoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipo> findAll(Pageable pageable) {
        return equipoDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Equipo equipo) {
        equipoDao.save(equipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipo findOne(Long id) {
        return equipoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        equipoDao.deleteById(id);
    }

    @Override
    public void actualizarUsuario(Equipo equipo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
