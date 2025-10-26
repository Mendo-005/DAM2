package es.ciudadescolar;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.util.JsonManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {

        JsonManager.parsearJsonAlumnos(new File("alumnos.json"));
    }
}