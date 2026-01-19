package dev.mendo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Esta clase extiende Application para crear una aplicación JavaFX que gestiona usuarios y muestra ayuda en un WebView usando un archivo Markdown.
// Se utilizan las bibliotecas JavaFX y flexmark (para convertir Markdown a HTML) en este proyecto.

public class GestionUsuariosApp extends Application {

    // Declaración de los elementos UI
    private ListView<String> listaUsuarios;  // Para mostrar la lista de usuarios
    private TextField nombreField, correoField;  // Campos de texto para ingresar nombre y correo del usuario

    @Override
    public void start(Stage primaryStage) {
        // Crear el campo de texto para el nombre
        nombreField = new TextField();
        nombreField.setPromptText("Nombre"); // Mensaje de sugerencia para el campo de texto

        // Crear el campo de texto para el correo
        correoField = new TextField();
        correoField.setPromptText("Correo"); // Mensaje de sugerencia para el campo de texto

        // Crear el botón de "Añadir Usuario" y configurar su acción
        Button agregarUsuarioButton = new Button("Añadir Usuario");
        agregarUsuarioButton.setOnAction(e -> agregarUsuario());  // Llama al método agregarUsuario cuando se hace clic

        // Crear la lista para mostrar los usuarios añadidos
        listaUsuarios = new ListView<>();
        listaUsuarios.setPrefHeight(200); // Definir la altura de la lista

        // Crear el botón de "Ayuda"
        Button ayudaButton = new Button("Ayuda");
        ayudaButton.setOnAction(e -> mostrarAyuda()); // Llama al método mostrarAyuda cuando se hace clic

        // Crear un layout (contenedor) para los controles (campos, botones, lista de usuarios)
        VBox vbox = new VBox(10, nombreField, correoField, agregarUsuarioButton, listaUsuarios, ayudaButton);
        vbox.setAlignment(Pos.CENTER);  // Centrar los elementos
        vbox.setPadding(new Insets(20));  // Añadir relleno alrededor de los elementos

        // Crear la escena y asignarla al escenario principal (primaryStage)
        Scene scene = new Scene(vbox, 400, 350);  // Definir el tamaño de la ventana
        primaryStage.setTitle("Gestión de Usuarios");  // Título de la ventana
        primaryStage.setScene(scene);  // Asignar la escena al escenario
        primaryStage.show();  // Mostrar la ventana
    }

    // Método para agregar un usuario a la lista
    private void agregarUsuario() {
        // Obtener los valores introducidos en los campos de texto
        String nombre = nombreField.getText();
        String correo = correoField.getText();

        // Comprobar si los campos no están vacíos
        if (!nombre.isEmpty() && !correo.isEmpty()) {
            // Agregar el usuario a la lista de usuarios en el formato "Nombre | Correo"
            listaUsuarios.getItems().add("Nombre: " + nombre + " | Correo: " + correo);

            // Limpiar los campos de texto para ingresar nuevos valores
            nombreField.clear();
            correoField.clear();
        } else {
            // Si los campos están vacíos, mostrar un mensaje de advertencia
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor ingrese nombre y correo.", ButtonType.OK);
            alert.show();  // Mostrar la alerta
        }
    }

    // Método para mostrar la ventana de ayuda cuando se hace clic en el botón "Ayuda"
    private void mostrarAyuda() {
        try {
            // Cargar el contenido del archivo Markdown de ayuda desde los recursos
            String markdownContent = cargarArchivoAyuda();

            // Convertir el Markdown a HTML utilizando flexmark
            Parser parser = Parser.builder().build();  // Crear el parser para el Markdown
            HtmlRenderer renderer = HtmlRenderer.builder().build();  // Crear el renderizador para convertir a HTML
            String htmlContent = renderer.render(parser.parse(markdownContent));  // Convertir el contenido Markdown a HTML

            // Crear un Alert de tipo "información" para mostrar el HTML dentro de un WebView
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText(null);  // No mostrar encabezado

            // Crear un WebView para mostrar el contenido HTML
            WebView webView = new WebView();
            webView.getEngine().loadContent(htmlContent);  // Cargar el HTML en el WebView

            // Añadir el WebView al contenido de la alerta
            alert.getDialogPane().setContent(webView);
            alert.showAndWait();  // Mostrar la alerta con el WebView
        } catch (Exception e) {
            e.printStackTrace();  // Mostrar el error si algo sale mal
        }
    }

    // Método para cargar el archivo Markdown de ayuda desde los recursos
    public String cargarArchivoAyuda() {
        StringBuilder contenido = new StringBuilder();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ayuda.md");  // Cargar el archivo desde recursos
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {  // Leer el archivo
            String line;
            while ((line = reader.readLine()) != null) {
                contenido.append(line).append("\n");  // Añadir cada línea al contenido
            }
        } catch (IOException e) {
            e.printStackTrace();  // Mostrar el error si no se puede leer el archivo
            return "Error al cargar el archivo de ayuda.";  // Retornar un mensaje de error si falla
        }
        return contenido.toString();  // Devolver el contenido cargado del archivo
    }

    // Método principal para lanzar la aplicación
    public static void main(String[] args) {
        launch(args);  // Iniciar la aplicación JavaFX
    }
}
