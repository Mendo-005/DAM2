package dev.mendo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.servicio.AlumnoService;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) 
    {
        LOG.info("iniciando aplicacion");
        AlumnoService alumnoService = new AlumnoService();
        alumnoService.añadirAlumno("Dario", "Pazos", Integer.valueOf(101));
        alumnoService.añadirAlumno("Fermin", "Sanz", Integer.valueOf(102));
    }
}