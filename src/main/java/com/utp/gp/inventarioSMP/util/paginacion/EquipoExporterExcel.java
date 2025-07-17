package com.utp.gp.inventarioSMP.util.paginacion;

import com.utp.gp.inventarioSMP.entidades.Equipo;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EquipoExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private List<Equipo> listaEquipos;

    public EquipoExporterExcel(List<Equipo> listaEquipos) {
        this.libro = new XSSFWorkbook();
        this.hoja = libro.createSheet("equipos");
        this.listaEquipos = listaEquipos;
    }

    private void escribirCabeceraDeLaTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(11);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("ID");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Codigo del Equipo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("descripcion");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("valor");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("tipo moneda");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("observacion");
        celda.setCellStyle(estilo);

        celda = fila.createCell(6);
        celda.setCellValue("categoria");
        celda.setCellStyle(estilo);

        celda = fila.createCell(7);
        celda.setCellValue("estado");
        celda.setCellStyle(estilo);

        celda = fila.createCell(8);
        celda.setCellValue("Usuario asignado");
        celda.setCellStyle(estilo);
    }

    private void escribirDatosDeLaTabla() {

        int numeroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(11);
        estilo.setFont(fuente);

        for (Equipo equipo : listaEquipos) {
            Row fila = hoja.createRow(numeroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(equipo.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(equipo.getEquipo_codigo());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(equipo.getEquipo_descripcion());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(equipo.getValor());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(equipo.getTipoMoneda());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(equipo.getObservacion());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(6);
            celda.setCellValue(equipo.getCategoria().getCategoria_nombre());
            hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(equipo.getEstado());
            hoja.autoSizeColumn(7);
            celda.setCellStyle(estilo);

            Usuario_asignado usuario = equipo.getAsignado();
            if (usuario != null) {
                celda = fila.createCell(8);
                celda.setCellValue(usuario.getNombre());
                hoja.autoSizeColumn(8);
                celda.setCellStyle(estilo);
            } else {
                celda = fila.createCell(8);
                celda.setCellValue("");
                hoja.autoSizeColumn(8);
                celda.setCellStyle(estilo);
            }
           
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirCabeceraDeLaTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);

        libro.close();
        outputStream.close();
    }

}
