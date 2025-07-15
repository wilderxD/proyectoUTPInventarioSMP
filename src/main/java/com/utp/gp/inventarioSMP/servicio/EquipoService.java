package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.EquipoDao;
import com.utp.gp.inventarioSMP.entidades.Equipo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoService implements IEquipo {

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
    public void actualizarEquipo(Equipo equipo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> equiposAsignados() {

        List<Equipo> equiposAsignados = new ArrayList<>();
        List<Equipo> listaEquipos = equipoDao.findAll();

        for (Equipo equipor : listaEquipos) {
            if (equipor.getAsignado() != null || !equipor.getAsignado().toString().isEmpty()) {
                equiposAsignados.add(equipor);
            }
        }
        return equiposAsignados;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> equiposNoAsignados() {
        List<Equipo> equiposNoAsignados = new ArrayList<>();
        List<Equipo> listaEquipos = equipoDao.findAll();

        for (Equipo equipor : listaEquipos) {
            if (equipor.getAsignado() == null || equipor.getAsignado().toString().isEmpty()) {
                equiposNoAsignados.add(equipor);
            }
        }
        return equiposNoAsignados;
    }

}


