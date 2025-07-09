package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.entidades.Rol;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import com.utp.gp.inventarioSMP.servicio.IRol;
import com.utp.gp.inventarioSMP.servicio.IUsuario;
import com.utp.gp.inventarioSMP.util.paginacion.PageRender;
import com.utp.gp.inventarioSMP.web.PasswordEncoderConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdministracionController {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private IRol iRol;
    
    
    @GetMapping("/verUsuario/{id}")
    public String verDetallerDelUsuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Usuario usuario = iUsuario.findOne(id);

        if (usuario == null) {
            flash.addFlashAttribute("error", "El Usuario no existe en la base de datos");
            return "redirect:/listarUsuarios";
        }

        modelo.put("usuario", usuario);
        modelo.put("titulo", "Detalle del empleado: " + usuario.getUsername());
        return "verUsuario";
    }

    @GetMapping("/listarUsuario")
    public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Usuario> usuarios = iUsuario.findAll(pageRequest);
        PageRender<Usuario> pageRender = new PageRender<>("/listarUsuario", usuarios);

        modelo.addAttribute("titulo", "Listado de Usuarios");
        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("page", pageRender);

        return "listarUsuario";
    }

    @GetMapping("/formularioUsuario")
    public String mostrarFormularioRegistroDeUsuario(Map<String, Object> modelo) {
        Usuario usuario = new Usuario();
        List<Rol> rol = iRol.findAll();
        modelo.put("usuario", usuario);
        modelo.put("roles", rol);
        modelo.put("titulo", "Registro de Usuario");
        return "formularioUsuario";
    }

    @PostMapping("/formularioUsuario")
    public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status, @RequestParam(required = false) String nuevaPassword) {
        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de Usuario");
            modelo.addAttribute("roles", iRol.findAll());
            return "formularioUsuario";
        }

        if (usuario.getId() != null) { // Es una edición
            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                // Encriptar nueva contraseña si se proporcionó
                usuario.setPassword(passwordEncoder.passwordEncoder().encode(nuevaPassword));
            } else {
                // Mantener la contraseña existente
                Usuario usuarioExistente = iUsuario.findById(usuario.getId()).orElse(null);
                if (usuarioExistente != null) {
                    usuario.setPassword(usuarioExistente.getPassword());
                }
            }
        } else { // Es un nuevo usuario
            // Asegurar que la contraseña se encripte
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        String mensaje = (usuario.getId() != null ? "El usuario a sido actualizado con exito!" : "El usuario a sido guardado con exito!.");
        iUsuario.save(usuario);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarUsuario";
        
        public String guardarUsuario(
        @Valid Usuario usuario,
        BindingResult result,
        @RequestParam(required = false) String nuevaPassword,
        Model modelo,
        RedirectAttributes flash,
        SessionStatus status) {
        
        if(result.hasErrors()){
            modelo.addAttribute("titulo", usuario.getId() != null ? "Editar Usuario" : "Nuevo Usuario");
            modelo.addAttribute("roles", rolService.listarTodosLosRoles());
            return "formularioUsuario";            
        }
        
        // Manejo especial para actualización de password
        if(usuario.getId() != null) {
            ((UsuarioServiceImpl)usuarioService).actualizarUsuario(usuario, nuevaPassword);
        } else {
            usuarioService.save(usuario);
        }
        
        status.setComplete();
        flash.addFlashAttribute("success", usuario.getId() != null ? 
            "Usuario actualizado correctamente" : "Usuario creado correctamente");
        
        return "redirect:/usuarios/listar";
    }
    }

    @GetMapping("/formularioUsuario/{id}")
    public String editarUsuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Usuario usuario = null;
        List<Rol> rol = iRol.findAll();
        if (id > 0) {
            usuario = iUsuario.findOne(id);
            if (usuario == null) {
                flash.addFlashAttribute("error", "El ID del usuario no existe en la base de datos!");
                return "redirect:/listarUsuario";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del usuario no puede ser CERO");
            return "redirect:/listarUsuario";
        }
        modelo.put("usuario", usuario);
        modelo.put("roles", rol);
        modelo.put("titulo", "Editar Usuario");
        return "formularioUsuario";
    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            iUsuario.delete(id);
            flash.addFlashAttribute("success", "Empleado eliminado con exito!");
        }
        return "redirect:/listarUsuario";
    }

}
