package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.entidades.Equipo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInventario {
    
     public List<Equipo> findAll();
    
    public Page<Equipo> findAll(Pageable pageable);
    
    public void save(Equipo equipo);
    
    public Equipo findOne(Long id);
    
    public void delete(Long id);
    
    public void actualizarEquipo(Equipo equipo);
    
    // Métodos existentes para asignación
    public List<Equipo> equiposAsignados();
    public List<Equipo> equiposNoAsignados();    
    
    // Nuevos métodos para los filtros
    public Page<Equipo> findByCategoriaId(Long categoriaId, Pageable pageable);
    public Page<Equipo> findByAsignadoIsNotNull(Pageable pageable);
    public Page<Equipo> findByAsignadoIsNull(Pageable pageable);
    
    // Para exportaciones filtradas
    public List<Equipo> findByCategoriaId(Long categoriaId);
    public List<Equipo> findAllAsignados();
    public List<Equipo> findAllNoAsignados();
}
