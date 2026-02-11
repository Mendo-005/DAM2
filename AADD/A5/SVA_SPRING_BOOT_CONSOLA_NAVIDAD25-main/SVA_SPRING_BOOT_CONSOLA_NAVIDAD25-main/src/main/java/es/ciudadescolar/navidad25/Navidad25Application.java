package es.ciudadescolar.navidad25;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.ciudadescolar.navidad25.model.Nino;
import es.ciudadescolar.navidad25.service.NinoService;

@SpringBootApplication
public class Navidad25Application implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(Navidad25Application.class);

	private final NinoService ninoService;
	public Navidad25Application(NinoService ninoService1)
	{
		this.ninoService = ninoService1;
	}

	public static void main(String[] args) {
		SpringApplication.run(Navidad25Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
			// lo que antes hacíamos en el main, ahora lo hacemos aquí
			LOG.info("Hola Spring Boot");

			Nino nino1 = ninoService.registrarNino("Juancho", Nino.Comportamiento.bueno);
			LOG.info(nino1.toString());
			
	}

}
