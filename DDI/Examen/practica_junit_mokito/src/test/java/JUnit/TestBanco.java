package JUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dev.mendo.JUnit.Banco;

/**
 * @Test →  marca un método como prueba
            assertEquals(esperado, real) → comprueba que dos valores son iguales
            assertTrue/assertFalse(condición) → comprueba que algo es verdadero/falso
            assertThrows(Excepcion.class, () -> método()) → comprueba que se lanza una
 */

public class TestBanco 
{
    /**
     * Verifica el correcto funcionamiento de la clase
     * Patrón AAA (Arrange - Act - Assert)
     */
    @Test
    public void ingresarTest() {
        // Arrange
        Banco banco = new Banco(200);
        // Act
        banco.ingresar(100);
        // Assert
        assertEquals(300, banco.getSaldo());
    } 
    
    /**
     * Verifica que no se pueda sacar más dinero del que hay
     * @throws IllegalArgumentException
     */
    @Test
    public void retirarTest() {
        // Arrange
        Banco banco = new Banco(200);

        // Act + Assert
        // Le decimos a JUnit: "esperamos que este código lance IllegalArgumentException"
        assertThrows(IllegalArgumentException.class, () -> banco.retirar(300));
        // Si el método NO lanza la excepción → el test FALLA (detecta el bug)
        // Si la lanza → el test PASA (comportamiento correcto)
    }

    /**
     * Verifica que no se pueda ingresar sumas negativas o nulas
     * @throws IllegalArgumentException
     */
    @Test
    public void ingresoNegativoTest() {
        // Arrange
        Banco banco = new Banco(200);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> banco.ingresar(-300));
    }

}
