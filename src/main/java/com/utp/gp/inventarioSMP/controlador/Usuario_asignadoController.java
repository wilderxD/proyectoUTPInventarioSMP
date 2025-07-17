package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.entidades.Equipo;
import com.utp.gp.inventarioSMP.entidades.Oficina;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import com.utp.gp.inventarioSMP.servicio.IEquipo;
import com.utp.gp.inventarioSMP.servicio.IOficina;
import com.utp.gp.inventarioSMP.servicio.IUsuario_asignado;
import com.utp.gp.inventarioSMP.util.paginacion.AsignadoExporterExcel;
import com.utp.gp.inventarioSMP.util.paginacion.AsignadoExporterPDF;
import com.utp.gp.inventarioSMP.util.paginacion.PageRender;
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
public class Usuario_asignadoController {

    @Autowired
    private IUsuario_asignado iAsignado;

    @Autowired
    private IEquipo iEquipo;

    @Autowired
    private IOficina iOficina;

    @GetMapping("/verAsignado/{id}")
    public String verDetalleDelAsignado(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Usuario_asignado asignado = iAsignado.findOne(id);

        if (asignado == null) {
            flash.addFlashAttribute("error", "El Equipo no existe en la base de datos");
            return "redirect:/listarAsignado";
        }

        modelo.put("asignado", asignado);
        modelo.put("titulo", "Detalle del Usuario asignado: " + asignado.getNombre());
        return "verAsignado";
    }

    @GetMapping("/listarAsignado")
    public String listarAsignado(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Usuario_asignado> asignados = iAsignado.findAll(pageRequest);
        PageRender<Usuario_asignado> pageRender = new PageRender<>("/listarAsignado", asignados);

        modelo.addAttribute("titulo", "Listado de Asignaciones");
        modelo.addAttribute("asignados", asignados);
        modelo.addAttribute("page", pageRender);

        return "listarAsignado";
    }

    @GetMapping("/formularioAsignado")
    public String mostrarFormularioRegistroDeAsignado(Map<String, Object> modelo) {
        Usuario_asignado asignado = new Usuario_asignado();
        List<Equipo> equipos = iEquipo.equiposNoAsignados();
        List<Oficina> oficinas = iOficina.findAll();
        modelo.put("asignado", asignado);
        modelo.put("equipos", equipos);
        modelo.put("oficinas", oficinas);
        modelo.put("titulo", "Registro Asignacion de Equipos");
        return "formularioAsignado";
    }

    @PostMapping("/formularioAsignado")
    public String guardarAsignado(@Valid Usuario_asignado asignado, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", asignado.getId() != null ? "Editar Asignacion de equipo" : "Nuevo registro de Asignacion");
            modelo.addAttribute("equipos", iEquipo.findAll());
            modelo.addAttribute("oficinas", iOficina.findAll());
            return "formularioAsignado";
        }

        Usuario_asignado usuarioGuardado = iAsignado.save1(asignado);

        // Actualizar el equipo con el ID del usuario asignado
        if (asignado.getEquipo() != null) {
            Equipo equipo = asignado.getEquipo();
            equipo.setAsignado(usuarioGuardado);
            iEquipo.save(equipo);
        }

        String mensaje = (asignado.getId() != null ? "El registro de Asignacion a sido actualizado con exito!" : "El registro de Asignacion a sido guardado con exito!");
        
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarAsignado";
    }

    @GetMapping("/formularioAsignado/{id}")
    public String editarAsignado(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Usuario_asignado asignado = null;
        List<Equipo> equipos = iEquipo.equiposNoAsignados();
        List<Oficina> oficinas = iOficina.findAll();
        if (id > 0) {
            asignado = iAsignado.findOne(id);
            if (asignado == null) {
                flash.addFlashAttribute("error", "El ID de la Asignacion no existe en la base de datos!");
                return "redirect:/listarAsignado";
            }
        } else {
            flash.addFlashAttribute("error", "El ID de la Asignacion no puede ser CERO");
            return "redirect:/listarAsignado";
        }
        modelo.put("asignado", asignado);
        modelo.put("equipos", equipos);
        modelo.put("oficinas", oficinas);
        modelo.put("titulo", "Editar Asignacion");
        return "formularioAsignado";
    }

    @GetMapping("/eliminarAsignado/{id}")
    public String eliminarEquipo(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            if (id > 0) {
                iAsignado.delete(id);
                flash.addFlashAttribute("success", "Registro eliminado con éxito!");
            } else {
                flash.addFlashAttribute("error", "ID inválido");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/listarAsignado";
    }

    @GetMapping("/exportarAsignadoPDF")
    public void exportarAsignadosPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormater.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Asignaciones_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Usuario_asignado> asignados = iAsignado.findAll();

        AsignadoExporterPDF exporter = new AsignadoExporterPDF(asignados);
        exporter.exportar(response);
    }

    @GetMapping("/exportarAsignadoExcel")
    public void exportarAsignadosExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octec-stream");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormater.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Asignaciones_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Usuario_asignado> asignados = iAsignado.findAll();

        AsignadoExporterExcel exporter = new AsignadoExporterExcel(asignados);
        exporter.exportar(response);
    }

}
