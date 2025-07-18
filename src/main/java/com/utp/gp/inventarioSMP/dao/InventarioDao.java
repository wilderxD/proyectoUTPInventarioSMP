package com.utp.gp.inventarioSMP.dao;

import com.utp.gp.inventarioSMP.entidades.Equipo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventarioDao extends JpaRepository<Equipo, Long>{
    
    // Métodos para filtros con paginación
    Page<Equipo> findByCategoriaId(Long categoriaId, Pageable pageable);
    Page<Equipo> findByAsignadoIsNotNull(Pageable pageable);
    Page<Equipo> findByAsignadoIsNull(Pageable pageable);
    
    // Métodos para exportaciones (sin paginación)
    List<Equipo> findByCategoriaId(Long categoriaId);
    List<Equipo> findByAsignadoIsNotNull();
    List<Equipo> findByAsignadoIsNull();     
}
