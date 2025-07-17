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
import com.utp.gp.inventarioSMP.entidades.Equipo;
import com.utp.gp.inventarioSMP.entidades.Usuario;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import lombok.Data;

@Data
public class AsignadoExporterPDF {

    private List<Equipo> listaEquipos;

    public AsignadoExporterPDF(List<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {

        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.darkGray);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.white);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Codigo del Equipo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Descripcion", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Valor", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Tipo de moneda", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Observacion", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Categoria", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Estado", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Usuario asignado", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Equipo equipo : listaEquipos) {
            tabla.addCell(String.valueOf(equipo.getId()));
            tabla.addCell(equipo.getEquipo_codigo());
            tabla.addCell(equipo.getEquipo_descripcion());
            tabla.addCell(String.valueOf(equipo.getValor()));
            tabla.addCell(equipo.getTipoMoneda());
            tabla.addCell(equipo.getObservacion());            
            tabla.addCell(equipo.getCategoria().getCategoria_nombre());
            tabla.addCell(equipo.getEstado());
            Usuario_asignado usuario = equipo.getAsignado();
            if (usuario != null) {
                tabla.addCell(usuario.getNombre());
            } else {
                tabla.addCell(""); 
            }
           
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.blue);
        fuente.setSize(16);

        Paragraph titulo = new Paragraph("Lista de Equipos", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(9);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f, 3f, 4f, 1.6f, 2f, 9f, 3f, 2f, 9f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }

}
