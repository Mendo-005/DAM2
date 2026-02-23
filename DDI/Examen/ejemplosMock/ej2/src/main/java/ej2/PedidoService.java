package ej2;

public class PedidoService {

    private PedidoRepository repo;

    public PedidoService(PedidoRepository repo) {
        this.repo = repo;
    }

    public void crearPedido(int id, String pedido) {
        if (!repo.existePedido(id)) {
            repo.guardar(pedido);
        }
    }

    public double obtenerTotalConIVA() {
        return repo.calcularTotal() * 1.21;
    }
}