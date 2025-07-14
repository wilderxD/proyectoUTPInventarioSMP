package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.entidades.Categoria;
import com.utp.gp.inventarioSMP.entidades.Equipo;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import com.utp.gp.inventarioSMP.servicio.ICategoria;
import com.utp.gp.inventarioSMP.servicio.IEquipo;
import com.utp.gp.inventarioSMP.servicio.IUsuario_asignado;
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
public class EquipoController {
    
    @Autowired
    private IEquipo iEquipo;

    @Autowired
    private IUsuario_asignado iAsignado;
    
    @Autowired
    private ICategoria iCategoria;

    @GetMapping("/verEquipo/{id}")
    public String verDetallerDelEquipo(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Equipo equipo = iEquipo.findOne(id);

        if (equipo == null) {
            flash.addFlashAttribute("error", "El Equipo no existe en la base de datos");
            return "redirect:/listarEquipos";
        }

        modelo.put("equipo", equipo);
        modelo.put("titulo", "Detalle del equipo: " + equipo.getEquipo_descripcion());
        return "verEquipo";
    }

    @GetMapping("/listarEquipo")
    public String listarEquipo(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Equipo> equipos = iEquipo.findAll(pageRequest);
        PageRender<Equipo> pageRender = new PageRender<>("/listarEquipos", equipos);

        modelo.addAttribute("titulo", "Listado de Equipos");
        modelo.addAttribute("equipos", equipos);
        modelo.addAttribute("page", pageRender);

        return "listarEquipos";
    }

    @GetMapping("/formularioEquipo")
    public String mostrarFormularioRegistroDeEquipo(Map<String, Object> modelo) {
        Equipo equipo = new Equipo();
        List<Usuario_asignado> asignados = iAsignado.findAll();
        List<Categoria> categorias = iCategoria.findAll();        
        modelo.put("equipo", equipo);
        modelo.put("asignados", asignados);
        modelo.put("categorias", categorias);        
        modelo.put("titulo", "Registro de Equipos");
        return "formularioEquipo";
    }

    @PostMapping("/formularioEquipo")
    public String guardarEquipo(@Valid Equipo equipo, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", equipo.getId() != null ? "Editar Usuario" : "Nuevo Usuario");
            modelo.addAttribute("asignados", iAsignado.findAll());
            modelo.addAttribute("categorias", iCategoria.findAll());               
            return "formularioEquipo";
        }
        
        status.setComplete();

        return "redirect:/listarUsuario";
    }

    @GetMapping("/formularioEquipo/{id}")
    public String editarUsuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Equipo equipo = null;
        List<Usuario_asignado> rol = iUsuario_asignado.findAll();
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
    public void esportarUsuarioExcel(HttpServletResponse response) throws IOException{
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
