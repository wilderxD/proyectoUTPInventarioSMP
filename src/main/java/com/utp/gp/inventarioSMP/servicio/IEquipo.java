package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.entidades.Equipo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEquipo {
    
    public List<Equipo> findAll();
    
    public Page<Equipo> findAll(Pageable pageable);
    
    public void save(Equipo equipo);
    
    public Equipo findOne(Long id);
    
    public void delete(Long id);
    
    public void actualizarEquipo(Equipo equipo);
    
    public List<Equipo> equiposAsignados();
    
    public List<Equipo> equiposNoAsignados();    

}
