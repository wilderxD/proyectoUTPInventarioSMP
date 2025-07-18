package com.utp.gp.inventarioSMP.dao;

import com.utp.gp.inventarioSMP.entidades.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoDao extends JpaRepository<Equipo, Long>{
          
    
}
