package es.ciudadescolar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("¡Hola! La aplicación está ejecutándose...");
        LOG.info("Programa Ejecutado");
        System.out.println("Aplicación completada correctamente.");
    }
}       