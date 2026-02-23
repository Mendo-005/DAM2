package dev.mendo;

public class Calculadora {

    public int sumar(int a, int b)       { return a + b; }
    public int restar(int a, int b)      { return a - b; }
    public int multiplicar(int a, int b) { return a * b; }

    public double dividir(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("No se puede dividir por cero");
        return (double) a / b;
    }

    public int factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("No puede ser negativo");
        int resultado = 1;
        for (int i = 1; i <= n; i++) resultado *= i;
        return resultado;
    }

    public boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    }
}