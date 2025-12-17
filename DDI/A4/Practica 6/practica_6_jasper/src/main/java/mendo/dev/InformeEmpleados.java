package mendo.dev;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class InformeEmpleados {

    public static void main(String[] args) {

        // Datos de conexión
        String jdbcUrl = "jdbc:mysql://localhost:3306/practica_informes";
        String username = "root";
        String password = "Root2425"; 
        Connection connection = null;

        try {
            // 1 Conexión a la base de datos
            System.out.println("Conectando a la base de datos...");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Conexión realizada con éxito.");

        // 2 Cargar el informe desde resources como stream (soporta dentro del JAR)
        InputStream reportStream = InformeEmpleados.class
            .getClassLoader()
            .getResourceAsStream("empleados_report.jasper");

        if (reportStream == null) {
        throw new RuntimeException(
            "No se encontró empleados_report.jasper en el classpath (resources)"
        );
        }

        System.out.println("Cargando informe desde el classpath (InputStream)");

        // 3 Rellenar el informe usando InputStream y un mapa de parámetros (vacío si no hay)
        Map<String, Object> parametros = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(
            reportStream,
            parametros,
            connection
        );

            // 4 Mostrar el informe
            JasperViewer.viewReport(jasperPrint, false);

            // 5 Exportar a PDF
            JasperExportManager.exportReportToPdfFile(
                    jasperPrint,
                    "empleados_informe.pdf"
            );

            System.out.println("Informe exportado como empleados_informe.pdf");

        } 
        catch (Exception e) 
        {
            System.err.println("Error al generar el informe");
            e.printStackTrace();
        } 
        finally 
        {
            // 6 Cerrar conexión
            try 
            {
                if (connection != null) 
                {
                    connection.close();
                    System.out.println("Conexión cerrada.");
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}
