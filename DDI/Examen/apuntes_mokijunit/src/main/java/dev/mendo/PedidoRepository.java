package dev.mendo;

import java.util.Optional;

public interface PedidoRepository {
    boolean existePedido(int id);
    void guardar(String pedido);
    double calcularTotal();
    Optional<String> buscarNombrePorId(int id);
}