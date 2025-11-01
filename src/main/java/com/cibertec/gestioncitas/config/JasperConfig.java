package com.cibertec.gestioncitas.config;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JasperConfig {

    @Bean
    public JasperReport reporteCitasPorMedico() {
        return compileReport("/reportes/citas_por_medico.jrxml");
    }

    @Bean
    public JasperReport reporteCitasPorEspecialidad() {
        return compileReport("/reportes/citas_por_especialidad.jrxml");
    }

    @Bean
    public JasperReport reporteEstadisticasMensuales() {
        return compileReport("/reportes/estadisticas_mensuales.jrxml");
    }

    @Bean
    public JasperReport reporteProductividadMedicos() {
        return compileReport("/reportes/productividad_medicos.jrxml");
    }

    @Bean
    public JasperReport reporteEstadistico() {
        return compileReport("/reportes/reporte_estadistico.jrxml");
    }

    private JasperReport compileReport(String reportPath) {
        try {
            JasperReport report = JasperCompileManager.compileReport(
                new ClassPathResource(reportPath).getInputStream()
            );
            return report;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error compilando reporte " + reportPath, e);
        }
    }
}

