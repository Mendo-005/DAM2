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

public class SimpleVoiceTranscriber extends JFrame {

    private JTextArea transcriptArea;
    private JLabel statusLabel;

    public SimpleVoiceTranscriber() {

        setTitle("Transcriptor de Voz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        transcriptArea = new JTextArea();
        transcriptArea.setEditable(false);
        transcriptArea.setLineWrap(true);
        transcriptArea.setWrapStyleWord(true);
        transcriptArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(transcriptArea);
        add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Estado: Cargando modelo...", JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.SOUTH);

        new Thread(this::startSpeechRecognition).start();
    }

    private void startSpeechRecognition() {
        try {
            LibVosk.setLogLevel(LogLevel.WARNINGS);

            Model model = new Model("target/classes/model");
            Recognizer recognizer = new Recognizer(model, 16000);

            // Formato compatible
            AudioFormat format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    16000,
                    16,
                    1,
                    2,
                    16000,
                    false
            );

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);

            microphone.open(format);
            microphone.start();

            SwingUtilities.invokeLater(() -> statusLabel.setText("Escuchando..."));

            byte[] buffer = new byte[4096];

            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);

                if (bytesRead <= 0) continue;

                if (recognizer.acceptWaveForm(buffer, bytesRead)) {

                    String json = recognizer.getResult();
                    String text = extractText(json);

                    if (!text.isEmpty()) {
                        SwingUtilities.invokeLater(() -> {
                            transcriptArea.append(text + "\n");
                            transcriptArea.setCaretPosition(transcriptArea.getDocument().getLength());
                        });
                    }

                } else {

                    String jsonPartial = recognizer.getPartialResult();
                    String partial = extractText(jsonPartial);

                    if (!partial.isEmpty()) {
                        SwingUtilities.invokeLater(() -> statusLabel.setText("Diciendo: " + partial));
                    }
                }
            }

        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> statusLabel.setText("Error: " + e.getMessage()));
            e.printStackTrace();
        }
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleVoiceTranscriber().setVisible(true));
    }
}
