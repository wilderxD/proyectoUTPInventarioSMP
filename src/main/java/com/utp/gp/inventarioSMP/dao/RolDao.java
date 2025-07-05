package com.utp.gp.inventarioSMP.dao;

import com.utp.gp.inventarioSMP.entidades.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolDao extends JpaRepository<Rol, Long>{
    Rol findByNombre(String nombre);
}
