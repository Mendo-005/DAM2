package es.ciudadescolar;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Componente VoiceButton: Un botón Swing que puede ser activado por clic
 * o pronunciando una palabra clave específica.
 */
public class VoiceButton extends JButton {

    private String palabraClave;
    private boolean escuchando = false;
    private Thread hiloEscucha;
    private Model modelo; // Referencia al modelo cargado

    // Paso 1: Definir propiedades y constructor
    // Recibe el objeto Model directamente para evitar errores de ruta dentro del componente
    public VoiceButton(String texto, String palabraClave, Model modelo) {
        super(texto);
        this.palabraClave = palabraClave.toLowerCase(); // Normalizamos a minúsculas
        this.modelo = modelo;
        
        // Estilo visual para diferenciarlo de un botón normal
        this.setBackground(new Color(200, 230, 255)); // Azul claro
        this.setToolTipText("Di: '" + palabraClave + "' para activar");

        // Iniciamos la escucha automáticamente al crear el botón
        iniciarEscucha();
    }

    // Paso 2: Determinación de eventos (Activación por voz)
    private void iniciarEscucha() {
        hiloEscucha = new Thread(() -> {
            try {
                // Verificamos que el modelo exista
                if (this.modelo == null) {
                    System.err.println("El modelo de voz es nulo. No se iniciará la escucha.");
                    return;
                }

                LibVosk.setLogLevel(LogLevel.WARNINGS);
                
                // Usamos el modelo pasado por el constructor
                Recognizer recognizer = new Recognizer(this.modelo, 16000);

                AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 16000, 16, 1, 2, 16000, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);

                microphone.open(format);
                microphone.start();
                escuchando = true;

                byte[] buffer = new byte[4096];

                while (escuchando) {
                    int bytesRead = microphone.read(buffer, 0, buffer.length);
                    
                    if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                        String json = recognizer.getResult();
                        verificarComando(json);
                    } 
                    // Nota: Podríamos usar getPartialResult() para mayor velocidad, 
                    // pero getResult() es más preciso.
                }
                microphone.close();
                recognizer.close();

            } catch (Exception e) {
                System.err.println("Error en el servicio de voz del botón: " + e.getMessage());
                e.printStackTrace();
            }
        });
        hiloEscucha.start();
    }

    private void verificarComando(String json) {
        String textoDetectado = extractText(json);
        
        // Si hay texto y contiene la palabra clave
        if (!textoDetectado.isEmpty() && textoDetectado.toLowerCase().contains(palabraClave)) {
            System.out.println("¡Comando de voz detectado!: " + palabraClave);
            
            // Paso 2: Asociar a acciones (Simulamos un clic físico)
            SwingUtilities.invokeLater(() -> {
                this.doClick(); // Esto dispara los ActionListener del usuario
                
                // Feedback visual (parpadeo verde)
                Color original = getBackground();
                setBackground(Color.GREEN);
                Timer timer = new Timer(500, e -> setBackground(original));
                timer.setRepeats(false);
                timer.start();
            });
        }
    }

    // Método auxiliar de tu script original
    private String extractText(String json) {
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            if (obj.has("text")) {
                return obj.get("text").getAsString();
            }
            if (obj.has("partial")) {
                return obj.get("partial").getAsString();
            }
        } catch (Exception ignored) {}
        return "";
    }
    
    // Método para detener la escucha limpiamente
    public void detenerEscucha() {
        this.escuchando = false;
    }
}