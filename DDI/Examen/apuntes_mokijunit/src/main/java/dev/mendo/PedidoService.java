package dev.mendo;

import java.util.Optional;

public class PedidoService {

    private final PedidoRepository repo;

    public PedidoService(PedidoRepository repo) { this.repo = repo; }

    public void crearPedido(int id, String pedido) {
        if (!repo.existePedido(id)) repo.guardar(pedido);
    }

    public double obtenerTotalConIVA() { return repo.calcularTotal() * 1.21; }

    public String getNombrePedido(int id) {
        Optional<String> nombre = repo.buscarNombrePorId(id);
        return nombre.isPresent() ? nombre.get() : "Pedido no encontrado";
    }
}