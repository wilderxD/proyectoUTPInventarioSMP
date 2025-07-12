package com.utp.gp.inventarioSMP.util.paginacion;

import com.utp.gp.inventarioSMP.entidades.Usuario;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UsuarioExporterExcel {
    
    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private List<Usuario> listaUsuarios;

    public UsuarioExporterExcel(List<Usuario> listaUsuarios) {
        this.libro = new XSSFWorkbook();
        this.hoja = libro.createSheet("usuarios");
        this.listaUsuarios = listaUsuarios;
    }
    
    private void escribirCabeceraDeLaTabla(){
        Row fila = hoja.createRow(0);
        
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);
        
        Cell celda = fila.createCell(0);
        celda.setCellValue("ID");
        celda.setCellStyle(estilo);
        
        celda = fila.createCell(1);
        celda.setCellValue("Username");
        celda.setCellStyle(estilo);
        
        celda = fila.createCell(2);
        celda.setCellValue("Rol");
        celda.setCellStyle(estilo);
        
        celda = fila.createCell(3);
        celda.setCellValue("Estado");
        celda.setCellStyle(estilo);
        
        celda = fila.createCell(4);
        celda.setCellValue("Fecha de registro");
        celda.setCellStyle(estilo);
    }
    
    private void escribirDatosDeLaTabla(){
        
        int numeroFilas = 1;
        
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(16);
        estilo.setFont(fuente);
        
        for(Usuario usuario : listaUsuarios){
            Row fila = hoja.createRow(numeroFilas++);
            
            Cell celda = fila.createCell(0);
            celda.setCellValue(usuario.getId());
            hoja.autoSizeColumn(0);
            celda.
        }        
         
    }
    
}
