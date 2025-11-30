package es.ciudadescolar;

import javax.swing.*;
import org.vosk.Model;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class TestVoiceButton {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba de Componente VoiceButton");
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());
            frame.setLocationRelativeTo(null);

            String rutaModelo = "ejercicio5\\src\\main\\resources\\model"; 
            // ---------------------------------------------------------

            Model model = null;
            try {
                // Verificamos si la carpeta existe antes de intentar cargar
                if (!new File(rutaModelo).exists()) {
                     JOptionPane.showMessageDialog(frame, "Error: La carpeta del modelo no existe en:\n" + rutaModelo, "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                
                System.out.println("Cargando modelo...");
                model = new Model(rutaModelo);
                System.out.println("Modelo cargado.");
                
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Instanciamos nuestro componente personalizado
            // Texto del botón: "¡Di HOLA!", Palabra mágica: "hola"
            VoiceButton botonVoz = new VoiceButton("¡Di HOLA!", "hola", model);

            // Añadimos una acción estándar de Swing (Paso 2 completado)
            botonVoz.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "¡Acción ejecutada por voz o clic!");
            });

            frame.add(botonVoz);
            
            // Asegurar que el hilo de voz se cierre al cerrar la ventana
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (botonVoz != null) botonVoz.detenerEscucha();
                    System.exit(0);
                }
            });

            frame.setVisible(true);
        });
    }
}