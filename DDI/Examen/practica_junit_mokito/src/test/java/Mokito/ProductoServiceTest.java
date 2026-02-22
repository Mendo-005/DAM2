package Mokito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.mendo.Mokito.ProductoRepository;
import dev.mendo.Mokito.ProductoService;

/**
 *  @Mock → crea un objeto simulado
    @InjectMocks → inyecta el mock en la clase que pruebas
    - when(...).thenReturn(...) → defines qué devuelve el mock
 */
public class ProductoServiceTest {

    @Mock
    private ProductoRepository repo; // Simulamos el repositorio (no existe de verdad)

    @InjectMocks
    private ProductoService productoService; // Inyecta el mock anterior aquí dentro

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada test
    }

    @Test
    public void getNombreEncontradoTest() {
        // Arrange
        // Le decimos al mock: "cuando te pidan el id 1, devuelve Manzana"
        when(repo.findNombreById(1)).thenReturn(Optional.of("Manzana"));

        // Act
        String resultado = productoService.getNombre(1);

        // Assert
        assertEquals("Manzana", resultado);
    }

    @Test
    public void getNombreNoEncontradoTest() {
        // Arrange
        // Le decimos al mock: "cuando te pidan el id 99, devuelve vacío"
        when(repo.findNombreById(99)).thenReturn(Optional.empty());

        // Act
        String resultado = productoService.getNombre(99);

        // Assert
        assertEquals("Producto no encontrado", resultado);
    }
}

//## Explicación paso a paso

//**¿Por qué usamos `@Mock` en el repositorio?**
//Porque en un test no queremos conectarnos a una base de datos real. El mock es un "doble de mentira" que se comporta como queremos nosotros.

//**¿Qué hace `when(...).thenReturn(...)`?**
//Es la instrucción clave de Mockito. Traducido al español sería:
//> *"Cuando alguien llame a `findNombreById(1)`, entonces devuelve `"Manzana"`"*

//**¿Por qué `@InjectMocks` en el servicio?**
//Porque `ProductoService` necesita un repositorio para funcionar. Mockito se lo inyecta automáticamente usando el `@Mock` que hemos definido arriba.

//@Mock          → la dependencia que simulamos (repositorio, BBDD...)
//@InjectMocks   → la clase que REALMENTE queremos probar (servicio)
//when().thenReturn() → programamos el comportamiento del mock
//assertEquals() → comprobamos que el servicio reacciona bien