package es.ciudadescolar.util;

public class SQL 
{
    protected static final String RECUPERA_ALUMNOS ="SELECT expediente, nombre, fecha_nac FROM alumnos";

    protected static final String RECUPERA_ALUMNO_EXP = "SELECT expediente, nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";

    protected static final String ALTA_NUEVO_ALUMNO = "INSERT INTO alumnos(expediente, nombre, fecha_nac) VALUES (?,?,?)";

    protected static final String CAMBIO_NOMBRE_ALUMNO = "UPDATE alumnos SET nombre =? WHERE expediente =?";

    protected static final String BAJA_ALUMNO = "DELETE FROM alumnos WHERE expediente =?";

    // SP con un parametro de entrada (int expediente)
    protected static final String INVOCACION_SP_INFO_ALUMNO = "{CALL sp_getalumno(?)}";
    
    // SP con un parametro de salida (int cuenta)
    protected static final String INVOCACION_SP_NUM_ALUMNOS = "{CALL getNumAlumnos(?)}";
    
    // FUN con parametro de entrada (int expediente) y salida (int nota)
    protected static final String INVOCACION_FUN_NOTA_ALUMNOS = "{? = CALL fun_getnotaalumno(?)}";

}

