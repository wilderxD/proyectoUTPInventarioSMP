package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.entidades.Oficina;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOficina {
    
    public List<Oficina> findAll();
    
    public Page<Oficina> findAll(Pageable pageable);
    
    public void save(Oficina oficina);
    
    public Oficina findOne(Long id);
    
    public void delete(Long id);
}
