

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.mendo.Product;
import dev.mendo.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio ProductService.
 * Se usa @Spy de Mockito para verificar interacciones internas
 * sin alterar el comportamiento real de la clase.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    /** Spy permite verificar llamadas a métodos sin mockear la implementación */
    @Spy
    private ProductService productService;

    // -------------------------------------------------------------------------
    // TEST 1 — Añadir un producto válido
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 1: Añadir producto válido incrementa el stock")
    void testAddValidProduct() {
        // Arrange & Act
        productService.addProduct("Manzana", 100, 0.50);

        // Assert
        assertEquals(1, productService.getProductCount(),
                "Debe haber exactamente 1 producto en el stock.");

        Product p = productService.getProducts().get(0);
        assertEquals("Manzana", p.getName());
        assertEquals(100, p.getQuantity());
        assertEquals(0.50, p.getPrice(), 0.001);
    }

    // -------------------------------------------------------------------------
    // TEST 2 — Nombre vacío lanza excepción
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 2: Nombre vacío lanza IllegalArgumentException")
    void testAddProductWithEmptyNameThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct("", 10, 5.0),
                "Un nombre vacío debe lanzar IllegalArgumentException."
        );
        assertTrue(ex.getMessage().toLowerCase().contains("nombre"),
                "El mensaje debe mencionar el nombre.");
    }

    // -------------------------------------------------------------------------
    // TEST 3 — Precio negativo lanza excepción
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 3: Precio negativo lanza IllegalArgumentException")
    void testAddProductWithNegativePriceThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct("Pera", 10, -1.0),
                "Un precio negativo debe lanzar IllegalArgumentException."
        );
    }

    // -------------------------------------------------------------------------
    // TEST 4 — Cantidad negativa lanza excepción
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 4: Cantidad negativa lanza IllegalArgumentException")
    void testAddProductWithNegativeQuantityThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct("Naranja", -5, 1.20),
                "Una cantidad negativa debe lanzar IllegalArgumentException."
        );
    }

    // -------------------------------------------------------------------------
    // TEST 5 — Eliminar producto existente
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 5: Eliminar producto existente reduce el stock")
    void testRemoveProductReducesCount() {
        productService.addProduct("Plátano", 20, 0.80);
        productService.addProduct("Kiwi", 15, 1.10);
        assertEquals(2, productService.getProductCount());

        productService.removeProduct(0);

        assertEquals(1, productService.getProductCount());
        assertEquals("Kiwi", productService.getProducts().get(0).getName());
    }

    // -------------------------------------------------------------------------
    // TEST 6 — Eliminar índice inválido lanza excepción (con Mockito spy)
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 6: Eliminar índice inválido lanza IndexOutOfBoundsException (Mockito spy)")
    void testRemoveInvalidIndexThrowsException() {
        // El spy invoca el método real pero podemos verificar la llamada
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> productService.removeProduct(99)
        );
        // Verificamos con Mockito que se llamó exactamente una vez con el índice 99
        verify(productService, times(1)).removeProduct(99);
    }

    // -------------------------------------------------------------------------
    // TEST 7 — Actualizar producto correctamente
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 7: Actualizar producto existente modifica sus datos")
    void testUpdateProductChangesValues() {
        productService.addProduct("Uva", 30, 2.00);
        productService.updateProduct(0, "Uva Verde", 50, 2.50);

        Product updated = productService.getProducts().get(0);
        assertEquals("Uva Verde", updated.getName());
        assertEquals(50, updated.getQuantity());
        assertEquals(2.50, updated.getPrice(), 0.001);
    }

    // -------------------------------------------------------------------------
    // TEST 8 — Buscar producto por nombre
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 8: Buscar producto por nombre devuelve el producto correcto")
    void testFindByNameReturnsCorrectProduct() {
        productService.addProduct("Sandía", 5, 3.50);
        productService.addProduct("Melón", 8, 2.90);

        Product found = productService.findByName("sandía"); // búsqueda insensible a mayúsculas
        assertNotNull(found, "Debe encontrar el producto 'Sandía'.");
        assertEquals("Sandía", found.getName());

        // Verificamos con Mockito que el método findByName fue invocado
        verify(productService, times(1)).findByName("sandía");
    }

    // -------------------------------------------------------------------------
    // TEST 9 — Nombre con caracteres peligrosos lanza excepción
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 9: Nombre con caracteres peligrosos lanza IllegalArgumentException (seguridad)")
    void testAddProductWithDangerousNameThrowsException() {
        // Intento de inyección de código HTML/script
        assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct("<script>alert('xss')</script>", 1, 1.0),
                "Un nombre con caracteres HTML debe lanzar IllegalArgumentException."
        );
    }

    // -------------------------------------------------------------------------
    // TEST 10 — La lista devuelta es inmutable
    // -------------------------------------------------------------------------
    @Test
    @DisplayName("Test 10: getProducts() devuelve una lista no modificable")
    void testGetProductsReturnsUnmodifiableList() {
        productService.addProduct("Cereza", 100, 4.00);
        List<Product> products = productService.getProducts();

        assertThrows(
                UnsupportedOperationException.class,
                () -> products.add(new Product("Hack", 1, 1.0)),
                "La lista devuelta debe ser inmutable."
        );
    }
}
