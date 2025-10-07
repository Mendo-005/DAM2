package es.ciudadescolar;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Programa para la Práctica 2 UT2 - Desarrollo de Interfaces
 * 
 * Esta aplicación crea una interfaz gráfica que registra las interacciones del usuario
 * incluyendo eventos de ratón y teclado en diferentes componentes de la ventana.
 * 
 * Funcionalidades implementadas:
 * - Detección de entrada y salida del ratón en el campo de texto
 * - Detección de entrada y salida del ratón en el área de registro
 * - Seguimiento del movimiento y arrastre del ratón con coordenadas
 * - Registro de teclas presionadas y liberadas
 * 
 * @author [Tu nombre]
 * @version 1.0
 * @since 2025-10-07
 */


public class ProgramaPractica2 extends JFrame 
{
    // Componentes de la interfaz
    private JTextField campoTexto;
    private JButton botonEnviar;
    private JTextArea areaRegistro;
    private JPanel panelRegistro;

    // Colores y fuentes
    private static final Color COLOR_FONDO = new Color(236, 240, 241);
    private static final Color COLOR_AREA_REGISTRO_NORMAL = new Color(210, 245, 200);
    private static final Color COLOR_AREA_REGISTRO_ACTIVO = new Color(255, 255, 160);
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_TEXTO = new Font("Consolas", Font.PLAIN, 14);


    /**
     * Constructor principal que inicializa la ventana y la hace visible
     */
    public ProgramaPractica2() 
    {
        configurarVentana();
        setVisible(true);
    }

    /**
     * Configura las propiedades básicas de la ventana principal
     * y establece la estructura de paneles
     */
    private void configurarVentana()
    {
        // Configuración básica de la ventana
        setTitle("Registro de Acciones de Usuario");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

         // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(panelPrincipal);

        // Título
        JLabel lblTitulo = new JLabel("Registro de Interacción", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(new Color(41, 128, 185));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel Superior
        panelPrincipal.add(panelSuperior(), BorderLayout.CENTER);

        // Panel Inferior
        panelPrincipal.add(panelInferior(), BorderLayout.SOUTH);
    }

    /**
     * Crea y configura el panel superior que contiene el campo de texto
     * y el botón de envío, junto con sus eventos de ratón y teclado
     * @return JPanel configurado para la parte superior
     */
    private JPanel panelSuperior()
    {
       // Panel superior con campo de texto y botón
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(COLOR_FONDO);

        // Panel registro con color de fondo
        panelRegistro = new JPanel(new BorderLayout());
        panelRegistro.setBackground(COLOR_AREA_REGISTRO_NORMAL);
        panelRegistro.setPreferredSize(new Dimension(0, 60));

        campoTexto = new JTextField();
        campoTexto.setFont(FUENTE_TEXTO);
        campoTexto.setMargin(new Insets(10, 10, 10, 10));
        panelRegistro.add(campoTexto, BorderLayout.CENTER);

        botonEnviar = new JButton("Enviar");
        botonEnviar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonEnviar.setBackground(new Color(52, 152, 219));
        botonEnviar.setForeground(Color.WHITE);
        botonEnviar.setFocusPainted(false);
        botonEnviar.setPreferredSize(new Dimension(90, 40));
        panelRegistro.add(botonEnviar, BorderLayout.EAST);

        panelSuperior.add(panelRegistro, BorderLayout.CENTER);

        return panelSuperior;
    }
    
    /**
     * Crea y configura el panel inferior que contiene el área de registro
     * donde se muestran todas las acciones del usuario
     * @return JPanel configurado para la parte inferior
     */
    private JPanel panelInferior()
    {
        // Área de registro de acciones
        areaRegistro = new JTextArea();
        areaRegistro.setFont(FUENTE_TEXTO);
        areaRegistro.setEditable(false);
        JScrollPane scrollRegistro = new JScrollPane(areaRegistro);

        // Listeners campo de texto
        campoTexto.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registrarAccion("Ratón entra en el campo de texto.");
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registrarAccion("Ratón sale del campo de texto.");
            }
        });

        campoTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                registrarAccion("Tecla presionada: " + java.awt.event.KeyEvent.getKeyText(e.getKeyCode()));
            }
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                registrarAccion("Tecla liberada: " + java.awt.event.KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        // Listeners área de registro
        panelRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                panelRegistro.setBackground(COLOR_AREA_REGISTRO_ACTIVO);
                registrarAccion("Ratón ha entrado en el área de registro.");
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panelRegistro.setBackground(COLOR_AREA_REGISTRO_NORMAL);
                registrarAccion("Ratón sale del área de registro.");
            }
        });

        panelRegistro.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                registrarAccion("Ratón movido: X=" + e.getX() + ", Y=" + e.getY());
            }
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                registrarAccion("Ratón arrastrado: X=" + e.getX() + ", Y=" + e.getY());
            }
        });

        // Botón enviar
        botonEnviar.addActionListener(e -> {
            String texto = campoTexto.getText().trim();
            if (!texto.isEmpty()) {
                registrarAccion("Texto enviado: " + texto);
                campoTexto.setText("");
            }
        });

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.add(scrollRegistro, BorderLayout.CENTER);
        return panelInferior;
    }

    /**
     * Registra una acción en el área de texto y mueve el cursor al final
     * @param accion Descripción de la acción realizada por el usuario
     */
    private void registrarAccion(String accion) {
        areaRegistro.append(accion + "\n");
        areaRegistro.setCaretPosition(areaRegistro.getDocument().getLength());
    }

    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ProgramaPractica2());
    }
}

