import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
//import javax.swing.border.LineBorder;


public class RegistroAccionesUsuario extends JFrame {

    private static final Color COLOR_FONDO_DEFAULT = new Color(236, 240, 241);
    private static final Color COLOR_FONDO_HOVER = new Color(214, 234, 248);
    private static final Color COLOR_AZUL_BOTON = new Color(52, 152, 219);
    private static final Color COLOR_VERDE_CAMPO = new Color(220, 255, 220);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_CAMPO_TEXTO = new Font("Consolas", Font.PLAIN, 14);

    private JPanel panelPrincipal;
    private JTextField campoTexto;
    private JButton botonEnviar;
    private JTextArea areaRegistro;

    public RegistroAccionesUsuario() {
        configurarVentana();
        inicializarComponentes();
        asignarListeners();
    }

    private void configurarVentana() {
        setTitle("Registro de acciones del usuario");
        setSize(520, 420);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(COLOR_FONDO_DEFAULT);
        this.setContentPane(panelPrincipal);

        JLabel labelTitulo = new JLabel("Registro de Interacción");
        labelTitulo.setFont(FONT_TITULO);
        labelTitulo.setForeground(COLOR_AZUL_BOTON); // Color azul para el título.
        labelTitulo.setBounds(150, 10, 300, 30); // Centrado visualmente.
        panelPrincipal.add(labelTitulo);

        campoTexto = new JTextField();
        campoTexto.setFont(FONT_CAMPO_TEXTO);
        campoTexto.setBackground(COLOR_VERDE_CAMPO); // Fondo verde claro.
        campoTexto.setBorder(BorderFactory.createLineBorder(new Color(170, 170, 170))); // Borde gris.
        campoTexto.setBounds(20, 50, 350, 40);
        panelPrincipal.add(campoTexto);

        botonEnviar = new JButton("Enviar");
        botonEnviar.setBounds(380, 50, 100, 40);
        botonEnviar.setBackground(COLOR_AZUL_BOTON); // Fondo azul.
        botonEnviar.setForeground(Color.WHITE); // Texto blanco.
        botonEnviar.setFocusPainted(false);
        botonEnviar.setBorder(null); // Sin borde para un look más plano.
        panelPrincipal.add(botonEnviar);

        areaRegistro = new JTextArea();
        areaRegistro.setEditable(false);
        areaRegistro.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(areaRegistro);
        scrollPane.setBounds(20, 110, 460, 250);
        panelPrincipal.add(scrollPane);
    }

    private void asignarListeners() {
        // Evento del botón para capturar el texto.
        botonEnviar.addActionListener(e -> {
            String texto = campoTexto.getText();
            registrarAccion("Botón presionado. Texto actual: " + texto);
        });

        // Eventos de ratón para el campo de texto.
        campoTexto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registrarAccion("Ratón entra en el campo de texto.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registrarAccion("Ratón sale del campo de texto.");
            }
        });

        // Eventos de teclado para el campo de texto.
        campoTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                registrarAccion("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                registrarAccion("Tecla liberada: " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        // Eventos de ratón para el panel principal.
        panelPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panelPrincipal.setBackground(COLOR_FONDO_HOVER);
                registrarAccion("Ratón entra en el área de registro.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Evita que el color cambie si el ratón se mueve a un componente hijo.
                if (!panelPrincipal.getBounds().contains(e.getPoint())) {
                    panelPrincipal.setBackground(COLOR_FONDO_DEFAULT);
                    registrarAccion("Ratón sale del área de registro.");
                }
            }
        });

        // Eventos de movimiento y arrastre del ratón.
        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                registrarAccion("Ratón movido: X=" + e.getX() + ", Y=" + e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                registrarAccion("Ratón arrastrado: X=" + e.getX() + ", Y=" + e.getY());
            }
        });
    }

    private void registrarAccion(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            areaRegistro.append(mensaje + "\n");
            areaRegistro.setCaretPosition(areaRegistro.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroAccionesUsuario().setVisible(true));
    }
}