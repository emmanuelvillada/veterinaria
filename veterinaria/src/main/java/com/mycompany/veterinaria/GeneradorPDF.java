package com.mycompany.veterinaria;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;

public class GeneradorPDF {

    public static void generarHistoriaClinica(Mascota mascota, List<Consulta> consultas, String rutaArchivo) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            // Título
            document.add(new Paragraph("CLÍNICA VETERINARIA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // espacio

            // Datos de la mascota
            document.add(new Paragraph("Nombre: " + mascota.getNombre()));
            document.add(new Paragraph("Especie: " + mascota.getEspecie()));
            document.add(new Paragraph("Raza: " + mascota.getRaza()));
            document.add(new Paragraph(" ")); // espacio

            // Consultas
            document.add(new Paragraph("Consultas:"));
            PdfPTable table = new PdfPTable(3); // Fecha, Veterinario, Motivo
            table.addCell("Fecha");
            table.addCell("Veterinario");
            table.addCell("Motivo");

            for (Consulta c : consultas) {
                table.addCell(c.getFecha());
                table.addCell("ID " + c.getIdVeterinario()); // o nombre si lo obtienes
                table.addCell(c.getMotivo());
            }

            document.add(table);
            document.close();

            System.out.println("PDF generado correctamente en: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
