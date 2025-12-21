package mendo.dev.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mendo.dev.clases.Comic;

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

public class ComicRowMapper implements RowMapper<Comic>
{

    @Override
    public Comic mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
       Comic comic = new Comic();
       comic.setTitulo(rs.getString("titulo"));
       comic.setNumero(rs.getInt("numero"));
       comic.setPrecio(rs.getDouble("precio"));
       comic.setCod_editorial(rs.getInt("cod_editorial"));
       comic.setFecha_publi(rs.getDate("fecha_publicacion").toLocalDate());
       
       return comic;
    }
    
}
