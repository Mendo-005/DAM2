package es.ciudadescolar;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Empleado;
import es.ciudadescolar.clases.Equipo;
import es.ciudadescolar.util.BdooManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static File fichero = new File("Equipos.bd4o");
    //private static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
 

    public static void main(String[] args) 
    {
        BdooManager manager = new BdooManager(fichero, true);

        // Creamos Equipos
        Equipo IT = new Equipo("IT", "Infraestructura & Tecnologia", 1028000, null);
        Equipo BABI = new Equipo("BABI", "Buisness Analytics & Intelligence", 1528000, null);
        Equipo RRHH = new Equipo("RRHH", "Recursos Humanos", 10282200, null);
        Equipo MRKG = new Equipo("MRKG", "Marketing", 102800, null);

        manager.generarEquipo(IT);
        manager.generarEquipo(BABI);
        manager.generarEquipo(RRHH);
        manager.generarEquipo(MRKG);


        // Creamos Empleados
        Empleado emp1 = new Empleado("Jose", "Salas", 324);
        Empleado emp2 = new Empleado("Carlos","Jose", 233);
        Empleado emp3 = new Empleado("Leo","Pochi", 243);
        Empleado emp4 = new Empleado("Mario","Perez", 253);

        try 
        {
            // Añadimos todos los Empleados
            manager.añadirEmpleado(IT, emp1);
            manager.añadirEmpleado(BABI, emp2);
            manager.añadirEmpleado(RRHH, emp3);
            manager.añadirEmpleado(MRKG, emp4);

            // --- Consultar lista de empleados ---
            List<Empleado> listaEmpleados = manager.getTodosEmpleados("IT");
            for (Empleado empleado : listaEmpleados) 
            {
                LOG.info("Empleados en lista " + empleado);    
            }

            // --- Consultar Empleado por Expediente ---
            Integer exp = 324;
            Empleado empleado = manager.getEmpleadoPorExp(exp);
            if (empleado != null) 
            {
                LOG.info("Empleado recuperado por expediente: " + empleado);
            }
            else
            {
                LOG.warn("No se ha recuperado ningun empleado con ese expediente: " + exp);
            }
        } 
        catch (Exception e) 
        {
            LOG.error("Error en la gestion de la BD: " + e.getMessage());
        } 
        finally
        {
            manager.cerrarBd();
        }

    }  
}