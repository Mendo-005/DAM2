package dev.mendo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PracticaExtra2FX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de clientes (JavaFX)");

        Button botonRegistrar = new Button("Registrar Nuevo Cliente");
        botonRegistrar.setOnAction(e -> abrirVentanaRegistro(primaryStage));

        BorderPane root = new BorderPane();
        VBox center = new VBox(botonRegistrar);
        center.setAlignment(Pos.CENTER);
        root.setCenter(center);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirVentanaRegistro(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Registro de Cliente");

        // Form fields
        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblCorreo = new Label("Correo Electrónico:");
        TextField txtCorreo = new TextField();

        Label lblTipo = new Label("Tipo de Cliente:");
        ComboBox<String> comboTipos = new ComboBox<>();
        comboTipos.getItems().addAll("Regular", "Premium", "VIP");
        comboTipos.getSelectionModel().selectFirst();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20, 20, 0, 20));

        form.add(lblNombre, 0, 0);
        form.add(txtNombre, 1, 0);
        form.add(lblCorreo, 0, 1);
        form.add(txtCorreo, 1, 1);
        form.add(lblTipo, 0, 2);
        form.add(comboTipos, 1, 2);

        // South area
        CheckBox chkAcepto = new CheckBox("Acepto los términos y condiciones");
        Button btnConfirmar = new Button("Confirmar Registro");
        btnConfirmar.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
        btnConfirmar.setDisable(true);

        Label lblMensaje = new Label("Debe aceptar los términos para continuar...");
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(360);
        lblMensaje.setAlignment(Pos.CENTER);

        VBox south = new VBox(10, chkAcepto, btnConfirmar, lblMensaje);
        south.setPadding(new Insets(10, 20, 20, 20));
        south.setAlignment(Pos.CENTER);

        // Validation logic
        Runnable comprobarValidaciones = () -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            boolean isTyCAceptado = chkAcepto.isSelected();

            if (!isTyCAceptado) {
                lblMensaje.setText("Debe aceptar los términos para continuar...");
            } else {
                lblMensaje.setText("");
            }

            boolean datosValidos = !nombre.isEmpty() && !correo.isEmpty() && isTyCAceptado;
            if (datosValidos && !correo.contains("@")) {
                lblMensaje.setText("Formato de correo erroneo...");
                datosValidos = false;
            }

            btnConfirmar.setDisable(!datosValidos);
        };

        // Add listeners
        txtNombre.textProperty().addListener((obs, oldV, newV) -> comprobarValidaciones.run());
        txtCorreo.textProperty().addListener((obs, oldV, newV) -> comprobarValidaciones.run());
        chkAcepto.selectedProperty().addListener((obs, oldV, newV) -> comprobarValidaciones.run());

        btnConfirmar.setOnAction(e -> lblMensaje.setText("Cliente registrado: " + txtNombre.getText() + ", " + comboTipos.getSelectionModel().getSelectedItem()));

        // Assemble dialog
        BorderPane dialogRoot = new BorderPane();
        dialogRoot.setCenter(form);
        dialogRoot.setBottom(south);

        Scene dialogScene = new Scene(dialogRoot, 500, 450);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
