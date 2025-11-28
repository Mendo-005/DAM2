package es.ciudadescolar.util;

public class SQL 
{
    protected  static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";
    
    protected static final String RECUPERA_ALUMNOS_POR_EXP = "SELECT expediente, nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";
    
    protected static final String ALTA_DE_UN_ALUMNO = "SELECT ";

    protected static final String DAR_DE_BAJA_ALUMNO = "DELETE*FROM alumnos WHERE expediente = ? ";

}
