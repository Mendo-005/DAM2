import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.mendo.Calculadora;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraTest {

    private Calculadora calc;

    // Se ejecuta ANTES de cada @Test → inicializar objetos
    @BeforeEach
    void setUp() { calc = new Calculadora(); }

    // Se ejecuta DESPUÉS de cada @Test → limpiar recursos
    @AfterEach
    void tearDown() { /* cerrar conexiones, ficheros, etc. */ }

    // Se ejecuta UNA vez antes de todos los tests (debe ser static)
    @BeforeAll
    static void inicioBateria() { System.out.println("Empezando tests..."); }

    // Se ejecuta UNA vez al final de todos los tests (debe ser static)
    @AfterAll
    static void finBateria() { System.out.println("Tests finalizados."); }


    // assertEquals → comprueba que esperado == real
    @Test
    void testSumar_assertEquals() {
        assertEquals(5,  calc.sumar(2, 3));
        assertEquals(-1, calc.sumar(-2, 1));
        assertEquals(0,  calc.sumar(0, 0));
    }

    @Test
    void testRestar_assertEquals() {
        assertEquals(1,  calc.restar(3, 2));
        assertEquals(-3, calc.restar(-2, 1));
    }

    @Test
    void testMultiplicar_assertEquals() {
        assertEquals(6, calc.multiplicar(2, 3));
        assertEquals(0, calc.multiplicar(0, 5));
    }

    @Test
    void testDividir_assertEquals() {
        assertEquals(2.0, calc.dividir(6, 3));
    }


    // assertNotEquals → comprueba que los valores son DISTINTOS
    @Test
    void testSumar_assertNotEquals() {
        assertNotEquals(99, calc.sumar(2, 3)); // 5 ≠ 99 → pasa
    }


    // assertTrue / assertFalse → comprueban una condición boolean
    @Test
    void testEsPrimo_assertTrue() {
        assertTrue(calc.esPrimo(7));
        assertTrue(calc.esPrimo(11));
    }

    @Test
    void testEsPrimo_assertFalse() {
        assertFalse(calc.esPrimo(4));
        assertFalse(calc.esPrimo(1));
    }


    // assertThrows → comprueba que el método lanza esa excepción
    // NUNCA hagas el if/throw tú en el test. Lo lanza LA CLASE.
    @Test
    void testDividir_assertThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> calc.dividir(5, 0));
    }

    @Test
    void testFactorial_assertThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> calc.factorial(-1));
    }


    // assertDoesNotThrow → comprueba que NO se lanza ninguna excepción
    @Test
    void testDividir_assertDoesNotThrow() {
        assertDoesNotThrow(() -> calc.dividir(10, 2));
    }


    // assertNull / assertNotNull → comprueban si un objeto es null o no
    @Test
    void testAssertNull() {
        String valor = null;
        assertNull(valor);
    }

    @Test
    void testAssertNotNull() {
        String valor = "hola";
        assertNotNull(valor);
    }


    // Ejemplo del patrón AAA completo
    @Test
    void testFactorial_AAA() {
        // Arrange
        int entrada  = 5;
        int esperado = 120;

        // Act
        int resultado = calc.factorial(entrada);

        // Assert
        assertEquals(esperado, resultado);
        assertEquals(1, calc.factorial(0)); // caso borde: 0! = 1
    }
}