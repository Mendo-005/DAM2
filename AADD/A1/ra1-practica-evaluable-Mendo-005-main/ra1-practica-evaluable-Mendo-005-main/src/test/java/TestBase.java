import java.io.File;

import org.junit.jupiter.api.BeforeAll;

import es.ciudadescolar.Main;

public class TestBase {
    private static boolean started = false;

    @BeforeAll
    static void runOnce()
    {
        if (!started)
        {   
            borrarSalida();
            Main.main(null);
            started= true;
        }
    }
    private static void borrarSalida()
    {
        File carpeta = new File(".");
        File[] candidatos = carpeta.listFiles((dir, name) -> name.startsWith("hospital_Zaragoza_"));
        if (candidatos != null)
            for (File fich:candidatos)
            {
                fich.delete();
            }
    } 
}
