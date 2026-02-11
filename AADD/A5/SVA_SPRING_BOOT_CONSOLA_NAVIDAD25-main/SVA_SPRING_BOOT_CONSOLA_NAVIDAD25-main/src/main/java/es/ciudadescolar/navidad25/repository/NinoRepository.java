package es.ciudadescolar.navidad25.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ciudadescolar.navidad25.model.Nino;
import es.ciudadescolar.navidad25.model.Nino.Comportamiento;

@Repository
public interface NinoRepository extends JpaRepository<Nino, Long>
{
    List<Nino> findByNombre(String nombre);

    List<Nino> findByNombreIgnoreCase(String  nombre);

    List<Nino> findByNombreContaining(String  nombre);

    List<Nino> findByComportamiento(Comportamiento  comportamiento);

    List<Nino> findByComportamientoOrderByNombreAsc(Comportamiento  comportamiento);

    List<Nino> findByNombreAndComportamiento(String nombre, Comportamiento comportamiento);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndComportamiento(String nombre, Comportamiento comportamiento);

    Long countByComportamiento(Comportamiento comportamiento);

    @Query("SELECT n FROM Nino n WHERE n.comportamiento = :comportamiento")
    List<Nino> buscarPorComportamiento(@Param("comportamiento") Comportamiento comportamiento);
}
