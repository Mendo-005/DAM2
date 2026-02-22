package dev.mendo.JUnit;

public class Banco {
    
    private double saldo;

    public Banco(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void ingresar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad inválida");
        saldo += cantidad;
    }

    public void retirar(double cantidad) {
        if (cantidad > saldo) throw new IllegalArgumentException("Saldo insuficiente");
        saldo -= cantidad;
    }

    public double getSaldo() {
        return saldo;
    }
}