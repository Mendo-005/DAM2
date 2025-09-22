public class Persona
{
    private int edad;
    public int Edad { get { return edad; } set { if (value >= 0) edad = value; } }

    public Persona(int edad) {
        this.edad = edad;
    }
}