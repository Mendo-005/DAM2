package es.ciudadescolar.navidad25.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.ciudadescolar.navidad25.model.Nino;
import es.ciudadescolar.navidad25.model.Nino.Comportamiento;
import es.ciudadescolar.navidad25.repository.NinoRepository;

@Service
public class NinoService 
{
    private static final Logger LOG = LoggerFactory.getLogger(NinoService.class);

    private final NinoRepository ninoRepository;

    public NinoService(NinoRepository ninoRepository) {
        this.ninoRepository = ninoRepository;
    }

    public Nino registrarNino(String nombreNino, Comportamiento comportamiento)
    {
        List<Nino> ninosExistentes = ninoRepository.findByNombreAndComportamiento(nombreNino, comportamiento);
        
        if(!ninosExistentes.isEmpty())
        {
            LOG.warn("El niño '{}' con comportamiento '{}' ya existe. No se registrará de nuevo.", nombreNino, comportamiento);
            return ninosExistentes.getFirst();
        }
        else
        {
            Nino nuevoNino = new Nino(nombreNino, comportamiento);
            LOG.info("Registrando nuevo niño: {}", nuevoNino);
            return ninoRepository.save(nuevoNino);
        }
    }    

    
}
