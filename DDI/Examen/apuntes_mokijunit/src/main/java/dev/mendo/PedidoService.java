package dev.mendo;

import java.util.Optional;

// Clase de lógica de negocio. Depende de PedidoRepository para acceder a datos.
// En los tests NO usamos la implementación real del repositorio:
// Mockito lo sustituye por un objeto simulado que controlamos nosotros.
public class PedidoService {

    // Dependencia inyectada por constructor → fácil de mockear en los tests
    private final PedidoRepository repo;

    public PedidoService(PedidoRepository repo) { this.repo = repo; }

    // Guarda el pedido solo si no existe ya uno con ese id.
    // → En el test verificamos con verify() si guardar fue llamado o no
    public void crearPedido(int id, String pedido) {
        if (!repo.existePedido(id)) repo.guardar(pedido);
    }

    // Calcula el total con IVA usando el total que devuelve el repositorio.
    // → En el test mockeamos calcularTotal() con thenReturn() para controlar el valor
    public double obtenerTotalConIVA() { return repo.calcularTotal() * 1.21; }

    // Devuelve el nombre del pedido si existe, o un mensaje de error si no.
    // → En el test probamos los dos caminos: Optional con valor y Optional vacío
    public String getNombrePedido(int id) {
        Optional<String> nombre = repo.buscarNombrePorId(id);
        return nombre.isPresent() ? nombre.get() : "Pedido no encontrado";
    }
}