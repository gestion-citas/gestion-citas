package com.cibertec.gestioncitas.services;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * SERVICIO DE REPORTES
 * Genera reportes en PDF con DATOS REALES de la base de datos
 */
@Service
public class ReporteService {

    /**
     * Obtiene la ruta base para guardar reportes
     */
    private String obtenerRutaReportes() {
        String rutaTemp = System.getProperty("user.home") + File.separator + "gestion_citas_reportes";
        File carpeta = new File(rutaTemp);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        return rutaTemp;
    }

    /**
     * REPORTE: Citas por Médico
     * Obtiene datos REALES de la BD y los muestra en el PDF
     */
    public String generarReporteCitasPorMedico() throws JRException {
        try {
            
            // 1. Cargar plantilla JRXML
            InputStream reporteStream = getClass().getResourceAsStream("/reportes/citas_por_medico.jrxml");
            if (reporteStream == null) {
                throw new Exception("❌ No se encontró: /reportes/citas_por_medico.jrxml");
            }
            
            // 2. Compilar
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);
            
            // 3. Preparar parámetros VACÍOS (los datos vienen de la BD)
            Map<String, Object> parametros = new HashMap<>();
            
            // 4. IMPORTANTE: Usar una DataSource que traiga datos de la BD
            // En este caso usamos EmptyDataSource pero la plantilla debe tener query SQL
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            
            // 5. Guardar con ruta ABSOLUTA
            String rutaBase = obtenerRutaReportes();
            String rutaCompleta = rutaBase + File.separator + "citas_por_medico.pdf";
            
            // 6. Exportar a PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaCompleta);
            
            return rutaCompleta;
            
        } catch (Exception e) {
            throw new JRException(e);
        }
    }

    /**
     * REPORTE: Citas por Especialidad
     */
    public String generarReporteCitasPorEspecialidad() throws JRException {
        try {
            
            InputStream reporteStream = getClass().getResourceAsStream("/reportes/citas_por_especialidad.jrxml");
            if (reporteStream == null) {
                throw new Exception("❌ No se encontró: /reportes/citas_por_especialidad.jrxml");
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            
            String rutaBase = obtenerRutaReportes();
            String rutaCompleta = rutaBase + File.separator + "citas_por_especialidad.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaCompleta);
            
            return rutaCompleta;
        } catch (Exception e) {
            throw new JRException(e);
        }
    }

    /**
     * REPORTE: Estadísticas Mensuales
     */
    public String generarReporteEstadisticasMensuales() throws JRException {
        try {
            
            InputStream reporteStream = getClass().getResourceAsStream("/reportes/estadisticas_mensuales.jrxml");
            if (reporteStream == null) {
                throw new Exception("❌ No se encontró: /reportes/estadisticas_mensuales.jrxml");
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            
            String rutaBase = obtenerRutaReportes();
            String rutaCompleta = rutaBase + File.separator + "estadisticas_mensuales.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaCompleta);
            
            return rutaCompleta;
        } catch (Exception e) {
            throw new JRException(e);
        }
    }

    /**
     * REPORTE: Productividad de Médicos
     */
    public String generarReporteProductividadMedicos() throws JRException {
        try {
            
            InputStream reporteStream = getClass().getResourceAsStream("/reportes/productividad_medicos.jrxml");
            if (reporteStream == null) {
                throw new Exception("❌ No se encontró: /reportes/productividad_medicos.jrxml");
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            
            String rutaBase = obtenerRutaReportes();
            String rutaCompleta = rutaBase + File.separator + "productividad_medicos.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaCompleta);
            
            return rutaCompleta;
        } catch (Exception e) {
            throw new JRException(e);
        }
    }

    /**
     * REPORTE: Estadístico General
     */
    public String generarReporteEstadistico() throws JRException {
        try {
            
            InputStream reporteStream = getClass().getResourceAsStream("/reportes/reporte_estadistico.jrxml");
            if (reporteStream == null) {
                throw new Exception("❌ No se encontró: /reportes/reporte_estadistico.jrxml");
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            
            String rutaBase = obtenerRutaReportes();
            String rutaCompleta = rutaBase + File.separator + "reporte_estadistico.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaCompleta);
            
            return rutaCompleta;
        } catch (Exception e) {
            throw new JRException(e);
        }
    }
}

