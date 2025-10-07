/*
GUÍA DE USUARIO:
Esta aplicación permite registrar las acciones del usuario sobre la interfaz gráfica.
- Escribe texto en el campo superior y pulsa "Enviar" para registrar tu mensaje.
- Observa cómo se registran las acciones del ratón (entrar, salir, mover, arrastrar) tanto en el campo de texto como en el área de registro.
- Las coordenadas X, Y del ratón se mostrarán cuando se mueva o arrastre dentro del área de registro.
- Las teclas presionadas y liberadas se registrarán también.
¡Explora la interfaz y observa cómo se actualiza el registro de acciones!

DESARROLLO DE INTERFACES DAM2 - PRÁCTICA 2 UT2

Autor: [Tu nombre]
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistroAccionesUsuario extends JFrame {
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

    public RegistroAccionesUsuario() {
        setTitle("Registro de acciones del usuario");
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

        panelPrincipal.add(panelSuperior, BorderLayout.CENTER);

        // Área de registro de acciones
        areaRegistro = new JTextArea();
        areaRegistro.setFont(FUENTE_TEXTO);
        areaRegistro.setEditable(false);
        JScrollPane scrollRegistro = new JScrollPane(areaRegistro);
        panelPrincipal.add(scrollRegistro, BorderLayout.SOUTH);

        // Listeners campo de texto
        campoTexto.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                registrarAccion("Ratón entra en el campo de texto.");
            }
            public void mouseExited(MouseEvent e) {
                registrarAccion("Ratón sale del campo de texto.");
            }
        });

        campoTexto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                registrarAccion("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()));
            }
            public void keyReleased(KeyEvent e) {
                registrarAccion("Tecla liberada: " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        // Listeners área de registro
        panelRegistro.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panelRegistro.setBackground(COLOR_AREA_REGISTRO_ACTIVO);
                registrarAccion("Ratón ha entrado en el área de registro.");
            }
            public void mouseExited(MouseEvent e) {
                panelRegistro.setBackground(COLOR_AREA_REGISTRO_NORMAL);
                registrarAccion("Ratón sale del área de registro.");
            }
        });

        panelRegistro.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                registrarAccion("Ratón movido: X=" + e.getX() + ", Y=" + e.getY());
            }
            public void mouseDragged(MouseEvent e) {
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
    }

    private void registrarAccion(String accion) {
        areaRegistro.append(accion + "\n");
        areaRegistro.setCaretPosition(areaRegistro.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistroAccionesUsuario().setVisible(true);
        });
    }
}