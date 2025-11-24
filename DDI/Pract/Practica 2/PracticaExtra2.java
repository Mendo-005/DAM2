import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class PracticaExtra2 extends JFrame {

    public PracticaExtra2() {
        setTitle("Gestión de clientes");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLocationRelativeTo(null); // Centrar en pantalla

        JButton botonRegistrar = new JButton("Registrar Nuevo Cliente");
        
        // Usamos expresión lambda para simplificar el código
        botonRegistrar.addActionListener(e -> abrirVentanaRegistro());

        // Panel simple para centrar el botón
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(botonRegistrar);
        add(panel);
    }

    private void abrirVentanaRegistro() {
        // Pasamos 'this' para que se centre respecto a la principal
        DialogoRegistro dialogo = new DialogoRegistro(this);
        dialogo.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PracticaExtra2().setVisible(true));
    }
}

// --- CLASE PARA LA VENTANA SECUNDARIA (JDialog) ---
class DialogoRegistro extends JDialog {

    private JTextField txtNombre, txtCorreo;
    private JComboBox<String> comboTipos;
    private JCheckBox chkAcepto;
    private JButton btnConfirmar;
    private JLabel lblMensaje;

    public DialogoRegistro(JFrame owner) {
        super(owner, "Registro de Cliente", true); // 'true' para que sea Modal (bloquea la de atrás)
        setSize(500, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10)); // Espacio entre zonas

        inicializarComponentes();
        
        // Ejecutamos la validación una vez al inicio para deshabilitar el botón
        comprobarValidaciones();
    }

    private void inicializarComponentes() {
        // 1. PANEL CENTRAL (Formulario)
        // Usamos GridLayout(3, 2) -> 3 filas, 2 columnas
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 20));
        panelFormulario.setBorder(new EmptyBorder(20, 20, 0, 20)); // Margen interno

        // Etiquetas y Campos
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        agregarEventosCampo(txtNombre); // Método auxiliar para eventos
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField();
        agregarEventosCampo(txtCorreo);
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Tipo de Cliente:"));
        String[] tipos = {"Regular", "Premium", "VIP"};
        comboTipos = new JComboBox<>(tipos);
        panelFormulario.add(comboTipos);

        add(panelFormulario, BorderLayout.CENTER);

        // 2. PANEL SUR (Acciones)
        // Usamos GridLayout(3, 1) -> 3 filas, 1 columna
        JPanel panelSur = new JPanel(new GridLayout(3, 1, 10, 10));
        panelSur.setBorder(new EmptyBorder(10, 20, 20, 20));

        chkAcepto = new JCheckBox("Acepto los términos y condiciones");
        // Evento al marcar/desmarcar
        chkAcepto.addActionListener(e -> comprobarValidaciones());
        panelSur.add(chkAcepto);

        btnConfirmar = new JButton("Confirmar Registro");
        btnConfirmar.addActionListener(e -> confirmacionPresionado(btnConfirmar));
        btnConfirmar.setBackground(Color.GREEN); // Color verde como en la foto
        btnConfirmar.setForeground(Color.BLACK);
        btnConfirmar.setEnabled(false); // Nace desactivado
        panelSur.add(btnConfirmar);

        lblMensaje = new JLabel("Debe aceptar los términos para continuar...", SwingConstants.CENTER);
        //lblMensaje.setForeground(Color.RED); // Opcional: Texto rojo para errores
        panelSur.add(lblMensaje);

        add(panelSur, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE LÓGICA Y EVENTOS ---

    // Agrega el color amarillo al hacer foco y detecta cuando escribes
    private void agregarEventosCampo(JTextField campo) {
        // 1. Color Amarillo al hacer click (Focus)
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBackground(Color.YELLOW);
            }

            @Override
            public void focusLost(FocusEvent e) {
                campo.setBackground(Color.WHITE);
            }
        });

        // 2. Validar cada vez que se escribe una tecla (Key release)
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                comprobarValidaciones();
            }
        });
    }

    // El cerebro de la ventana: Decide si el botón se activa
    private void comprobarValidaciones() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        boolean isTyCAceptado = chkAcepto.isSelected();

        // 1. Lógica del mensaje inferior
        if (!isTyCAceptado) {
            lblMensaje.setText("Debe aceptar los términos para continuar...");
        } else {
            lblMensaje.setText(""); // Borrar mensaje si aceptó
        }

        // 2. Lógica del botón confirmar
        // Debe haber texto en nombre Y correo Y haber aceptado TyC
        boolean datosValidos = !nombre.isEmpty() && !correo.isEmpty() && isTyCAceptado;

        // Validación extra (opcional): que el correo tenga '@'
        if (datosValidos && !correo.contains("@")) {
            lblMensaje.setText("Formato de correo erroneo...");
        }

        btnConfirmar.setEnabled(datosValidos);
    }

    private void confirmacionPresionado(JButton buttonConfirmado){
        buttonConfirmado.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               lblMensaje.setText("Cliente registrado: " + txtNombre.getText()+ ", "+ comboTipos.getSelectedItem());
            }
            
        });
    }
}