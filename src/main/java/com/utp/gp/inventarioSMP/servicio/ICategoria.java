package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.entidades.Categoria;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoria {
    
    public List<Categoria> findAll();
    
    public Page<Categoria> findAll(Pageable pageable);
    
    public void save(Categoria categoria);
    
    public Categoria findOne(Long id);
    
    public void delete(Long id);
}
