
package com.utp.gp.inventarioSMP.util.paginacion;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import lombok.Data;

@Data
public class UsuarioExporterPDF {
    
    private List<Usuario> listaUsuarios;
    
    public UsuarioExporterPDF(List<Usuario> listaUsuarios){
        this.listaUsuarios = listaUsuarios;
    }
    
    private void escribirCabeceraDeLaTabla(PdfPTable tabla){
        
        PdfPCell celda = new PdfPCell();
        
        celda.setBackgroundColor(Color.darkGray);
        celda.setPadding(5);
        
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.white);
        
        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);
        
        celda.setPhrase(new Phrase("Username", fuente));
        tabla.addCell(celda);
        
        celda.setPhrase(new Phrase("Rol", fuente));
        tabla.addCell(celda);
        
        celda.setPhrase(new Phrase("Estado", fuente));
        tabla.addCell(celda);
        
        celda.setPhrase(new Phrase("Fecha de creación", fuente));
        tabla.addCell(celda);        
    }
    
    private void escribirDatosDeLaTabla(PdfPTable tabla){
        for(Usuario usuario: listaUsuarios){
            tabla.addCell(String.valueOf(usuario.getId()));
            tabla.addCell(usuario.getUsername());
            tabla.addCell(usuario.getRol().getNombre());
            tabla.addCell(usuario.getStatus());
            tabla.addCell(usuario.getFecha_modificacion().toString());
        }        
    }
    
    public void exportar(HttpServletResponse response) throws IOException{
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());
        
        documento.open();
        
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.blue);
        fuente.setSize(18);
        
        Paragraph titulo = new Paragraph("Lista de Usuarios", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);
        
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] {1f,2.3f,2.3f,2f,6f});
        tabla.setWidthPercentage(110);
        
        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);
        
        documento.add(tabla);
        documento.close();
    }
    
}
