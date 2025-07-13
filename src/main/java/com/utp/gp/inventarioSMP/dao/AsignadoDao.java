package com.utp.gp.inventarioSMP.dao;

import com.utp.gp.inventarioSMP.entidades.Asignado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AsignadoDao extends JpaRepository<Asignado, Long>{
    Asignado findByNombre(String nombre);
}
