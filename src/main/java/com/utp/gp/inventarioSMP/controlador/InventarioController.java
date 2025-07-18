package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.entidades.Categoria;
import com.utp.gp.inventarioSMP.entidades.Equipo;
import com.utp.gp.inventarioSMP.servicio.ICategoria;
import com.utp.gp.inventarioSMP.servicio.IEquipo;
import com.utp.gp.inventarioSMP.servicio.IInventario;
import com.utp.gp.inventarioSMP.util.paginacion.PageRender;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InventarioController {

    @Autowired
    private IInventario iEquipo;

    @Autowired
    private ICategoria iCategoria;

    @GetMapping("/listarInventario")
    public String listarEquipo(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "asignacion", required = false) String asignacion,
            Model modelo) {

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Equipo> equipos;

        // Lógica de filtrado completa
        if (categoriaId != null && categoriaId > 0) {
            equipos = iEquipo.findByCategoriaId(categoriaId, pageRequest);
        } else if (asignacion != null) {
            if ("asignados".equals(asignacion)) {
                equipos = iEquipo.findByAsignadoIsNotNull(pageRequest);
            } else if ("sin-asignar".equals(asignacion)) {
                equipos = iEquipo.findByAsignadoIsNull(pageRequest);
            } else {
                equipos = iEquipo.findAll(pageRequest);
            }
        } else {
            equipos = iEquipo.findAll(pageRequest);
        }

        // Asegúrate de enviar estos atributos al modelo
        List<Categoria> categorias = iCategoria.findAll();

        modelo.addAttribute("titulo", "Listado de Equipos");
        modelo.addAttribute("equipos", equipos);
        modelo.addAttribute("categorias", categorias);
        modelo.addAttribute("selectedCategoria", categoriaId);
        modelo.addAttribute("selectedAsignacion", asignacion);
        modelo.addAttribute("page", new PageRender<>("/listarEquipo", equipos));

        return "listarInventario"; // Asegúrate que coincida con tu template
    }

    @GetMapping("/exportarInventarioPDF")
    public void exportarEquiposPDF(
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "asignacion", required = false) String asignacion,
            HttpServletResponse response) throws IOException {

        List<Equipo> equipos;

        if (categoriaId != null && categoriaId > 0) {
            equipos = iEquipo.findByCategoriaId(categoriaId);
        } else if (asignacion != null) {
            if ("asignados".equals(asignacion)) {
                equipos = iEquipo.findAllAsignados();
            } else if ("sin-asignar".equals(asignacion)) {
                equipos = iEquipo.findAllNoAsignados();
            } else {
                equipos = iEquipo.findAll();
            }
        } else {
            equipos = iEquipo.findAll();
        }

        // Resto del código de exportación PDF...
    }

    @GetMapping("/exportarInventarioExcel")
    public void exportarEquiposExcel(
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "asignacion", required = false) String asignacion,
            HttpServletResponse response) throws IOException {

        List<Equipo> equipos;

        if (categoriaId != null && categoriaId > 0) {
            equipos = iEquipo.findByCategoriaId(categoriaId);
        } else if (asignacion != null) {
            if ("asignados".equals(asignacion)) {
                equipos = iEquipo.findAllAsignados();
            } else if ("sin-asignar".equals(asignacion)) {
                equipos = iEquipo.findAllNoAsignados();
            } else {
                equipos = iEquipo.findAll();
            }
        } else {
            equipos = iEquipo.findAll();
        }

        // Resto del código de exportación Excel...
    }

}
