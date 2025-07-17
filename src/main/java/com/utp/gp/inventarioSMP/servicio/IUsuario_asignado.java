package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuario_asignado {
    public List<Usuario_asignado> findAll();
    
    public Page<Usuario_asignado> findAll(Pageable pageable);
    
    public void save(Usuario_asignado Usuario_asignado);
    
    public Usuario_asignado save1(Usuario_asignado usuario_asignado);
    
    public Usuario_asignado findOne(Long id);
    
    public void delete(Long id);
    
}
