package com.utp.gp.inventarioSMP.dao;

import com.utp.gp.inventarioSMP.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
}
