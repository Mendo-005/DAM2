package dev.mendo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JPAUtil.class);
    
    // Debes utilizar el nombre de la persistencia que hay en persistence.xml
    private static final EntityManagerFactory EMF;
    
    static{
        EMF = Persistence.createEntityManagerFactory("Persistencia");
        LOG.debug("Creando pool de conexiones...");
    }
    
    /**
     * Creamos un metodo que solicitara un EntityManager
     * @return EntityManager
     */
    public static EntityManager getEntityManager(){ 
        LOG.debug("Nueva conexion del pool solicitada");
        return EMF.createEntityManager();
    }

    /**
     * Metodo que solicita el Service para crear transacciones
     * @param entityManager
     * @return EntityTransaction
     */
    public static EntityTransaction getEntityTransaction(EntityManager entityManager){
        LOG.debug("Nueva transaccion del pool solicitada");
        return entityManager.getTransaction();
    }

    /**
     * Metodo para cerrar el pool de conexiones desde el Main
     */
    public static void close() {
        if (EMF.isOpen())
        {
            EMF.close();
            LOG.debug("Se ha cerrado el pool de conexiones con la BD");
        }
    }
}
