package es.ciudadescolar.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.ciudadescolar.model.Actor;


/**
 * RowMapper es una interfaz clave del framework Spring JDBC 
 * (válida tanto en Spring clásico como en Spring Boot) que 
 * permite convertir una fila de un ResultSet en un objeto Java. 
 * Spring JDBC la usa para mapear consultas SQL a objetos de dominio 
 * sin necesidad de escribir código repetitivo.
 * 
 * Su propósito es tomar cada fila del ResultSet de JDBC y 
 * transformarla en un objeto Java de tipo T
 * 
 * Spring usa internamente el RowMapper cuando llamas a métodos como:
 *  jdbcTemplate.query(...)
 *  jdbcTemplate.queryForObject(...)
 */

public class ActorRowMapper implements RowMapper<Actor>
{
    @Override
    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Actor actor = new Actor();
        actor.setIdActor(rs.getInt("actor_id"));
        actor.setNombre(rs.getString("first_name"));
        actor.setApellido(rs.getString("last_name"));
        
        return actor;
    }

}