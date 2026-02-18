package es.ciudadescolar.navidad25test.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.ciudadescolar.navidad25test.model.Nino;
import es.ciudadescolar.navidad25test.model.Nino.Comportamiento;
import es.ciudadescolar.navidad25test.service.NinoService;

@RestController
@RequestMapping("/api/v1/ninos")
public class NinoController {

    private static final Logger LOG = LoggerFactory.getLogger(NinoController.class);

    private final NinoService ninoService;

    public NinoController(NinoService ninoServiceCons) {
        this.ninoService = ninoServiceCons;
    }

    @GetMapping()
    public ResponseEntity<List<Nino>> listarTodosLosNinos()
    {
        List<Nino> listaNinos = ninoService.getTodosNinos();
        LOG.debug("Peticion GET /api/v1/ninos - recuperamos todos los ninos: " + listaNinos.size());

        if (listaNinos.isEmpty()) 
        {
            return ResponseEntity.noContent().build();    
        }
        else
        {
            return ResponseEntity.ok(listaNinos);
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Nino> getMethodName(@RequestParam Long id) {
        Optional<Nino> ninoBuscado = ninoService.recuperarNinoPorId(id);

        if (ninoBuscado.isPresent()) 
        {
            return  ResponseEntity.ok(ninoBuscado.get());
        } 
        else 
        {
            return  ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/busqueda")
    public ResponseEntity<List<Nino>> busquedaNinosPorComportamiento(@RequestParam Comportamiento comportamiento) {
        List<Nino> ninosBuscados = ninoService.recuperarNinoPorComportamiento(comportamiento);
        if (ninosBuscados.isEmpty()) 
        {
            return ResponseEntity.noContent().build();    
        }
        else
        {
            return ResponseEntity.ok(ninosBuscados);
        }
    }
    
    @GetMapping("/{id}")
    public  ResponseEntity<Void> borrarNinoPorId(@PathVariable Long id) {
        
        if (id != null) {
            
            if (ninoService.borrarNinoPorId(id).isPresent()) 
            {
                ninoService.borrarNinoPorId(id);
                return  ResponseEntity.noContent().build();
            } 
            else 
            {
                return  ResponseEntity.notFound().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

}
