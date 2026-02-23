package dev.mendo;

// Clase de ejemplo sin dependencias externas.
// Contiene operaciones matemáticas básicas para demostrar JUnit puro.
public class Calculadora {

    // Devuelve la suma de dos enteros
    public int sumar(int a, int b)       { return a + b; }

    // Devuelve la resta de dos enteros
    public int restar(int a, int b)      { return a - b; }

    // Devuelve la multiplicación de dos enteros
    public int multiplicar(int a, int b) { return a * b; }

    // Devuelve la división. Lanza excepción si el divisor es 0.
    // → Esto nos permite probar assertThrows en el test
    public double dividir(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("No se puede dividir por cero");
        return (double) a / b;
    }

    // Devuelve el factorial de n. Lanza excepción si n es negativo.
    // → Otro caso para probar assertThrows
    public int factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("No puede ser negativo");
        int resultado = 1;
        for (int i = 1; i <= n; i++) resultado *= i;
        return resultado;
    }

    // Devuelve true si n es primo, false si no lo es.
    // → Nos permite probar assertTrue y assertFalse en el test
    public boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    }
}