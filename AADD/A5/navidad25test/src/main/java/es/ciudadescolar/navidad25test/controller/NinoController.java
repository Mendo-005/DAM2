package es.ciudadescolar.navidad25test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ciudadescolar.navidad25test.model.Nino;
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
    public List<Nino> listarTodosLosNinos()
    {
        List<Nino> listaNinos = ninoService.getTodosNinos();
        LOG.debug("Peticion GET /api/v1/ninos - recuperamos todos los ninos: " + listaNinos.size());
        return listaNinos;
    }

}
