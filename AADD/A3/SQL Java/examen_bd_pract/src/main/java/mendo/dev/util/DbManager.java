package mendo.dev.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mendo.dev.clases.IngredienteReceta;

/*
Desarrolla un programa en JAVA con maven que interactue con la BD relacional Pizzas suministrada y:
a) permita volcar a un LOG los ingredientes de una determinada pizza (parámetro - nombre de la pizza)
b) recupere el precio de una pizza invocando la función existente "getPrecioPizza": ejemplo select nombre_pizza AS 'nonmbre pizza', getPrecioPizza(cod_pizza) AS 'precio pizza' from pizza;
c) permita añadir un ingrediente y cantidad a una determinada pizza invocando procedimiento existente "addIngredientePizza": ejemplo CALL addIngredientePizza('Pollo','Margarita',175); Nota: ingrediente y pizza deben existir en la BD.
d) Dar de alta de forma transaccional la siguiente pizza y sus ingredientes:
    pizza 3 "Melanzana", precio 16€, ingredientes: Mozzarella 425g, Tomate 245g, Berenjena 600g, Aceite balsámico 90g
    
    Ojo: hay ingredientes que habrá que dar de alta y otros no porque ya existen en la tabla "ingrediente".

*/

public class DbManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);
    private static String DRIVER = "driver";
    private static String URL = "url";
    private static String USER = "user";
    private static String PWD = "password";

    private static Connection con = null;

    public DbManager()
    {
        Properties prop = new Properties();
        
        try 
        {
            prop.load(new FileInputStream("conexionBD.properties"));
            Class.forName(prop.getProperty(DRIVER));

            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USER), prop.getProperty(PWD));
            LOG.info("Conexion establecida con la base de datos");
        }
        catch (IOException e) 
        {
            LOG.error("Error en la conexion con la base de datos: " + e.getMessage());
        } 
        catch (ClassNotFoundException e) 
        {
            LOG.error("Error en la conexion con la base de datos: " + e.getMessage());
        } 
        catch (SQLException e) 
        {
            LOG.error("Error en la conexion con la base de datos: " + e.getMessage());
        }
    }

    public List<String> getIngredientesDePizza(String nomPizza)
    {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<String> listaIngredientes = null;

        if (con != null)
        {
            try
            {
                pst = con.prepareStatement(SQL.GET_ALL_INGREDIENTES);
                pst.setString(1, nomPizza);

                rst = pst.executeQuery();

                if (rst.next()) 
                {
                    listaIngredientes = new ArrayList<>();
                    do
                    {
                        String ingrediente = rst.getString(1);
                        listaIngredientes.add(ingrediente);    
                    } while (rst.next());
                }
            } 
            catch (SQLException e)
            {
                LOG.error("Error en la ejecucion de getIngredientesDePizza(): " + e.getMessage());
            }
            finally
            {
                if (rst != null && pst != null) 
                {
                    try 
                    {
                        rst.close();
                        pst.close();
                        LOG.debug("Recursos liberados correctamente");
                    } 
                    catch (SQLException e) 
                    {
                        LOG.error("Error liberando los recursos: " + e.getMessage());
                    }  
                }
            }
        }
        
        return listaIngredientes;
    }
        
    public Integer getPrecioPizza(Integer codePiza)
    {
        Integer precioPizza = null;
        CallableStatement cs = null;

        if (con != null) 
        {
            try 
            {
                cs = con.prepareCall(SQL.FUN_GET_PRECIO_PIZZA);
                cs.setInt(2, codePiza);
                
                cs.registerOutParameter(1, Types.INTEGER);
                cs.execute();
                LOG.debug("Funcion ejecutada correctamente");
                precioPizza = Integer.valueOf(cs.getInt(1));

            } 
            catch (SQLException e) 
            {
                LOG.error("Error en la ejecucion de getPrecioPizza(): " + e.getMessage());
            }
            finally
            {
                if (cs != null) 
                {
                    try 
                    {
                        cs.close();
                        LOG.debug("Recursos liberados correctamente");
                    } 
                    catch (SQLException e) 
                    {
                        LOG.error("Error liberando los recursos: " + e.getMessage());
                    }  
                }
            }
        }
        return precioPizza;
    }

    /*
    public boolean addIngredientePizza(String ingredientes, String pizza, Integer cantidad)
    {
        boolean status = false;
        CallableStatement cs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        Set<String> listaPizzaIngredientesEnBd = null;

        if (con != null) 
        {
            try 
            {
                pst =  con.prepareStatement(SQL.GET_INGREDIENTES_PIZZAS);
                rst = pst.executeQuery();
                listaPizzaIngredientesEnBd = new HashSet<>();
                if (rst.next()) 
                {
                    do
                    {
                        String pizzaOIngrediente = rst.getString(1);
                        listaPizzaIngredientesEnBd.add(pizzaOIngrediente);
                        LOG.debug("Ingrediente o Pizza incluido al SET: " + pizzaOIngrediente);
                    }
                    while(rst.next());
                    LOG.debug("Ingredientes y pizzas incluidos al SET correctamente");

                    if (listaPizzaIngredientesEnBd.contains(ingredientes) && listaPizzaIngredientesEnBd.contains(pizza)) 
                    {
                        cs = con.prepareCall(SQL.SP_ADD_INGREDIENTE_PIZZA);
                        cs.setString(1, ingredientes);
                        cs.setString(2, pizza);
                        cs.setInt(3, cantidad);
                        
                        cs.executeUpdate(); // O cs.executeUpdate()
                        cs.clearParameters();
                        LOG.debug(cantidad + ingredientes + "icluidos a la Pizza " + pizza);
                        status = true;
                    }
                }
            } 
            catch (SQLException e) 
            {
                LOG.error("Error en la llamada de addIngredientePizza(): " + e.getMessage());
            }
            finally
            {
                if (cs != null && rst != null && pst != null) 
                {
                    try 
                    {
                        cs.close();
                        pst.close();
                        rst.close();
                        LOG.debug("Recursos liberados correctamente");
                    } 
                    catch (SQLException e) 
                    {
                        LOG.error("Error liberando los recursos: " + e.getMessage());
                    }  
                }
            }
        }

        return status;
    }*/

    public boolean addIngredientePizza(String ingrediente, String pizza, Integer cantidad) 
    { 
        boolean status = false;
        // PreparedStatement y ResultSet ya no son necesarios
        CallableStatement cs = null; 

        if (con != null) 
        {
            try 
            {
                cs = con.prepareCall(SQL.SP_ADD_INGREDIENTE_PIZZA); 

                cs.setString(1, ingrediente);
                cs.setString(2, pizza);      
                cs.setInt(3, cantidad);      

                cs.executeUpdate();
                cs.clearParameters();

                LOG.debug(cantidad + " de " + ingrediente + " incluidos a la Pizza " + pizza);
                status = true;

            } 
            catch (SQLException e) 
            {
                LOG.error("Error en la llamada de addIngredientePizza(): " + e.getMessage());
            } 
            finally 
            {
                if (cs != null) 
                    {
                    try 
                    {
                        cs.close();
                        LOG.debug("Recursos liberados correctamente");
                    } 
                    catch (SQLException e) 
                    {
                        LOG.error("Error liberando los recursos: " + e.getMessage());
                    } 
                }
            }
        }
        return status;
    }

public boolean addPizzasIngredientes(String nomPizza, Double precio, List<IngredienteReceta> receta)
    {
        boolean status = false;
        
        // El parámetro codPizza se ELIMINA de la firma para reflejar la realidad del código
        // public boolean addPizzasIngredientes(String nomPizza, Double precio, List<IngredienteReceta> receta) 
        
        PreparedStatement pstPizza = null;
        PreparedStatement pstIngred = null;
        PreparedStatement pstPizzaIngred = null;
        PreparedStatement pstGetIngredId = null; 
        
        Integer codPizza = null; // Variable para almacenar el ID generado por la BD

        if (con != null) 
        {
            try 
            {
                // --- AJUSTE DE SENTENCIAS Y PREPARACIÓN ---
                pstPizza = con.prepareStatement(SQL.ADD_PIZZA, Statement.RETURN_GENERATED_KEYS); // Importante para las claves que genera la bd directamente
                pstIngred = con.prepareStatement(SQL.ADD_INGREDIENTE, Statement.RETURN_GENERATED_KEYS); 
                pstPizzaIngred = con.prepareStatement(SQL.ADD_INGREDIENTE_PIZZA);
                pstGetIngredId = con.prepareStatement(SQL.GET_INGREDIENTE_ID);
                
                con.setAutoCommit(false); // Transaccional
                
                // 1. INSERCIÓN DE LA PIZZA Y OBTENCIÓN DEL ID REAL
                pstPizza.setString(1, nomPizza);
                pstPizza.setDouble(2, precio);
                pstPizza.executeUpdate();
                
                // Obtener ID generado por la BD
                try (ResultSet rsKeys = pstPizza.getGeneratedKeys()) 
                {
                    if (rsKeys.next()) 
                    {
                        codPizza = rsKeys.getInt(1); // ID REAL de la BD
                    }
                }
                
                // 2. PROCESAR CADA INGREDIENTE EN LA RECETA
                for (IngredienteReceta item : receta) 
                {
                    String nomIngred = item.getNombre_ingrediente();
                    Double cantidad = item.getCantidad();
                    Integer codIngredActual = null;

                    // --- 2.1 Buscar si el ingrediente existe (Eficiente) ---
                    pstGetIngredId.setString(1, nomIngred);
                    try (ResultSet rs = pstGetIngredId.executeQuery()) 
                    {
                        if (rs.next()) 
                        {
                            codIngredActual = rs.getInt(1); // El ingrediente ya existe, ID obtenido
                        }
                    }

                    // --- 2.2 Insertar si NO existe y obtener ID ---
                    if (codIngredActual == null) 
                    {
                        pstIngred.setString(1, nomIngred);
                        
                        if (pstIngred.executeUpdate() == 0) 
                        {
                            throw new SQLException("Fallo al insertar ingrediente: " + nomIngred);
                        }
                        
                        // Obtener ID generado
                        try (ResultSet rsGenerated = pstIngred.getGeneratedKeys()) 
                        {
                            if (rsGenerated.next()) 
                            {
                                codIngredActual = rsGenerated.getInt(1); 
                            }
                        }
                        pstIngred.clearParameters();
                    }

                    // 3. INSERTAR RELACIÓN PIZZA_INGREDIENTE
                    pstPizzaIngred.setInt(1, codPizza);         
                    pstPizzaIngred.setInt(2, codIngredActual);  
                    pstPizzaIngred.setDouble(3, cantidad);      
                    
                    pstPizzaIngred.executeUpdate();
                    pstPizzaIngred.clearParameters();
                }
                
                con.commit();
                status = true;
                LOG.debug("Se han añadido la pizza con sus ingredientes");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error al ejecutar addPizzas: " + e.getMessage());
                try 
                {
                    con.rollback();
                    LOG.debug("Rollback realizado");
                } 
                catch (SQLException e1) 
                {
                    LOG.error("Error en el rollback: " + e.getMessage());
                }
            }
            finally 
            {
                // Cierre de Recursos
                try 
                {
                    if (pstPizza != null) pstPizza.close();
                    if (pstIngred != null) pstIngred.close();
                    if (pstPizzaIngred != null) pstPizzaIngred.close();
                    if (pstGetIngredId != null) pstGetIngredId.close();
                    if (con != null) con.setAutoCommit(true);
                    LOG.debug("Recursos liberados correctamente");
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
