import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.mendo.PedidoRepository;
import dev.mendo.PedidoService;

class PedidoServiceTest {

    // ── Forma 1: anotaciones ──────────────────────────────────
    // @Mock    → crea el objeto simulado de la interfaz
    // @InjectMocks → inyecta el mock en el servicio automáticamente
    @Mock
    private PedidoRepository repoMock;

    @InjectMocks
    private PedidoService service;

    // Obligatorio para inicializar @Mock e @InjectMocks
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }


    // ── Forma 2: mock() manual ────────────────────────────────
    // Sin anotaciones, todo dentro del test. Más simple para exámenes.
    @Test
    void testFormaManual_sinAnotaciones() {
        PedidoRepository mockManual = mock(PedidoRepository.class);
        when(mockManual.calcularTotal()).thenReturn(200.0);

        PedidoService serviceManual = new PedidoService(mockManual);

        assertEquals(242.0, serviceManual.obtenerTotalConIVA());
        verify(mockManual, times(1)).calcularTotal();
    }


    // ── when().thenReturn() ───────────────────────────────────
    // "cuando se llame a X con ese argumento, devuelve Y"
    @Test
    void testTotalConIVA_thenReturn() {
        when(repoMock.calcularTotal()).thenReturn(100.0);

        double total = service.obtenerTotalConIVA();

        assertEquals(121.0, total);
    }

    @Test
    void testGetNombrePedido_thenReturn() {
        when(repoMock.buscarNombrePorId(1)).thenReturn(Optional.of("Manzanas"));

        assertEquals("Manzanas", service.getNombrePedido(1));
    }

    @Test
    void testGetNombrePedido_noEncontrado() {
        when(repoMock.buscarNombrePorId(99)).thenReturn(Optional.empty());

        assertEquals("Pedido no encontrado", service.getNombrePedido(99));
    }


    // ── when().thenThrow() ────────────────────────────────────
    // El mock lanza una excepción cuando se llama al método (con return)
    @Test
    void testTotalConIVA_thenThrow() {
        when(repoMock.calcularTotal())
            .thenThrow(new RuntimeException("Error en BD"));

        assertThrows(RuntimeException.class,
            () -> service.obtenerTotalConIVA());
    }


    // ── verify() ─────────────────────────────────────────────
    // Comprueba que el mock fue llamado (y cuántas veces)
    @Test
    void testCrearPedido_verify() {
        when(repoMock.existePedido(1)).thenReturn(false);

        service.crearPedido(1, "Pedido A");

        verify(repoMock).guardar("Pedido A");            // se llamó al menos 1 vez
        verify(repoMock, times(1)).existePedido(1);      // exactamente 1 vez
    }


    // ── verify(mock, never()) ─────────────────────────────────
    // Comprueba que el método NUNCA fue llamado
    @Test
    void testCrearPedido_noGuardaSiExiste() {
        when(repoMock.existePedido(1)).thenReturn(true); // ya existe

        service.crearPedido(1, "Pedido A");

        verify(repoMock, never()).guardar("Pedido A");
    }


    // ── times / atLeast / atMost ──────────────────────────────
    @Test
    void testVerifyVariantes() {
        when(repoMock.calcularTotal()).thenReturn(50.0);
        service.obtenerTotalConIVA();
        service.obtenerTotalConIVA();

        verify(repoMock, times(2)).calcularTotal();    // exactamente 2
        verify(repoMock, atLeast(1)).calcularTotal();  // mínimo 1
        verify(repoMock, atMost(3)).calcularTotal();   // máximo 3
    }


    // ── doNothing() ───────────────────────────────────────────
    // Para métodos void: el mock no hace nada
    // (es el comportamiento por defecto, pero se puede declarar explícito)
    @Test
    void testDoNothing_metodoVoid() {
        doNothing().when(repoMock).guardar("Pedido B");

        repoMock.guardar("Pedido B");

        verify(repoMock).guardar("Pedido B");
    }


    // ── doThrow() ─────────────────────────────────────────────
    // Para métodos void que deben lanzar excepción
    // Con void NO puedes usar when().thenThrow() → usa doThrow()
    @Test
    void testDoThrow_metodoVoid() {
        doThrow(new IllegalArgumentException("Pedido inválido"))
            .when(repoMock).guardar("INVALIDO");

        assertThrows(IllegalArgumentException.class,
            () -> repoMock.guardar("INVALIDO"));
    }


    // ── Argument Matchers ─────────────────────────────────────
    // anyInt(), anyString(), anyLong(), any(Clase.class)
    // Útiles cuando no importa el valor exacto del argumento
    @Test
    void testAnyInt_cualquierEntero() {
        when(repoMock.existePedido(anyInt())).thenReturn(true);

        service.crearPedido(99, "Pedido X");

        verify(repoMock, never()).guardar(anyString());
    }

    @Test
    void testAnyString_enVerify() {
        when(repoMock.existePedido(1)).thenReturn(false);

        service.crearPedido(1, "Lo que sea");

        verify(repoMock).guardar(anyString());
    }
}