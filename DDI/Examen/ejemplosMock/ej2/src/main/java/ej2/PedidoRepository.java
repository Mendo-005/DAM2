package ej2;

public interface PedidoRepository {
    boolean existePedido(int id);
    void guardar(String pedido);
    double calcularTotal();
}