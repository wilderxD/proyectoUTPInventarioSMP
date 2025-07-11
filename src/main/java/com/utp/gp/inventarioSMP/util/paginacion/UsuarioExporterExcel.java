package com.utp.gp.inventarioSMP.util.paginacion;

import com.utp.gp.inventarioSMP.entidades.Usuario;
import java.util.List;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Data
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
    }
    
}
