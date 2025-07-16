package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.entidades.Oficina;
import com.utp.gp.inventarioSMP.servicio.IOficina;
import com.utp.gp.inventarioSMP.util.paginacion.OficinaExporterExcel;
import com.utp.gp.inventarioSMP.util.paginacion.OficinaExporterPDF;
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
public class OficinaController {
    
    @Autowired
    private IOficina iOficina;
    
    @GetMapping("/verOficina/{id}")
    public String verDetallerDelEquipo(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Oficina oficina = iOficina.findOne(id);

        if (oficina == null) {
            flash.addFlashAttribute("error", "La Oficina no existe en la base de datos");
            return "redirect:/listarOficina";
        }

        modelo.put("oficina", oficina);
        modelo.put("titulo", "Detalle de Oficina: " + oficina.getNombre_oficina());
        return "verEquipo";
    }

    @GetMapping("/listarOficina")
    public String listarEquipo(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Oficina> oficinas = iOficina.findAll(pageRequest);
        PageRender<Oficina> pageRender = new PageRender<>("/listarOficina", oficinas);

        modelo.addAttribute("titulo", "Listado de Oficinas");
        modelo.addAttribute("oficinas", oficinas);
        modelo.addAttribute("page", pageRender);

        return "listarOficina";
    }

    @GetMapping("/formularioOficina")
    public String mostrarFormularioRegistroDeEquipo(Map<String, Object> modelo) {
        Oficina oficina = new Oficina();
                
        modelo.put("oficina", oficina);               
        modelo.put("titulo", "Registro de Oficinas");
        return "formularioOficina";
    }

    @PostMapping("/formularioOficina")
    public String guardarEquipo(@Valid Oficina oficina, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", oficina.getId() != null ? "Editar Oficina" : "Nueva Oficina");                          
            return "formularioOficina";
        }
              
        String mensaje = (oficina.getId() != null? "La oficina a sido actualizada con exito!" : "El registro de oficina a sido guardado con exito!");
        
        iOficina.save(oficina);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarOficina";
    }

    @GetMapping("/formularioOficina/{id}")
    public String editarEquipo(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Oficina oficina = null;
        
        if (id > 0) {
            oficina = iOficina.findOne(id);
            if (oficina == null) {
                flash.addFlashAttribute("error", "El ID de la Oficina no existe en la base de datos!");               
                return "redirect:/listarOficina";
            }
        } else {
            flash.addFlashAttribute("error", "El ID de la Oficina no puede ser CERO");
            return "redirect:/listarOficina";
        }
        modelo.put("oficina", oficina);        
        modelo.put("titulo", "Editar Oficina");
        return "formularioOficina";
    }

    @GetMapping("/eliminarOficina/{id}")
    public String eliminarEquipo(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            if (id > 0) {
                iOficina.delete(id);
                flash.addFlashAttribute("success", "Equipo eliminado con éxito!");
            } else {
                flash.addFlashAttribute("error", "ID inválido");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/listarOficina";
    }

    @GetMapping("/exportarOficinaPDF")
    public void exportarOficinasPDF(HttpServletResponse response) throws IOException{
        response.setContentType("application/pdf");
        
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormater.format(new Date());
        
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Oficinas_" + fechaActual + ".pdf";
        
        response.setHeader(cabecera, valor);
        
        List<Oficina> oficinas = iOficina.findAll();
        
        OficinaExporterPDF exporter = new OficinaExporterPDF(oficinas);
        exporter.exportar(response);
    }
    
    @GetMapping("/exportarOficinaExcel")
    public void exportarOficinasExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octec-stream");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormater.format(new Date());
        
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Oficinas_" + fechaActual + ".xlsx";
        
        response.setHeader(cabecera, valor);
        
        List<Oficina> listaOficinas = iOficina.findAll();
        
        OficinaExporterExcel exporter = new OficinaExporterExcel(listaOficinas);
        exporter.exportar(response);
    }
    
}
