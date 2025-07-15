package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.RolDao;
import com.utp.gp.inventarioSMP.dao.UsuarioDao;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import com.utp.gp.inventarioSMP.web.PasswordEncoderConfig;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioService implements UserDetailsService, IUsuario{
    
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private RolDao rolDao;
    
    @Autowired
    private PasswordEncoderConfig passwordEncoder;

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

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }
    
    
    @Override
    @Transactional
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioDao.findAll(pageable);
    }

    @Override
    @Transactional    
    public void save(Usuario usuario) {
        // Lógica para encriptar password si es nuevo usuario
        if(usuario.getId() == null || usuario.getPassword().startsWith("{bcrypt}") == false) {
            usuario.setPassword(passwordEncoder.passwordEncoder().encode(usuario.getPassword()));
        }
        usuarioDao.save(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario, String nuevaPassword) {
        if(nuevaPassword != null && !nuevaPassword.isEmpty()) {
            usuario.setPassword(passwordEncoder.passwordEncoder().encode(nuevaPassword));
        } else if(usuario.getId() != null) {
            Usuario usuarioExistente = findOne(usuario.getId());
            if(usuarioExistente != null) {
                usuario.setPassword(usuarioExistente.getPassword());
            }
        }
        save(usuario);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario findOne(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioDao.deleteById(id);
    }
    
}
