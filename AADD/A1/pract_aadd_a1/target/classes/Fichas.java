public class Fichas {
    
    private Strintg Nombre; 
    private Strintg Contraseña; 
    private Integer Edad; 
    private Float Altura; 
    private Float Peso;
    public Strintg getNombre() {
        return Nombre;
    }
    public void setNombre(Strintg nombre) {
        Nombre = nombre;
    }
    public Strintg getContraseña() {
        return Contraseña;
    }
    public void setContraseña(Strintg contraseña) {
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
    
    public Fichas(Strintg nombre, Strintg contraseña, Integer edad, Float altura, Float peso) {
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
