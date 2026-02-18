package mendo.dev;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mendo.dev.dominio.modelo.Usuario;
import mendo.dev.servicios.PerfilServicios;
import mendo.dev.servicios.UsuarioServicio;
import mendo.dev.util.JPAUtil;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    // Debes utilizar el nombre de la persistencia que hay en persistence.xml
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("PersistenciaPractica");

    public static void main(String[] args) 
    {
        LOG.debug ("Inicio de aplicacion");
        try 
        {
            PerfilServicios perfilServicios = new PerfilServicios();
            UsuarioServicio usuarioServicio = new UsuarioServicio();

            usuarioServicio.crearNuevoUsuario("Jacobo", "Sanz de Baranda", LocalDate.of(2000, 3, 8), "jsanz@empresa.es");
            LOG.info("Se ha creado un nuevo usuario");

            Usuario usuario1 = usuarioServicio.buscarUsuarioPorEmail("jsanz@empresa.es");
            perfilServicios.crearNuevoPerfil("jsanz3", "asdfasnmxXXXXqlk2334nmwe", usuario1);
            LOG.info("Se ha creado un nuevo perfil");

            perfilServicios.actualizarPerfil("010101010", "jsanz3");
            LOG.info("Se ha actualizado el perfil");

            usuarioServicio.eliminarUsuario(usuario1);
            LOG.info("Se ha borrado el usuario: " + usuario1);

            List<Usuario> listaDeUsuarios = usuarioServicio.listarTodosLosUsuarios();
            LOG.debug("Usuarios : "+listaDeUsuarios.size());
            for (Usuario usuario : listaDeUsuarios) {
                LOG.info(usuario.toString());
            }
        } 
        catch (Exception e) 
        {
            LOG.error ("Error en la aplicacion: "+e.getMessage());
        } 
        finally 
        {
            JPAUtil.close(); 
        }
        LOG.debug ("Fin de aplicacion");
    }
}