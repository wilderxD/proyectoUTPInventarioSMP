package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.InventarioDao;
import com.utp.gp.inventarioSMP.entidades.Equipo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService implements IInventario{

     @Autowired
    private InventarioDao equipoDao;

    // Métodos existentes permanecen igual...
    
    @Override
    @Transactional(readOnly = true)
    public Page<Equipo> findByCategoriaId(Long categoriaId, Pageable pageable) {
        return equipoDao.findByCategoriaId(categoriaId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipo> findByAsignadoIsNotNull(Pageable pageable) {
        return equipoDao.findByAsignadoIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipo> findByAsignadoIsNull(Pageable pageable) {
        return equipoDao.findByAsignadoIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findByCategoriaId(Long categoriaId) {
        return equipoDao.findByCategoriaId(categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAllAsignados() {
        return equipoDao.findByAsignadoIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAllNoAsignados() {
        return equipoDao.findByAsignadoIsNull();
    }

    // Optimización de los métodos existentes
    @Override
    @Transactional(readOnly = true)
    public List<Equipo> equiposAsignados() {
        return equipoDao.findByAsignadoIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> equiposNoAsignados() {
        return equipoDao.findByAsignadoIsNull();
    }

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
    public void actualizarEquipo(Equipo equipo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }   
    
}
