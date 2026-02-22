package dev.mendo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador JavaFX que gestiona los eventos de la interfaz ProductView.fxml.
 * Actúa como intermediario entre la vista (FXML) y la lógica de negocio (ProductService).
 */
public class ProductController {

    // ---- Campos del formulario ----
    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtPrice;

    // ---- Tabla de productos ----
    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Integer> colQuantity;
    @FXML private TableColumn<Product, Double> colPrice;

    // ---- Etiqueta de estado ----
    @FXML private Label lblStatus;

    /** Lista observable que enlaza la tabla con los datos en memoria */
    private final ObservableList<Product> observableList = FXCollections.observableArrayList();

    /** Servicio con la lógica de negocio y almacenamiento */
    private final ProductService productService = new ProductService();

    /**
     * Método llamado automáticamente por JavaFX al cargar el FXML.
     * Configura las columnas de la tabla y su fuente de datos.
     */
    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableProducts.setItems(observableList);

        // Al seleccionar una fila, rellenar el formulario para edición
        tableProducts.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        txtName.setText(newVal.getName());
                        txtQuantity.setText(String.valueOf(newVal.getQuantity()));
                        txtPrice.setText(String.valueOf(newVal.getPrice()));
                    }
                });
    }

    /**
     * Añade un nuevo producto al stock.
     * Valida el formulario y muestra mensajes de error si los datos son incorrectos.
     */
    @FXML
    private void handleAddProduct() {
        try {
            String name = txtName.getText();
            int quantity = parseQuantity(txtQuantity.getText());
            double price = parsePrice(txtPrice.getText());

            productService.addProduct(name, quantity, price);
            refreshTable();
            clearForm();
            setStatus("✔ Producto '" + name.trim() + "' añadido correctamente.", false);
        } catch (IllegalArgumentException e) {
            setStatus("⚠ Error: " + e.getMessage(), true);
        }
    }

    /**
     * Actualiza el producto seleccionado en la tabla con los datos del formulario.
     */
    @FXML
    private void handleUpdateProduct() {
        int selectedIndex = tableProducts.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            setStatus("⚠ Selecciona un producto de la tabla para editar.", true);
            return;
        }
        try {
            String name = txtName.getText();
            int quantity = parseQuantity(txtQuantity.getText());
            double price = parsePrice(txtPrice.getText());

            productService.updateProduct(selectedIndex, name, quantity, price);
            refreshTable();
            clearForm();
            setStatus("✔ Producto actualizado correctamente.", false);
        } catch (IllegalArgumentException e) {
            setStatus("⚠ Error: " + e.getMessage(), true);
        }
    }

    /**
     * Elimina el producto seleccionado en la tabla.
     */
    @FXML
    private void handleDeleteProduct() {
        int selectedIndex = tableProducts.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            setStatus("⚠ Selecciona un producto de la tabla para eliminar.", true);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar el producto seleccionado?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmar eliminación");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                String name = observableList.get(selectedIndex).getName();
                productService.removeProduct(selectedIndex);
                refreshTable();
                clearForm();
                setStatus("✔ Producto '" + name + "' eliminado.", false);
            }
        });
    }

    /**
     * Limpia los campos del formulario.
     */
    @FXML
    private void handleClear() {
        clearForm();
        tableProducts.getSelectionModel().clearSelection();
        setStatus("Formulario limpiado.", false);
    }

    // -------------------------------------------------------------------------
    // Métodos auxiliares privados
    // -------------------------------------------------------------------------

    /** Sincroniza la lista observable con el estado actual del servicio */
    private void refreshTable() {
        observableList.setAll(productService.getProducts());
    }

    /** Limpia todos los campos del formulario */
    private void clearForm() {
        txtName.clear();
        txtQuantity.clear();
        txtPrice.clear();
    }

    /**
     * Actualiza la etiqueta de estado.
     *
     * @param message Texto a mostrar
     * @param isError true para estilo de error, false para éxito/información
     */
    private void setStatus(String message, boolean isError) {
        lblStatus.setText(message);
        lblStatus.setStyle(isError
                ? "-fx-text-fill: #c0392b; -fx-font-weight: bold;"
                : "-fx-text-fill: #27ae60; -fx-font-weight: bold;");
    }

    /**
     * Parsea y valida el campo de cantidad como entero.
     *
     * @throws IllegalArgumentException si el texto no es un entero válido
     */
    private int parseQuantity(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad debe ser un número entero válido.");
        }
    }

    /**
     * Parsea y valida el campo de precio como decimal.
     *
     * @throws IllegalArgumentException si el texto no es un decimal válido
     */
    private double parsePrice(String text) {
        try {
            return Double.parseDouble(text.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe ser un número decimal válido (ej: 9.99).");
        }
    }
}
