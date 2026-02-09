package es.ciudadescolar.navidad25;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Navidad25Application {

	private static final Logger LOG = LoggerFactory.getLogger(Navidad25Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Navidad25Application.class, args);
	}

	public void run(String...args) throws Exception
	{
		LOG.info("Hola Spring Boot");
	}
}
