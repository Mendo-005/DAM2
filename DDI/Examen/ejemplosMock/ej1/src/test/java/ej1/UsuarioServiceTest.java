package ej1;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UsuarioServiceTest {

    @Test
    void obtenerNombre_devuelveNombreMockeado() {

        // 1. Crear un objeto simulado, un mock, del repositorio
        UsuarioRepository repoMock = mock(UsuarioRepository.class);

        // 2. Definir el comportamiento del mock
        // Cuando se llame a buscarNombrePorId(1),
        // entonces devolverá "Ana"
        when(repoMock.buscarNombrePorId(1)).thenReturn("Ana");

        // 3. Inyectar el mock en la clase que queremos probar
        UsuarioService service = new UsuarioService(repoMock);

        // 4. Ejecutar el método a probar
        String resultado = service.obtenerNombre(1);

        // 5. Verificar que el resultado es el esperado
        assertEquals("Ana", resultado);

        // 6. Verificar que el método del repositorio fue llamado una vez
        verify(repoMock).buscarNombrePorId(1);
    }
}