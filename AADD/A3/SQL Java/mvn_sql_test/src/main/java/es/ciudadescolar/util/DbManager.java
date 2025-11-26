package es.ciudadescolar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.clases.Alumno;

public class DbManager {

    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);
    private static final String DRIVER = "driver";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private Connection con = null;

    public DbManager()
    {
        Properties prop = new  Properties();

        try 
        {   
            prop.load(new FileInputStream("conexionBD.properties"));
            Class.forName(prop.getProperty("driver"));
            //con = DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425", "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(DRIVER), prop.getProperty(USER), prop.getProperty(PASSWORD));
        } 
        catch (ClassNotFoundException e) 
        {
            LOG.error("Registro de Driver con error: " + e.getMessage());
        } catch (SQLException e) {
            LOG.error("Imposible conectar con la base de datos: " + e.getMessage());
        } catch (IOException e) 
        {
            LOG.error("No se ha podido establecer el archivo de Properties: " + e.getMessage());
        }
    }

    public List<Alumno> mostrarAlumnos()
    {
        List<Alumno> listaAlumnos = null;

        Statement stAlumnos = null;

        ResultSet rstAlumnos = null;

        Alumno alumno = null;

        if (con != null) 
        {
            try 
            {
                stAlumnos = con.createStatement();    
                rstAlumnos = stAlumnos.executeQuery("SELECT expediente, nombre, fecha_nac FROM alumnos");
                
                if (rstAlumnos.next()) 
                {
                    listaAlumnos = new  ArrayList<>(); 
                    do
                    {
                        alumno = new Alumno();

                        alumno.setExpediente(Integer.valueOf(rstAlumnos.getInt(1)));
                        alumno.setNombre(rstAlumnos.getString(2));
                        
                        Date fecha = (rstAlumnos.getDate(3));
                        if (fecha != null) 
                        {
                            alumno.setFecha_nac(rstAlumnos.getDate(3).toLocalDate());
                        }
                        
                        
                        listaAlumnos.add(alumno);

                    } while(rstAlumnos.next());
                        
                }
                else
                {
                    LOG.warn("No se han recuperado datos en la consulta");
                }

                

                LOG.info("Se ha ejecutao  correctamente la sentencia SELECT");
            }   
            catch (SQLException e) 
            {
                LOG.error("Imposible consultar alumnos: " + e.getMessage());
            }
            finally
            {
                try 
                {
                    if (rstAlumnos !=null && stAlumnos != null) {
                        
                        rstAlumnos.close();
                        stAlumnos.close();

                        LOG.info("Se ha cerrado correctamente la conexion");
                    }
                    
                } 
                catch (SQLException ex) 
                {
                    LOG.error("Imposible en el cierre de la conexion");
                }
            }
        }

        return listaAlumnos;

    }



    public boolean  cerrarDb()
    {
        boolean status = false;

        if (con != null) 
        {
            try 
            {
                con.close();
                LOG.info("Base de datos cerrada");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error cerrando la base de datos: " + e.getMessage());
            }    
        }

        return status;
    }
    
    
}
