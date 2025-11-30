package es.ciudadescolar;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Launcher {

    public static void main(String[] args) {
        // Estilo visual del sistema operativo
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel de Control IA - Hugging Face");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(450, 250);
            frame.setLayout(new GridBagLayout());

            // Panel principal
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            JLabel lblTitulo = new JLabel("Detector de Emociones");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblSub = new JLabel("(Backend: PyTorch + Transformers)");
            lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton btnLanzar = new JButton(" INICIAR CÁMARA ");
            btnLanzar.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnLanzar.setBackground(new Color(50, 168, 82)); // Verde
            btnLanzar.setForeground(Color.WHITE);
            btnLanzar.setFocusPainted(false);
            btnLanzar.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnLanzar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            btnLanzar.addActionListener(e -> ejecutarPython(btnLanzar));

            panel.add(lblTitulo);
            panel.add(lblSub);
            panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
            panel.add(btnLanzar);

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void ejecutarPython(JButton btn) {
        btn.setEnabled(false); // Evitar doble clic
        btn.setText("Iniciando...");

        new Thread(() -> {
            try {
                // RUTA: Ajusta si es necesario
                String pythonCmd = "python"; 
                String scriptPath = "C:/Users/mario/DAM2/DDI/A3/Practica 3/ejercicio3/src/python/emotion_detector.py";

                File scriptFile = new File(scriptPath);
                if (!scriptFile.exists()) {
                    JOptionPane.showMessageDialog(null, "Error: No encuentro: " + scriptFile.getAbsolutePath());
                    resetBtn(btn);
                    return;
                }

                System.out.println("Lanzando Hugging Face desde: " + scriptFile.getAbsolutePath());

                ProcessBuilder pb = new ProcessBuilder(pythonCmd, scriptPath);
                pb.redirectErrorStream(true);
                
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[PYTHON]: " + line);
                }

                int exitCode = process.waitFor();
                System.out.println("Proceso terminado: " + exitCode);
                
                resetBtn(btn);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                resetBtn(btn);
            }
        }).start();
    }
    
    private static void resetBtn(JButton btn) {
        SwingUtilities.invokeLater(() -> {
            btn.setText(" INICIAR CÁMARA ");
            btn.setEnabled(true);
        });
    }
}