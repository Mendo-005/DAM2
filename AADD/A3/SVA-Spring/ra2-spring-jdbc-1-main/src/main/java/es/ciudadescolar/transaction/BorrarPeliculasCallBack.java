package es.ciudadescolar.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;


public class BorrarPeliculasCallBack implements TransactionCallback<Void> {
/**
 * La notación de la clase recuerda el patrón de diseño utilizado:Callback Pattern

    Spring implementa un clásico:  

    Callback Pattern + Template Method Pattern

    TransactionTemplate es la plantilla (Template)

    Nosotros implementamos el callback (TransactionCallback)

    El framework controla cuándo se ejecuta tu código

 */
    private static final Logger LOG = LoggerFactory.getLogger(BorrarPeliculasCallBack.class);

    private final JdbcTemplate jdbc;

    public BorrarPeliculasCallBack(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Void doInTransaction(TransactionStatus status) {
    try {
            jdbc.update("DELETE FROM film WHERE film_id = ?", 20);
            jdbc.update("DELETE FROM film WHERE film_id = ?", 21);
            LOG.info("Se borraron las películas 20 y 21");
        } catch (Exception e) {
            LOG.error("Error en transacción: " + e.getMessage());
            status.setRollbackOnly();
        }
        return null;
    }

    
}
