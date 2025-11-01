package com.cibertec.gestioncitas.web.controller;

import com.cibertec.gestioncitas.services.ReporteSimpleService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    @Autowired
    private ReporteSimpleService reporteSimpleService;

    // ============= VER PÁGINA DE REPORTES =============
    @GetMapping
    public String verReportes(HttpSession session) {
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/";
        }
        return "dashboard/admin/reportes/index";
    }

    // ============= REPORTE 1: CITAS POR MÉDICO =============
    @GetMapping("/citas-por-medico")
    public void citasPorMedico(HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("usuarioId") == null) {
            response.sendRedirect("/");
            return;
        }
        try {
            String rutaArchivo = reporteSimpleService.generarReporteCitasPorMedico();
            descargarArchivo(response, rutaArchivo, "citas_por_medico.pdf");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ============= REPORTE 2: ESTADÍSTICAS MENSUALES =============
    @GetMapping("/citas-por-especialidad")
    public void citasPorEspecialidad(HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("usuarioId") == null) {
            response.sendRedirect("/");
            return;
        }
        try {
            // Cambio aquí - Usar el método que SÍ existe
            String rutaArchivo = reporteSimpleService.generarReporteEstadisticas();
            descargarArchivo(response, rutaArchivo, "citas_por_especialidad.pdf");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ============= REPORTE 3: PRODUCTIVIDAD DE MÉDICOS =============
    @GetMapping("/productividad-medicos")
    public void productividadMedicos(HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("usuarioId") == null) {
            response.sendRedirect("/");
            return;
        }
        try {
            // Cambio aquí - Usar el método que SÍ existe
            String rutaArchivo = reporteSimpleService.generarReporteCitasPorMedico();
            descargarArchivo(response, rutaArchivo, "productividad_medicos.pdf");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ============= MÉTODO PRIVADO: DESCARGAR ARCHIVO =============
    private void descargarArchivo(HttpServletResponse response, String rutaArchivo, String nombreDescarga) throws IOException {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try (FileInputStream fis = new FileInputStream(archivo);
             OutputStream os = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreDescarga + "\"");
            response.setContentLength((int) archivo.length());
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } catch (IOException e) {
            throw e;
        }
    }
}
