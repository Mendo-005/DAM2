package ej2;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PedidoServiceTest {

    @Test
    void crearPedido_guardaSoloSiNoExiste() {

        PedidoRepository repoMock = mock(PedidoRepository.class);

        // thenReturn 
        when(repoMock.existePedido(1))
                .thenReturn(false); // simula que no existe

        PedidoService service = new PedidoService(repoMock);

        service.crearPedido(1, "Pedido A");

        // Verifica que se llamó al método guardar de PedidoRepository
        verify(repoMock).guardar("Pedido A");

        // Verifica que existePedido de la interfaz PedidoRepository 
        //y que se llamó exactamente una vez
        verify(repoMock, times(1)).existePedido(1);
    }

    @Test
    void obtenerTotalConIVA_usaValorSimulado() {

        PedidoRepository repoMock = mock(PedidoRepository.class);

        // Simular que devuelve correctamente el cálculo de precio con IVA
        //Probamos que funciona el método calcularTotal de PedidoRepository
        when(repoMock.calcularTotal()).thenReturn(100.0);

        PedidoService service = new PedidoService(repoMock);

        double total = service.obtenerTotalConIVA();

        assertEquals(121.0, total);
        // Valor incorrecto a propósito
        assertEquals(100.0, total);  // debería ser 121.0
    }

    @Test
    void ejemploThenThrow() {

        PedidoRepository repoMock = mock(PedidoRepository.class);

        // Simular una excepción
        when(repoMock.calcularTotal())
                .thenThrow(new RuntimeException("Error BD"));

        PedidoService service = new PedidoService(repoMock);

        assertThrows(RuntimeException.class,
                () -> service.obtenerTotalConIVA());
    }

    @Test
    void ejemploDoNothingYDoThrow() {

        PedidoRepository repoMock = mock(PedidoRepository.class);

        // doNothing (para métodos void)
        doNothing().when(repoMock).guardar("Pedido B");

        repoMock.guardar("Pedido B");

        verify(repoMock).guardar("Pedido B");

        // doThrow para métodos void
        doThrow(new IllegalArgumentException())
                .when(repoMock).guardar("Pedido C");

        assertThrows(IllegalArgumentException.class,
                () -> repoMock.guardar("Pedido C"));
    }

    @Test
    void ejemploArgumentMatchers() {

        PedidoRepository repoMock = mock(PedidoRepository.class);

        // anyInt() permite aceptar cualquier entero
        when(repoMock.existePedido(anyInt()))
                .thenReturn(true);

        PedidoService service = new PedidoService(repoMock);

        service.crearPedido(99, "Pedido X");

        // Como siempre devuelve true, no debe llamar a guardar
        verify(repoMock, never()).guardar(anyString());
    }
}