package dev.mendo;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.servicio.AlumnoService;
import dev.mendo.servicio.EmpresaService;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) 
    {
        LOG.info("iniciando aplicacion");
        AlumnoService alumnoService = new AlumnoService();
        
        // Alumno 1: Id 101, Dario Pazos, 15/06/05, email: dario.pazos@instituto.es, teléfono: 912443213
        alumnoService.añadirAlumno("Dario", "Pazos", 101, 
                                  LocalDate.of(2005, 6, 15), 
                                  "dario.pazos@instituto.es", 
                                  "912443213");
        
        // Alumno 2: Id 102, Fermin Sanz, 21/09/02, email: fermin.sanz@instituto.es, teléfono: 6660006666
        alumnoService.añadirAlumno("Fermin", "Sanz", 102, 
                                  LocalDate.of(2002, 9, 21), 
                                  "fermin.sanz@instituto.es", 
                                  "6660006666");
        
        // 2)
        EmpresaService empresaService = new EmpresaService();
        empresaService.actualizarEmpresa(1, "Carretera de Colmenar Km 12,800");

        // 3)
        String consultas = empresaService.listaDeEmpresas();
        LOG.info(consultas);
        LOG.info("Aplicacion finalizada");
    }
}