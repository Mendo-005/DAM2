package dev.mendo;

/**
 * Modelo de datos que representa un producto del stock de la tienda.
 * Contiene nombre, cantidad y precio como atributos principales.
 */
public class Product {

    private String name;
    private int quantity;
    private double price;

    /**
     * Constructor principal del producto.
     *
     * @param name     Nombre del producto (no puede ser nulo ni vacío)
     * @param quantity Cantidad disponible en stock (debe ser >= 0)
     * @param price    Precio unitario del producto (debe ser > 0)
     */
    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // --- Getters ---

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // --- Setters ---

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product{name='%s', quantity=%d, price=%.2f}", name, quantity, price);
    }
}
