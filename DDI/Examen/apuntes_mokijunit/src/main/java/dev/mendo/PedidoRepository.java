package dev.mendo;

import java.util.Optional;

// Interfaz que simula la capa de acceso a datos (base de datos, API...).
// En los tests NO se implementa: Mockito la simula automáticamente.
// → Todo lo que declaremos aquí lo podemos mockear en PedidoServiceTest.
public interface PedidoRepository {

    // Devuelve true si ya existe un pedido con ese id, false si no
    boolean existePedido(int id);

    // Guarda un pedido. Es void → en el test usaremos doNothing / doThrow
    void guardar(String pedido);

    // Devuelve el total acumulado de pedidos
    // → En el test lo mockeamos con thenReturn para controlar el valor
    double calcularTotal();

    // Devuelve el nombre del pedido si existe, o Optional.empty() si no
    // → En el test probamos ambos casos: con valor y vacío
    Optional<String> buscarNombrePorId(int id);
}