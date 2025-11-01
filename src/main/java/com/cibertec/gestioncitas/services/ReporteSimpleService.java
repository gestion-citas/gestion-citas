package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.entities.Cita;
import com.cibertec.gestioncitas.entities.Medico;
import com.cibertec.gestioncitas.repositories.CitaRepository;
import com.cibertec.gestioncitas.repositories.MedicoRepository;
import com.cibertec.gestioncitas.repositories.EspecialidadRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteSimpleService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    private static final String RUTA_REPORTES = System.getProperty("user.home") + File.separator + "reportes";

    // ========== COLORES CORPORATIVOS ==========
    private static final BaseColor COLOR_PRINCIPAL = new BaseColor(102, 126, 234);
    private static final BaseColor COLOR_SECUNDARIO = new BaseColor(118, 75, 162);
    private static final BaseColor COLOR_GRIS = new BaseColor(108, 117, 125);
    private static final BaseColor COLOR_VERDE = new BaseColor(76, 175, 80);
    private static final BaseColor COLOR_NARANJA = new BaseColor(255, 152, 0);
    private static final BaseColor COLOR_ROJO = new BaseColor(244, 67, 54);

    // ============= REPORTE 1: CITAS POR MÉDICO CON DISEÑO =============
    public String generarReporteCitasPorMedico() {
        try {
            crearDirectorio();
            String nombreArchivo = "citas_por_medico_" + System.currentTimeMillis() + ".pdf";
            String rutaCompleta = RUTA_REPORTES + File.separator + nombreArchivo;

            Document document = new Document(PageSize.A4, 40, 40, 60, 60);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            
            // Agregar header y footer
            writer.setPageEvent(new HeaderFooterPageEvent());
            
            document.open();

            // ========== ENCABEZADO ==========
            agregarEncabezado(document, "REPORTE DE CITAS POR MÉDICO");

            // ========== INFORMACIÓN GENERAL ==========
            List<Medico> medicos = medicoRepository.findAll();
            List<Cita> citas = citaRepository.findAll();

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, COLOR_GRIS);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

            Paragraph info = new Paragraph();
            info.add(new Chunk("Total de Médicos: ", boldFont));
            info.add(new Chunk(String.valueOf(medicos.size()) + "\n", normalFont));
            info.add(new Chunk("Total de Citas: ", boldFont));
            info.add(new Chunk(String.valueOf(citas.size()), normalFont));
            info.setSpacingBefore(10);
            info.setSpacingAfter(15);
            document.add(info);

            // ========== SEPARADOR ==========
            LineSeparator separator = new LineSeparator();
            separator.setLineColor(COLOR_GRIS);
            document.add(new Chunk(separator));
            document.add(Chunk.NEWLINE);

            // ========== AGRUPAR CITAS POR MÉDICO ==========
            Map<Medico, List<Cita>> citasPorMedico = citas.stream()
                    .collect(Collectors.groupingBy(Cita::getMedico));

            for (Map.Entry<Medico, List<Cita>> entry : citasPorMedico.entrySet()) {
                Medico medico = entry.getKey();
                List<Cita> citasMedico = entry.getValue();

                // NOMBRE DEL MÉDICO CON FONDO DE COLOR
                PdfPTable headerMedico = new PdfPTable(1);
                headerMedico.setWidthPercentage(100);
                headerMedico.setSpacingBefore(15);
                headerMedico.setSpacingAfter(10);
                
                PdfPCell cellMedico = new PdfPCell();
                cellMedico.setBackgroundColor(COLOR_PRINCIPAL);
                cellMedico.setPadding(8);
                cellMedico.setBorder(Rectangle.NO_BORDER);
                
                Font medicoFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.WHITE);
                Paragraph nombreMedico = new Paragraph("Dr(a). " + medico.getNombres() + " " + medico.getApellidos(), medicoFont);
                cellMedico.addElement(nombreMedico);
                headerMedico.addCell(cellMedico);
                document.add(headerMedico);

                // ESPECIALIDAD
                Paragraph especialidad = new Paragraph();
                especialidad.add(new Chunk("Especialidad: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                especialidad.add(new Chunk(medico.getEspecialidad().getNombre(), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                especialidad.setSpacingAfter(10);
                document.add(especialidad);

                // TABLA DE CITAS DEL MÉDICO
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{2.5f, 2f, 1.5f, 1.5f});
                table.setSpacingAfter(10);

                // Encabezados
                agregarCeldaEncabezado(table, "Paciente");
                agregarCeldaEncabezado(table, "Fecha");
                agregarCeldaEncabezado(table, "Hora");
                agregarCeldaEncabezado(table, "Estado");

                // Datos
                for (Cita cita : citasMedico) {
                    agregarCeldaDatos(table, cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());
                    agregarCeldaDatos(table, cita.getFecha().toString());
                    agregarCeldaDatos(table, cita.getIdCita() != null ? cita.getIdCita().toString() : "N/A");
                    agregarCeldaEstado(table, cita.getEstado());
                }

                document.add(table);
            }

            document.close();
            return rutaCompleta;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============= REPORTE 2: ESTADÍSTICAS =============
    public String generarReporteEstadisticas() {
        try {
            crearDirectorio();
            String nombreArchivo = "estadisticas_" + System.currentTimeMillis() + ".pdf";
            String rutaCompleta = RUTA_REPORTES + File.separator + nombreArchivo;

            Document document = new Document(PageSize.A4, 40, 40, 60, 60);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            writer.setPageEvent(new HeaderFooterPageEvent());
            
            document.open();

            agregarEncabezado(document, "REPORTE DE ESTADÍSTICAS");

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(70);
            table.setWidths(new float[]{3f, 1f});
            table.setSpacingBefore(15);

            agregarCeldaEncabezado(table, "Concepto");
            agregarCeldaEncabezado(table, "Total");

            agregarCeldaDatos(table, "Total de Médicos");
            agregarCeldaDatosCentrado(table, String.valueOf(medicoRepository.count()));

            agregarCeldaDatos(table, "Total de Especialidades");
            agregarCeldaDatosCentrado(table, String.valueOf(especialidadRepository.count()));

            agregarCeldaDatos(table, "Total de Citas");
            agregarCeldaDatosCentrado(table, String.valueOf(citaRepository.count()));

            document.add(table);
            document.close();

            return rutaCompleta;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    private void agregarEncabezado(Document document, String titulo) throws DocumentException {
        Font smallFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, COLOR_GRIS);
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, COLOR_PRINCIPAL);
        
        Paragraph logo = new Paragraph("🏥 Sistema de Gestión de Citas", smallFont);
        logo.setSpacingAfter(5);
        document.add(logo);

        Paragraph tituloPrincipal = new Paragraph(titulo, tituloFont);
        tituloPrincipal.setAlignment(Element.ALIGN_CENTER);
        tituloPrincipal.setSpacingAfter(5);
        document.add(tituloPrincipal);

        Paragraph fecha = new Paragraph("Fecha de Generación: " + new java.util.Date(), smallFont);
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingAfter(15);
        document.add(fecha);

        LineSeparator line = new LineSeparator();
        line.setLineColor(COLOR_PRINCIPAL);
        line.setLineWidth(2);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void agregarCeldaEncabezado(PdfPTable table, String texto) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, headerFont));
        cell.setBackgroundColor(COLOR_PRINCIPAL);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void agregarCeldaDatos(PdfPTable table, String texto) {
        Font dataFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(texto, dataFont));
        cell.setPadding(6);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    private void agregarCeldaDatosCentrado(PdfPTable table, String texto) {
        Font dataFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(texto, dataFont));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    private void agregarCeldaEstado(PdfPTable table, String estado) {
        BaseColor color;
        if ("CONFIRMADA".equalsIgnoreCase(estado)) {
            color = COLOR_VERDE;
        } else if ("PENDIENTE".equalsIgnoreCase(estado)) {
            color = COLOR_NARANJA;
        } else {
            color = COLOR_ROJO;
        }

        Font estadoFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(estado, estadoFont));
        cell.setBackgroundColor(color);
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void crearDirectorio() {
        File directorio = new File(RUTA_REPORTES);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    // ========== CLASE PARA HEADER Y FOOTER ==========
    class HeaderFooterPageEvent extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable footer = new PdfPTable(1);
			footer.setTotalWidth(document.getPageSize().getWidth() - 80);
			
			Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, COLOR_GRIS);
			PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.TOP);
			cell.setBorderColor(COLOR_GRIS);
			cell.setBorderWidth(0.5f);
			cell.setPaddingTop(10);
			
			Paragraph footerText = new Paragraph("Sistema de Gestión de Citas Médicas - Página " + writer.getPageNumber(), footerFont);
			footerText.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(footerText);
			
			footer.addCell(cell);
			footer.writeSelectedRows(0, -1, 40, 50, writer.getDirectContent());
        }
    }
}

