package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.entidades.Rol;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import com.utp.gp.inventarioSMP.servicio.IRol;
import com.utp.gp.inventarioSMP.servicio.IUsuario;
import com.utp.gp.inventarioSMP.util.paginacion.PageRender;
import com.utp.gp.inventarioSMP.util.paginacion.UsuarioExporterExcel;
import com.utp.gp.inventarioSMP.util.paginacion.UsuarioExporterPDF;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        modelo.put("titulo", "Detalle del Usuario: " + usuario.getUsername());
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
    public String guardarUsuario(@Valid Usuario usuario, BindingResult result, @RequestParam(required = false) String nuevaPassword, Model modelo, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", usuario.getId() != null ? "Editar Usuario" : "Nuevo Usuario");
            modelo.addAttribute("roles", iRol.findAll());            
            return "formularioUsuario";
        }

        // Manejo especial para actualización de password
        if (usuario.getId() != null) {
            iUsuario.actualizarUsuario(usuario, nuevaPassword);
            flash.addFlashAttribute("success", "Usuario actualizado correctamente");
        } else {
            iUsuario.save(usuario);
            flash.addFlashAttribute("success", "Usuario creado correctamente");
        }

        status.setComplete();

        return "redirect:/listarUsuario";
    }

    @GetMapping("/formularioUsuario/{id}")
    public String editarUsuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Usuario usuario = null;
        List<Rol> rol = iRol.findAll();
        if (id > 0) {
            usuario = iUsuario.findOne(id);
            if (usuario == null) {
                flash.addFlashAttribute("error", "El ID del usuario no existe en la base de datos!");
                System.out.println(flash);
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
        try {
            if (id > 0) {
                iUsuario.delete(id);
                flash.addFlashAttribute("success", "Usuario eliminado con éxito!");
            } else {
                flash.addFlashAttribute("error", "ID inválido");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/listarUsuario";
    }

    @GetMapping("/exportarUsuarioPDF")
    public void exportarUsuariosPDF(HttpServletResponse response) throws IOException{
        response.setContentType("application/pdf");
        
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormater.format(new Date());
        
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Usuarios_" + fechaActual + ".pdf";
        
        response.setHeader(cabecera, valor);
        
        List<Usuario> usuarios = iUsuario.findAll();
        
        UsuarioExporterPDF exporter = new UsuarioExporterPDF(usuarios);
        exporter.exportar(response);
    }
    
    @GetMapping("/exportarUsuarioExcel")
    public void exportarUsuarioExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octec-stream");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormater.format(new Date());
        
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Usuarios_" + fechaActual + ".xlsx";
        
        response.setHeader(cabecera, valor);
        
        List<Usuario> listaUsuarios = iUsuario.findAll();
        
        UsuarioExporterExcel exporter = new UsuarioExporterExcel(listaUsuarios);
        exporter.exportar(response);
    }
    
}
