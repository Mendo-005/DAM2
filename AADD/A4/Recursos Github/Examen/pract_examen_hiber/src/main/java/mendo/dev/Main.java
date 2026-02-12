package mendo.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import mendo.dev.dominio.modelo.Perfil;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    // Debes utilizar el nombre de la persistencia que hay en persistence.xml
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("PersistenciaPractica");

    public static void main(String[] args) 
    {
        
    }
}