package mendo.dev.util;

public class SQL 
{
    protected static final String FUN_GET_PRECIO = "{? = CALL getPrecioComic(?)} " ;
    
    protected static final String SP_ADD_ASIGNAR_CREADOR = "{CALL asignarCreadorComic(?,?,?)} " ;
    
    protected static final String ADD_COMIC = "INSERT INTO Comic (titulo, numero, precio, cod_editorial, fecha_publicacion) VALUES(?, ?, ?, ?, ?)";
    
    protected static final String ADD_AUTOR = "INSERT INTO Creador (nombre) VALUES (?)";
    
    protected static final String ADD_AUTOR_COMIC = "INSERT INTO Comic_Creador (cod_comic, cod_creador, rol) VALUES (?, ?, ?) " ;
    
    protected static final String GET_AUTORES = "SELECT nombre FROM Creador" ;

    protected static final String GET_CREADORES_COMIC = "SELECT C.nombre " + 
                                                        "FROM Creador C, Comic_Creador CC, Comic CO " + 
                                                        "WHERE C.cod_creador = CC.cod_creador "+
                                                        "    AND CC.cod_comic = CO.cod_comic"+   
                                                        "    AND CO.titulo = ?";    
}
