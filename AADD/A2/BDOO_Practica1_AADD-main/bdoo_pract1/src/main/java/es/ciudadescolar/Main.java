package es.ciudadescolar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Alumno;
import es.ciudadescolar.clases.Instituto;
import es.ciudadescolar.util.BdooManager;

public class Main {
        
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        BdooManager bd = new BdooManager(new File("instituto.db4o"), true);

        Instituto ciudadEscolar = new Instituto("Ciudad Escolar", null, null);
        bd.generarInstituto(ciudadEscolar);

        List<Alumno> listaAlumnos = new ArrayList<>();

        Alumno alumno = new Alumno("Mario","999", 23);
        Alumno alumno2 = new Alumno("Paco","222", 55);
        Alumno alumno3 = new Alumno("Jose","544", 66);
        Alumno alumno4 = new Alumno("Maria","121", 12);
        
        try 
        {
            listaAlumnos.add(alumno);        
            listaAlumnos.add(alumno2);        
            listaAlumnos.add(alumno3);        
            listaAlumnos.add(alumno4);
            for (Alumno al : listaAlumnos) 
            {
                LOG.info("Alumno en pre lista: " + al);    
            }

            ciudadEscolar.setListaAlumnos(listaAlumnos);
            bd.matricularAlumno(alumno);
            bd.matricularAlumno(alumno2);
            bd.matricularAlumno(alumno3);
            bd.matricularAlumno(alumno4);
            bd.commitTransaction();

        } 
        catch (Exception e) 
        {
            bd.rollbackTransaction();
            LOG.warn("No se ha podido realizar la transaccion");    
        }
    }
}