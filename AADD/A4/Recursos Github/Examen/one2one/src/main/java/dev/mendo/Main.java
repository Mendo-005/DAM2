package dev.mendo;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Perfil;
import dev.mendo.dominio.modelo.Usuario;
import dev.mendo.servicio.UsuarioServicio;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        LOG.info("========= APP INICIADA ===============");
        // 1)
        Usuario usuario = new Usuario("Jacobo","Sanz de Baranda",LocalDate.of(2000, 03, 8) ,"jsanz@empresa.es");
        Perfil perfil = new Perfil("jsanz3", "asdfasnmxXXXXqlk2334nmwe.3kn3$");
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        usuarioServicio.crearUsuarioCascadePerfil(usuario, perfil);

        // 2)
        Perfil newPerfil = new Perfil("Messi10", "joseSala");
        usuarioServicio.actualizarPrefilDelUsuario(usuario, newPerfil);

        // 3)
        usuarioServicio.borrarUsuarioYPerfil(usuario);

        // 4)
        List<String> reporte = usuarioServicio.reporteDeUsuarios();
        for (String entrada : reporte) 
        {
            LOG.info(entrada);    
        }
        
        LOG.info("========= APP CERRADA ===============");
    }
}