package dev.mendo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Capa de lógica de negocio para gestionar el stock de productos.
 * Almacena los productos en un ArrayList en memoria y aplica
 * validaciones de seguridad sobre los datos de entrada.
 */
public class ProductService {

    /** Almacenamiento en memoria de los productos */
    private final List<Product> products = new ArrayList<>();

    /** Patrón para detectar posibles inyecciones de código o HTML */
    private static final Pattern INVALID_CHARS = Pattern.compile("[<>\"'%;()&+]");

    /** Longitud máxima permitida para el nombre de un producto */
    private static final int MAX_NAME_LENGTH = 100;

    // -------------------------------------------------------------------------
    // Validaciones
    // -------------------------------------------------------------------------

    /**
     * Valida todos los campos de un producto antes de insertarlo o actualizarlo.
     *
     * @param name     Nombre del producto
     * @param quantity Cantidad del producto
     * @param price    Precio del producto
     * @throws IllegalArgumentException si algún campo no supera la validación
     */
    public void validateProduct(String name, int quantity, double price) {
        validateName(name);
        validateQuantity(quantity);
        validatePrice(price);
    }

    /**
     * Valida el nombre: no nulo, no vacío, longitud máxima y sin caracteres peligrosos.
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (name.trim().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los " + MAX_NAME_LENGTH + " caracteres.");
        }
        if (INVALID_CHARS.matcher(name).find()) {
            throw new IllegalArgumentException(
                    "El nombre contiene caracteres no permitidos (<, >, \", ', %, ;, (, ), &, +).");
        }
    }

    /**
     * Valida que la cantidad no sea negativa.
     */
    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
    }

    /**
     * Valida que el precio sea mayor que cero.
     */
    private void validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        if (Double.isNaN(price) || Double.isInfinite(price)) {
            throw new IllegalArgumentException("El precio no es un valor numérico válido.");
        }
    }

    // -------------------------------------------------------------------------
    // Operaciones CRUD
    // -------------------------------------------------------------------------

    /**
     * Agrega un nuevo producto al stock tras validar sus campos.
     *
     * @param name     Nombre del producto
     * @param quantity Cantidad inicial en stock
     * @param price    Precio unitario
     * @return El producto creado
     * @throws IllegalArgumentException si los datos no son válidos
     */
    public Product addProduct(String name, int quantity, double price) {
        validateProduct(name, quantity, price);
        String trimmedName = name.trim();
        Product product = new Product(trimmedName, quantity, price);
        products.add(product);
        return product;
    }

    /**
     * Elimina un producto del stock por su posición en la lista.
     *
     * @param index Índice del producto a eliminar
     * @throws IndexOutOfBoundsException si el índice no es válido
     */
    public void removeProduct(int index) {
        if (index < 0 || index >= products.size()) {
            throw new IndexOutOfBoundsException("Índice de producto no válido: " + index);
        }
        products.remove(index);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param index    Índice del producto a editar
     * @param name     Nuevo nombre
     * @param quantity Nueva cantidad
     * @param price    Nuevo precio
     * @throws IllegalArgumentException  si los datos no son válidos
     * @throws IndexOutOfBoundsException si el índice no existe
     */
    public void updateProduct(int index, String name, int quantity, double price) {
        if (index < 0 || index >= products.size()) {
            throw new IndexOutOfBoundsException("Índice de producto no válido: " + index);
        }
        validateProduct(name, quantity, price);
        String trimmedName = name.trim();
        Product p = products.get(index);
        p.setName(trimmedName);
        p.setQuantity(quantity);
        p.setPrice(price);
    }

    /**
     * Devuelve la lista de productos (no modificable desde fuera).
     *
     * @return Lista inmutable de productos
     */
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    /**
     * Devuelve el número total de productos en el stock.
     */
    public int getProductCount() {
        return products.size();
    }

    /**
     * Busca un producto por nombre (búsqueda insensible a mayúsculas).
     *
     * @param name Nombre a buscar
     * @return El primer producto que coincida, o null si no se encuentra
     */
    public Product findByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }
}
