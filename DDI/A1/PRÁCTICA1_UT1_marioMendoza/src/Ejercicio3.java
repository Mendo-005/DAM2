package src;

import java.awt.*;
import javax.swing.*;

// Reproductor de música simple
public class Ejercicio3 {
    private static JFrame ventanaLista;
    private static JFrame ventanaReproductor;
    private static JLabel lblCancion;
    private static JList<String> listaCanciones;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            crearVentanaLista();
            crearVentanaReproductor();
            
            ventanaLista.setLocation(100, 100);
            ventanaReproductor.setLocation(400, 100);
            
            ventanaLista.setVisible(true);
            ventanaReproductor.setVisible(true);
        });
    }
    
    private static void crearVentanaLista() {
        ventanaLista = new JFrame("🎵 Playlist");
        ventanaLista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaLista.setSize(300, 400);
        ventanaLista.setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("♪ Mi Música");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        DefaultListModel<String> modelo = new DefaultListModel<>();
        modelo.addElement("Bohemian Rhapsody - Queen");
        modelo.addElement("Imagine - John Lennon");
        modelo.addElement("Hotel California - Eagles");
        modelo.addElement("Billie Jean - Michael Jackson");
        modelo.addElement("Sweet Child O' Mine - GNR");
        
        listaCanciones = new JList<>(modelo);
        listaCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaCanciones.getSelectedValue() != null) {
                String cancion = listaCanciones.getSelectedValue().split(" - ")[0];
                lblCancion.setText("♪ " + cancion);
            }
        });
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(new JButton("▶"));
        panelBotones.add(new JButton("⏸"));
        panelBotones.add(new JButton("⏹"));
        
        ventanaLista.add(titulo, BorderLayout.NORTH);
        ventanaLista.add(new JScrollPane(listaCanciones), BorderLayout.CENTER);
        ventanaLista.add(panelBotones, BorderLayout.SOUTH);
    }
    
    private static void crearVentanaReproductor() {
        ventanaReproductor = new JFrame("🎧 Reproductor");
        ventanaReproductor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaReproductor.setSize(300, 400);
        ventanaReproductor.setLayout(new BorderLayout());
        
        // Pantalla de canción
        lblCancion = new JLabel("♪ Selecciona una canción");
        lblCancion.setHorizontalAlignment(JLabel.CENTER);
        lblCancion.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Área visual del reproductor
        JLabel icono = new JLabel("MUSIC");
        icono.setHorizontalAlignment(JLabel.CENTER);
        icono.setFont(new Font("Arial", Font.BOLD, 36));
        icono.setOpaque(true);
        icono.setBackground(new Color(220, 220, 220));
        icono.setBorder(BorderFactory.createRaisedBevelBorder());
        icono.setPreferredSize(new Dimension(200, 150));
        
        // Controles principales (GridLayout 2x3)
        JPanel controles = new JPanel(new GridLayout(2, 3, 10, 10));
        controles.add(new JButton("⏮"));  // Anterior
        controles.add(new JButton("▶"));   // Play
        controles.add(new JButton("⏭"));  // Siguiente
        controles.add(new JButton("🔇"));  // Silencio
        controles.add(new JButton("⏹"));  // Stop
        controles.add(new JButton("🔊"));  // Volumen
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(lblCancion, BorderLayout.NORTH);
        panelSuperior.add(icono, BorderLayout.CENTER);
        
        ventanaReproductor.add(panelSuperior, BorderLayout.CENTER);
        ventanaReproductor.add(controles, BorderLayout.SOUTH);
    }
}
