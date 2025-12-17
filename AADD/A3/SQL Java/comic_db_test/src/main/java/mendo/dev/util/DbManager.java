package mendo.dev.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);
    private static String URL = "url";
    private static String DRIVER = "driver";
    private static String USER = "user";
    private static String PWD = "password";

    private static Connection con = null;

    public DbManager()
    {
        Properties prop = new Properties();

        try 
        {
            prop.load(new FileInputStream("prop.properties"));
            Class.forName(prop.getProperty(DRIVER));
            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USER), prop.getProperty(PWD));
            LOG.info("Conexion realizada con exito");

        }
        catch (IOException | SQLException | ClassNotFoundException e) 
        {
            LOG.error("Error creando la conexion con la BD: " + e.getMessage());
        }
    }

    /* 
        Desarrolla un programa en JAVA con Maven que interactúe con la BD relacional ComicStore_BD suministrada y:

        a) permita volcar a un LOG los creadores de un determinado cómic (parámetro - título del cómic) 
        
        b) recupere el precio de un cómic invocando la función existente "getPrecioComic": ejemplo select titulo AS 'titulo comic', getPrecioComic(cod_comic) AS 'precio venta' from Comic; 
        
        c) permita asignar un creador y rol a un determinado cómic invocando el procedimiento existente "asignarCreadorComic": ejemplo CALL asignarCreadorComic('Stan Lee','Amazing Fantasy','Escritor'); 
        Nota: el creador y el cómic deben existir en la BD. 
        
        d) Dar de alta de forma transaccional el siguiente cómic y sus creadores: cómic: "Spawn", Número 1, Precio $3.50, Editorial 'Image Comics', Creadores: Todd McFarlane (Escritor y Dibujante).

        Ojo: habrá que dar de alta al creador **Todd McFarlane** ya que no existe en la tabla 
    */

    public List<String> getAutorComic(String titulo)
    {
        List<String> listaAutores = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        if (con != null) 
        {
            try 
            {
                pst = con.prepareStatement(SQL.GET_CREADORES_COMIC);
                pst.setString(1, titulo);
                rst = pst.executeQuery();

                listaAutores = new ArrayList<>();
                if (rst.next()) 
                {
                    String autor = rst.getString(1);

                    listaAutores.add(autor);
                }

                LOG.debug("Autores recuperados con exito");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error al ejecutar getAutorComic(): " + e.getMessage());
            }
            finally
            {
                try 
                {
                    if (pst != null) {pst.close();}
                    if (rst != null) {rst.close();}
                    LOG.debug("Recursos liberados con exito");
                } 
                catch (SQLException e) 
                {
                    LOG.error("Error liberando los recursos: " + e.getMessage());
                }
            }
        }

        return listaAutores;
    }

    public Double getPrecioComic(Integer id_comic)
    {
        Double precio = null;
        CallableStatement cs = null;

        if (con != null) 
        {
            try 
            {
                cs = con.prepareCall(SQL.FUN_GET_PRECIO);
                cs.setInt(2, id_comic);

                cs.registerOutParameter(1, Types.DOUBLE);
                cs.execute();
                
                precio = Double.valueOf(cs.getDouble(1));
                LOG.debug("Funcion ejecutada correctamente");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error al ejecutar getPrecioComic(): " + e.getMessage());

            }
            finally
            {
                try 
                {
                    if (cs != null) {cs.close();}
                    LOG.debug("Recursos liberados con exito");
                } 
                catch (SQLException e) 
                {
                    LOG.error("Error liberando los recursos: " + e.getMessage());
                }
            }
        }

        return precio;
    }

    public boolean addAsignarCreadorComic(String nombre_creador, String titulo_comic, String rol_creador)
    {
        boolean status = false;
        CallableStatement cs = null;

        if (con != null) 
        {
            try 
            {
                cs = con.prepareCall(SQL.SP_ADD_ASIGNAR_CREADOR);
                cs.setString(1, nombre_creador);
                cs.setString(2, titulo_comic);
                cs.setString(3, rol_creador);

                cs.execute();
                
                status = true;
                LOG.debug("Se ha asignado correctamente al creador");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error al ejecutar addAsignarCreadorComic(): " + e.getMessage());
            }
            finally
            {
                try 
                {
                    if (cs != null) {cs.close();}
                    LOG.debug("Recursos liberados con exito");
                } 
                catch (SQLException e) 
                {
                    LOG.error("Error liberando los recursos: " + e.getMessage());
                }
            }
        }

        return status;
    }

    public boolean addCreadoresYComic(String nombre_creador, String rol_creador, String titulo_comic,
                                      Integer numero_comic, Double precio_comic, Integer cod_editorial, 
                                      Date fecha_publicacion )
    {
        boolean status = false;
        PreparedStatement pstAddComic = null;
        PreparedStatement pstAddCreador = null;
        PreparedStatement pstAddCreadorComic = null;
        PreparedStatement pstCreador = null;
        ResultSet rst = null;

        Integer cod_autor = null;
        Integer cod_comic = null;

        List<String> listaAutores = null;

        if (con != null) 
        {
            try 
            {
                pstAddComic = con.prepareStatement(SQL.ADD_COMIC, Statement.RETURN_GENERATED_KEYS  );
                pstAddCreador = con.prepareStatement(SQL.ADD_AUTOR, Statement.RETURN_GENERATED_KEYS );
                pstAddCreadorComic = con.prepareStatement(SQL.ADD_AUTOR_COMIC);

                con.setAutoCommit(false);

                // Autor
                pstCreador = con.prepareStatement(SQL.GET_AUTORES);
                rst = pstCreador.executeQuery();
                if (rst.next()) 
                {
                    listaAutores = new ArrayList<>();
                    do 
                    {
                        String autor = rst.getString(1);
                        listaAutores.add(autor);

                    } while (rst.next());    
                }

                if (!listaAutores.contains(nombre_creador)) 
                {   
                    pstAddCreador.setString(1, nombre_creador);
                    pstAddCreador.executeUpdate();    
                }
                
                try (ResultSet rsKeys = pstAddCreador.getGeneratedKeys()) 
                {
                    if (rsKeys.next()) 
                    {
                        cod_autor = rsKeys.getInt(1);
                    }
                }

                // Comic
                pstAddComic.setString(1, titulo_comic);
                pstAddComic.setInt(2, numero_comic);
                pstAddComic.setDouble(3, precio_comic);
                pstAddComic.setInt(4, cod_editorial);
                pstAddComic.setDate(5, fecha_publicacion);
                pstAddComic.executeUpdate();

                try (ResultSet rsKeys = pstAddComic.getGeneratedKeys()) 
                {
                    if (rsKeys.next()) 
                    {
                        cod_comic = rsKeys.getInt(1);
                    }
                }

                // Comic_Autor
                pstAddCreadorComic.setInt(1, cod_comic);
                pstAddCreadorComic.setInt(2, cod_autor);
                pstAddCreadorComic.setString(3, rol_creador);
                pstAddCreadorComic.executeUpdate();

                con.commit();
                LOG.debug("Se ha añadido correctamente el comic a la BD");
                status = true;
            } 
            catch (SQLException e) 
            {
                LOG.error("Error al ejecutar addCreadoresYComic(): " + e.getMessage());
                try 
                {
                    con.rollback();
                    LOG.debug("Rollback realizado con exito");
                } 
                catch (SQLException ex) 
                {
                    LOG.error("Error al ejecutar el rollback: " + ex.getMessage());
                }
            }
            finally
            {
                try 
                {
                    con.setAutoCommit(true);
                    if (pstAddComic != null) {pstAddComic.close();}
                    if (pstAddCreador != null) {pstAddCreador.close();}
                    if (pstCreador != null) {pstCreador.close();}
                    LOG.debug("Recursos liberados con exito");
                } 
                catch (SQLException e) 
                {
                    LOG.error("Error liberando los recursos: " + e.getMessage());
                }
            }
        }

        return status;
    }
}

    

