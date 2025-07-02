package com.utp.gp.inventarioSMP.dao;

import com.utp.gp.inventarioSMP.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDao extends JpaRepository<Categoria, Long>{
    
}
