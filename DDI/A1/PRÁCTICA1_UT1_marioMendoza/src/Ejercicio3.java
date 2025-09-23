package src;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Ejercicio3 {
    private static JFrame ventanaLista;
    private static JFrame ventanaReproductor;
    private static JLabel lblCancionActual;
    private static JLabel lblArtistaActual;
    private static DefaultListModel<String> modeloLista;
    private static JList<String> listaCanciones;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            crearVentanaLista();
            crearVentanaReproductor();
            
            // Posicionar las ventanas una al lado de la otra
            ventanaLista.setLocation(100, 100);
            ventanaReproductor.setLocation(500, 100);
            
            ventanaLista.setVisible(true);
            ventanaReproductor.setVisible(true);
        });
    }
    
    private static void crearVentanaLista() {
        ventanaLista = new JFrame("Lista de Música");
        ventanaLista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaLista.setSize(350, 500);
        ventanaLista.setLayout(new BorderLayout());
        
        // Panel superior con icono y título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(245, 245, 247));
        panelSuperior.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        // Icono de música
        JLabel iconoMusica = new JLabel("🎵");
        iconoMusica.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel titulo = new JLabel("Mi Música");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(51, 51, 51));
        
        panelSuperior.add(iconoMusica, BorderLayout.WEST);
        panelSuperior.add(titulo, BorderLayout.CENTER);
        
        // Botón de menú
        JButton btnMenu = new JButton("⋯");
        btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnMenu.setForeground(new Color(51, 122, 183));
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        panelSuperior.add(btnMenu, BorderLayout.EAST);
        
        // Lista de canciones
        modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Song Title - Singer");
        modeloLista.addElement("Bohemian Rhapsody - Queen");
        modeloLista.addElement("Imagine - John Lennon");
        modeloLista.addElement("Hotel California - Eagles");
        modeloLista.addElement("Billie Jean - Michael Jackson");
        modeloLista.addElement("Sweet Child O' Mine - Guns N' Roses");
        modeloLista.addElement("Stairway to Heaven - Led Zeppelin");
        
        listaCanciones = new JList<>(modeloLista);
        listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCanciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaCanciones.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        // Personalizar el renderer de la lista
        listaCanciones.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                setBorder(new EmptyBorder(12, 15, 12, 15));
                
                if (isSelected) {
                    setBackground(new Color(0, 122, 255, 50));
                    setForeground(new Color(0, 122, 255));
                } else {
                    setBackground(Color.WHITE);
                    setForeground(new Color(51, 51, 51));
                }
                
                return this;
            }
        });
        
        // Listener para selección de canciones
        listaCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaCanciones.getSelectedValue() != null) {
                String cancionSeleccionada = listaCanciones.getSelectedValue();
                String[] partes = cancionSeleccionada.split(" - ");
                if (partes.length >= 2) {
                    lblCancionActual.setText(partes[0]);
                    lblArtistaActual.setText(partes[1]);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(listaCanciones);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Panel inferior con controles básicos
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBackground(new Color(245, 245, 247));
        panelInferior.setBorder(new EmptyBorder(10, 0, 15, 0));
        
        JButton btnPlay = new JButton("▶");
        JButton btnNext = new JButton("⏭");
        
        btnPlay.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        btnNext.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        
        // Estilo de botones
        for (JButton btn : new JButton[]{btnPlay, btnNext}) {
            btn.setPreferredSize(new Dimension(45, 35));
            btn.setBackground(new Color(0, 122, 255));
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
        }
        
        panelInferior.add(btnPlay);
        panelInferior.add(btnNext);
        
        ventanaLista.add(panelSuperior, BorderLayout.NORTH);
        ventanaLista.add(scrollPane, BorderLayout.CENTER);
        ventanaLista.add(panelInferior, BorderLayout.SOUTH);
    }
    
    private static void crearVentanaReproductor() {
        ventanaReproductor = new JFrame("Reproductor");
        ventanaReproductor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaReproductor.setSize(350, 500);
        ventanaReproductor.setLayout(new BorderLayout());
        ventanaReproductor.getContentPane().setBackground(new Color(245, 245, 247));
        
        // Panel principal con información de la canción
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(new Color(245, 245, 247));
        panelPrincipal.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Imagen del álbum (simulada con icono)
        JLabel imagenAlbum = new JLabel("🎵");
        imagenAlbum.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
        imagenAlbum.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagenAlbum.setPreferredSize(new Dimension(200, 200));
        imagenAlbum.setOpaque(true);
        imagenAlbum.setBackground(new Color(220, 220, 220));
        imagenAlbum.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Información de la canción
        lblCancionActual = new JLabel("Song Title");
        lblCancionActual.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCancionActual.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCancionActual.setForeground(new Color(51, 51, 51));
        
        lblArtistaActual = new JLabel("Singer");
        lblArtistaActual.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblArtistaActual.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblArtistaActual.setForeground(new Color(102, 102, 102));
        
        // Barra de progreso
        JSlider barraProgreso = new JSlider(0, 100, 30);
        barraProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraProgreso.setMaximumSize(new Dimension(250, 30));
        barraProgreso.setBackground(new Color(245, 245, 247));
        
        // Panel de controles con GridLayout
        JPanel panelControles = new JPanel(new GridLayout(2, 3, 20, 15));
        panelControles.setBackground(new Color(245, 245, 247));
        panelControles.setMaximumSize(new Dimension(280, 120));
        
        // Botones de control
        JButton btnAnterior = new JButton("⏮");
        JButton btnReproducir = new JButton("▶");
        JButton btnSiguiente = new JButton("⏭");
        JButton btnVolumenBajo = new JButton("🔇");
        JButton btnVolumenAlto = new JButton("🔊");
        JButton btnOpciones = new JButton("⋯");
        
        // Configurar botones
        JButton[] botones = {btnAnterior, btnReproducir, btnSiguiente, btnVolumenBajo, btnVolumenAlto, btnOpciones};
        for (JButton btn : botones) {
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            btn.setPreferredSize(new Dimension(60, 45));
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(0, 122, 255));
            btn.setBorderPainted(true);
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            btn.setFocusPainted(false);
        }
        
        // Hacer el botón de reproducir más prominente
        btnReproducir.setBackground(new Color(0, 122, 255));
        btnReproducir.setForeground(Color.WHITE);
        btnReproducir.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        panelControles.add(btnAnterior);
        panelControles.add(btnReproducir);
        panelControles.add(btnSiguiente);
        panelControles.add(btnVolumenBajo);
        panelControles.add(btnVolumenAlto);
        panelControles.add(btnOpciones);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(imagenAlbum);
        panelPrincipal.add(Box.createVerticalStrut(30));
        panelPrincipal.add(lblCancionActual);
        panelPrincipal.add(Box.createVerticalStrut(5));
        panelPrincipal.add(lblArtistaActual);
        panelPrincipal.add(Box.createVerticalStrut(30));
        panelPrincipal.add(barraProgreso);
        panelPrincipal.add(Box.createVerticalStrut(30));
        panelPrincipal.add(panelControles);
        
        ventanaReproductor.add(panelPrincipal, BorderLayout.CENTER);
    }
}
