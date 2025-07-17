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

    private List<Usuario_asignado> listaAsignados;

    public AsignadoExporterPDF(List<Usuario_asignado> listaAsignados) {
        this.listaAsignados = listaAsignados;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {

        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.darkGray);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.white);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("DNI", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Descripcion equipo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Oficina", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha de asignacion", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Usuario_asignado asignado : listaAsignados) {
            tabla.addCell(String.valueOf(asignado.getId()));
            tabla.addCell(asignado.getNombre());
            tabla.addCell(asignado.getCodigo());
            tabla.addCell(asignado.getEquipo().getEquipo_descripcion());
            tabla.addCell(asignado.getOficina().getNombre_oficina());
            tabla.addCell(asignado.getFecha_asignado().toString());
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.blue);
        fuente.setSize(16);

        Paragraph titulo = new Paragraph("Lista de Asignaciones", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f, 4f, 2f, 6f, 4f, 4f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }

}
