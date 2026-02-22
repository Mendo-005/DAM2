package dev.mendo.Mokito;

import java.util.Optional;

public class ProductoService {
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public String getNombre(int id) {
        Optional<String> nombre = repo.findNombreById(id);
        return nombre.isPresent() ? nombre.get() : "Producto no encontrado";
    }
}