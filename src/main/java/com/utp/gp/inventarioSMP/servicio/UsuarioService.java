package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.RolDao;
import com.utp.gp.inventarioSMP.dao.UsuarioDao;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioService implements UserDetailsService{
    
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado.");
        }
        
        GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().getNombre());
        
        return new User(usuario.getUsername(), usuario.getPassword(), Collections.singletonList(authority));
        
    }
    
    
    
}
