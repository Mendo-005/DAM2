package mendo.dev;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mendo.dev.clases.IngredienteReceta;
import mendo.dev.util.DbManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        DbManager manager = new DbManager();

        if (manager != null) 
        {
            // 1)
            List<String> listaIngredientes = manager.getIngredientesDePizza("Barbacoa");
            for (String ing : listaIngredientes) 
            {
                LOG.info(ing);
            }

            // 2)
            Integer codPizza = 2;
            Integer precioPizza = manager.getPrecioPizza(codPizza);
            LOG.info("Precio de la pizza: " + precioPizza);

            // 3)
            String pizza = "4 Quesos";
            String ingrediente = "Pollo";
            Integer cantidad = 100;
            try {
                manager.addIngredientePizza(ingrediente, pizza, cantidad);
                List<String> nuevosIngredientes = manager.getIngredientesDePizza("4 Quesos");
                
                LOG.info("Ingredientes incluidos correctamente");
                for (String ing : nuevosIngredientes) 
                {
                    LOG.info(ing);
                }
            } 
            catch (Exception e) {
                LOG.error("Error en la ejecucion del proceso de inclusion de ingredientes (Main): " + e.getMessage());
            }

            // 4) Dar de alta la pizza Melanzana
            String nomPizzaNueva = "Melanzana";
            Double precio = 16.0;

            List<IngredienteReceta> listaIngredienteRecetas = new ArrayList<>();
            // NOTA: La clase IngredienteReceta debe tener un constructor de solo dos parámetros (nombre, cantidad)
            listaIngredienteRecetas.add(new IngredienteReceta("Mozzarella", 425.0));
            listaIngredienteRecetas.add(new IngredienteReceta("Tomate", 245.0));
            listaIngredienteRecetas.add(new IngredienteReceta("Berenjena", 600.0));
            listaIngredienteRecetas.add(new IngredienteReceta("Aceite balsámico", 90.0));

            // LLAMADA CORREGIDA (Ya no pasamos codPizzaNueva)
            manager.addPizzasIngredientes(nomPizzaNueva, precio, listaIngredienteRecetas);
             List<String> nuevosIngredientes = manager.getIngredientesDePizza("Melanzana");
            for (String ing : nuevosIngredientes) 
            {
                LOG.info(ing);
            }
        }
    }

}