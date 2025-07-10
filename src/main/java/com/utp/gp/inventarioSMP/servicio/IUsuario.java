package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.entidades.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuario {
    
    public List<Usuario> findAll();
            
    public Page<Usuario> findAll(Pageable pageable);
    
    public void save(Usuario usuario);   
        
    public Usuario findOne(Long id);
    
    public void delete(Long id);
    
    public void actualizarUsuario(Usuario usuario, String nuevaPassword);
}
