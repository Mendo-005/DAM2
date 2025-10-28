package es.ciudadescolar.clases;
public class Fichas {
    
    private String Nombre; 
    private String Contraseña; 
    private Integer Edad; 
    private Float Altura; 
    private Float Peso;
    

    public String getNombre() {
        return Nombre;
    }


    public void setNombre(String nombre) {
        Nombre = nombre;
    }


    public String getContraseña() {
        return Contraseña;
    }


    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }


    public Integer getEdad() {
        return Edad;
    }


    public void setEdad(Integer edad) {
        Edad = edad;
    }


    public Float getAltura() {
        return Altura;
    }


    public void setAltura(Float altura) {
        Altura = altura;
    }


    public Float getPeso() {
        return Peso;
    }


    public void setPeso(Float peso) {
        Peso = peso;
    }


    public Fichas(String nombre, String contraseña, Integer edad, Float altura, Float peso) {
        Nombre = nombre;
        Contraseña = contraseña;
        Edad = edad;
        Altura = altura;
        Peso = peso;
    }


    @Override
    public String toString() {
        return "[Nombre=" + Nombre + ", Contraseña=" + Contraseña + ", Edad=" + Edad + ", Altura=" + Altura
                + ", Peso=" + Peso + "]";
    }
}
