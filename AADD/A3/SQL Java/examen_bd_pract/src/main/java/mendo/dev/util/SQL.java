package mendo.dev.util;

public class SQL
{
    protected static final String GET_ALL_INGREDIENTES = "SELECT\n" + //
                "    I.nombre_ingrediente\n" + //
                "FROM\n" + //
                "    pizza P,\n" + //
                "    pizza_ingrediente PI,\n" + //
                "    ingrediente I\n" + //
                "WHERE\n" + //
                "    P.cod_pizza = PI.pizzaId\n" + //
                "    AND PI.ingredienteId = I.cod_ingrediente\n" + //
                "    AND P.nombre_pizza = ?";
    
    
    protected static final String FUN_GET_PRECIO_PIZZA = "{? = CALL getPrecioPizza(?)}";
    
    protected static final String SP_ADD_INGREDIENTE_PIZZA = "{CALL addIngredientePizza(?,?,?)}";
    
    protected static final String ADD_PIZZA = "INSERT INTO pizza(nombre_pizza, precio) values (?,?)"; 
    
    // Ya no pasamos cod_ingrediente, lo genera la BD
    protected static final String ADD_INGREDIENTE = "INSERT INTO ingrediente(nombre_ingrediente) values (?)"; 
    
    // Consulta para buscar el ID real de un ingrediente por nombre (para evitar duplicados)
    protected static final String GET_INGREDIENTE_ID = "SELECT cod_ingrediente FROM ingrediente WHERE nombre_ingrediente = ? LIMIT 1";
    
    // Consulta para la tabla de unión
    protected static final String ADD_INGREDIENTE_PIZZA = "INSERT INTO pizza_ingrediente(pizzaId, ingredienteId, cantidad) VALUES (?,?,?)";

    // Consulta de ejemplo para obtener ingredientes de una pizza
    protected static final String GET_INGREDIENTES = "SELECT nombre_ingrediente FROM ingrediente I";
}
