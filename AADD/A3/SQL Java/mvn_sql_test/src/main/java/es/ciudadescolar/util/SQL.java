package es.ciudadescolar.util;

public class SQL 
{
<<<<<<< HEAD
=======
<<<<<<< HEAD
    protected static final String RECUPERA_ALUMNOS ="SELECT expediente, nombre, fecha_nac FROM alumnos";

    protected static final String RECUPERA_ALUMNO_EXP = "SELECT expediente, nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";

    protected static final String ALTA_NUEVO_ALUMNO = "INSERT INTO alumnos(expediente, nombre, fecha_nac) VALUES (?,?,?)";

    protected static final String CAMBIO_NOMBRE_ALUMNO = "UPDATE alumnos SET nombre =? WHERE expediente =?";

     protected static final String BAJA_ALUMNO = "DELETE FROM alumnos WHERE expediente =?";

}
=======
>>>>>>> d29dd218349addfca7bebd047c29e260fb123868
    protected  static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";
    
    protected static final String RECUPERA_ALUMNOS_POR_EXP = "SELECT expediente, nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";
    
    protected static final String ALTA_DE_UN_ALUMNO = "SELECT ";

    protected static final String DAR_DE_BAJA_ALUMNO = "DELETE*FROM alumnos WHERE expediente = ? ";

}
<<<<<<< HEAD
=======
>>>>>>> 8fc9065ae11502455ba61e8d4458e2ce4fb02e26
>>>>>>> d29dd218349addfca7bebd047c29e260fb123868
