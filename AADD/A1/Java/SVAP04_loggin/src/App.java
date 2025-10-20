import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private  static final Logger LOG = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws Exception 
    {
        LOG.info("Este evento representa una información general de nuestra aplicación.");
        LOG.warn("Este evento representa una advertencia sobre un posible problema futuro.");
        LOG.error("Este evento representa un error que ha ocurrido en la aplicación.");
        LOG.trace("Este evento representa información de seguimiento detallada.");
        LOG.debug("Este evento representa información de depuración.");
    }
}
    