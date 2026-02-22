

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.mendo.ProductService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de seguridad y validación de datos para ProductService.
 * Verifican que la aplicación rechaza entradas maliciosas o erróneas
 * que podrían comprometer la integridad de los datos.
 */
class SecurityTests {

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService();
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 1 — Precio cero no permitido
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-1: Precio igual a cero lanza excepción")
    void testPriceZeroNotAllowed() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct("Producto Test", 1, 0.0)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("precio"));
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 2 — Nombre solo con espacios
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-2: Nombre compuesto solo de espacios en blanco es rechazado")
    void testBlankNameRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct("   ", 5, 1.0),
                "Un nombre en blanco (solo espacios) no debe ser válido."
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 3 — Nombre con inyección SQL-like
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-3: Caracteres de inyección SQL son rechazados")
    void testSQLInjectionRejected() {
        // El carácter ';' y '%' son señales de inyección SQL
        assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct("'; DROP TABLE products; --", 1, 9.99)
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 4 — Nombre excesivamente largo
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-4: Nombre demasiado largo (>100 chars) es rechazado")
    void testTooLongNameRejected() {
        String longName = "A".repeat(101);
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct(longName, 1, 1.0)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("100"),
                "El mensaje debe indicar el límite de caracteres.");
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 5 — Precio infinito rechazado
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-5: Precio infinito (Double.POSITIVE_INFINITY) es rechazado")
    void testInfinitePriceRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct("ProductoInfinito", 1, Double.POSITIVE_INFINITY)
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 6 — NaN como precio es rechazado
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-6: Precio NaN (Not a Number) es rechazado")
    void testNaNPriceRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct("ProductoNaN", 1, Double.NaN)
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 7 — Nombre con etiquetas HTML/script
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-7: Etiquetas HTML en el nombre son rechazadas (XSS)")
    void testHtmlTagsInNameRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct("<b>Producto</b>", 5, 2.50)
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 8 — Actualizar con índice negativo
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-8: Actualizar con índice negativo lanza excepción")
    void testUpdateNegativeIndexThrows() {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> service.updateProduct(-1, "Nombre", 10, 5.0)
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 9 — Nombre nulo es rechazado
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-9: Nombre null lanza IllegalArgumentException")
    void testNullNameRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.addProduct(null, 1, 1.0)
        );
    }

    // -------------------------------------------------------------------------
    // SEGURIDAD 10 — Producto válido con cantidad = 0 (stock agotado es válido)
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Seg-10: Cantidad 0 es válida (producto sin stock)")
    void testZeroQuantityIsAllowed() {
        // Tener 0 unidades es válido: el producto existe pero está agotado
        assertDoesNotThrow(() -> service.addProduct("ProductoAgotado", 0, 1.99));
        assertEquals(1, service.getProductCount());
    }
}
