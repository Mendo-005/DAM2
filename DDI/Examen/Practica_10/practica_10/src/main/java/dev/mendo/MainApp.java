package dev.mendo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 * Carga la vista FXML y lanza la ventana principal.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ProductView.fxml"));
        Scene scene = new Scene(loader.load(), 800, 550);
        primaryStage.setTitle("Gestión de Stock — DAM2 Práctica 10");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    /**
     * Punto de entrada de la JVM.
     * Se necesita un método main independiente cuando se lanza desde Maven
     * sin el módulo de JavaFX en el classpath del sistema.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
