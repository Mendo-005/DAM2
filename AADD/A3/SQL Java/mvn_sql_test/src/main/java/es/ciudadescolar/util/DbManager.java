package es.ciudadescolar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private Connection con = null;

    public DbManager()
    {
        Properties prop = new  Properties();

        try 
        {   
            prop.load(new FileInputStream("conexionBD.properties"));
            Class.forName(prop.getProperty(DRIVER));
            //con = DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425", "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USER), prop.getProperty(PASSWORD));
            LOG.info("Conexion obtenida");
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
                rstAlumnos = stAlumnos.executeQuery(SQL.RECUPERA_ALUMNOS);
                
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

    public Alumno getAlumnoPorExp(int exped, String nombre)
    {
        Alumno al = null;
        PreparedStatement stAlumno = null;
        ResultSet rstAlumno = null;

        try 
        {
            stAlumno = con.prepareStatement(SQL.RECUPERA_ALUMNOS_POR_EXP);
            stAlumno.setInt(1,exped);
            stAlumno.setString(2,nombre);
            rstAlumno = stAlumno.executeQuery();

            if (rstAlumno.next()) 
            {
                al = new Alumno(rstAlumno.getInt(1), rstAlumno.getString(2), rstAlumno.getDate(3));


                LOG.info("Se ha ejecutao  correctamente la sentencia SELECT");
            }
            else
            {
                LOG.warn("No se han recuperado datos en la consulta");
            }
        } 
        catch (SQLException e) 
        {
            LOG.error("Imposible consultar por expediente al alumno: " + e.getMessage());
        }
        finally
            {
                try 
                {
                    if (rstAlumno !=null && stAlumno != null) {
                        
                        rstAlumno.close();
                        stAlumno.close();
                        LOG.info("Se ha cerrado correctamente la conexion");
                    }
                } 
                catch (SQLException ex) 
                {
                    LOG.error("Imposible en el cierre de la conexion");
                }
            }

        return al;
    }
}
