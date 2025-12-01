package es.ciudadescolar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Alumno;
import es.ciudadescolar.clases.Instituto;
import es.ciudadescolar.util.BdooManager;

public class Main 
{        
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        BdooManager bd = new BdooManager(new File("instituto.db4o"), true);

        // Creamos Instituto
        Instituto ciudadEscolar = new Instituto("Ciudad Escolar", 100, null);
        bd.generarInstituto(ciudadEscolar);

        // Creamos Alumnos
        Alumno alumno = new Alumno("Mario","999", 23);
        Alumno alumno2 = new Alumno("Paco","222", 55);
        Alumno alumno3 = new Alumno("Jose","544", 66);
        Alumno alumno4 = new Alumno("Maria","121", 12);
        
        try 
        {
            // Matricular alumnos usando el Manager (NO la clase Instituto)
            bd.matricularAlumno(ciudadEscolar, alumno);
            bd.matricularAlumno(ciudadEscolar, alumno2);
            bd.matricularAlumno(ciudadEscolar, alumno3);
            bd.matricularAlumno(ciudadEscolar, alumno4);

            bd.commitTransaction();

            LOG.info("--- PRUEBAS DE CONSULTAS Y LÓGICA ---");

            // Modificar Alumno por expediente
            bd.modificarAlumnoExp("Jose", "789");
            
            // Borrar alumno
            bd.expulsarAlumno(ciudadEscolar, alumno);
            bd.matricularAlumno(ciudadEscolar, alumno);

            // Borrar alumno por expediente
            bd.expulsarAlumnoExp("999");

            // 1. Consultar todos los alumnos [cite: 44]
            List<Alumno> lista = bd.getTodosAlumnos("Ciudad Escolar");
            for (Alumno al : lista) 
            {
                LOG.info("Alumno recuperado: " + al);    
            }

            // 2. Consultar instituto por alumno (SODA) [cite: 51]
            Instituto instiPaco = bd.consultaInstiMatriculado("Paco");
            if (instiPaco != null)
            {
                LOG.info("El alumno Paco estudia en: " + instiPaco.getNombre());
            }

            // 3. Consultar instituto por ID (String) [cite: 50]
            Instituto instiId = bd.consultaMatriculasInstituto("100");
            if (instiId != null)
            {
                LOG.info("Instituto con ID 100 encontrado: " + instiId.getNombre());
            }

            // 4. Expulsar a todos los alumnos (Transaccional) [cite: 49]
            LOG.info("--- EXPULSANDO A TODOS LOS ALUMNOS ---");
            bd.expulsarATodosAlumnos("Ciudad Escolar");
            bd.commitTransaction();

            // Verificación
            List<Alumno> vacia = bd.getTodosAlumnos("Ciudad Escolar");
            LOG.info("Alumnos tras expulsión masiva: " + vacia.size());

        } 
        catch (Exception e) 
        {
            bd.rollbackTransaction();
            LOG.warn("No se ha podido realizar la transaccion: " + e.getMessage());    
        }
        finally
        {
            bd.cerrar();
        }
    }
}