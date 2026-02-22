package dev.mendo.Mokito;

import java.util.Optional;

public interface ProductoRepository {
    Optional<String> findNombreById(int id);
}
