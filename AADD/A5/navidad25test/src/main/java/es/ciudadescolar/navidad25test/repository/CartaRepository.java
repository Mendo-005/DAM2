package es.ciudadescolar.navidad25test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ciudadescolar.navidad25test.model.Carta;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long>{

        boolean existsByNinoIdAndContenido(Long id, String contenidoCarta);

}
