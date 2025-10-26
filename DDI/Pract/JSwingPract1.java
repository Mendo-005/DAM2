/*
 * PROYECTO COMPLETO JAVA SWING - APUNTES PARA EXAMEN
 * Incluye: Botones, Paneles, Layouts, Colores, Eventos, Components avanzados
 */

// Importaciones básicas
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

/*
 * Clase principal del proyecto Swing
 */
public class JSwingPract1 extends JFrame implements ActionListener, KeyListener {
    
    // DECLARACIÓN DE COMPONENTES (importante para el examen)
    private JButton btnFlowLayout, btnBorderLayout, btnGridLayout, btnAbsolute;
    private JButton btnColores, btnEventos, btnTabla, btnLista;
    private JPanel panelPrincipal, panelSecundario;
    private JTextField txtNombre, txtApellido;
    private JTextArea txtArea;
    private JLabel lblTitulo, lblInfo;
    private JComboBox<String> comboColores;
    private JCheckBox chkOpcion1, chkOpcion2;
    private JRadioButton radioMasculino, radioFemenino;
    private ButtonGroup grupoGenero;
    private JTable tabla;
    private JList<String> lista;
    private JScrollPane scrollTabla, scrollLista, scrollArea;
    // Registro de teclas
    private JTextArea txtKeyLog;
    private JScrollPane scrollKeyLog;
    
    // CONSTRUCTOR (patrón importante para el examen)
    public JSwingPract1() {
        super("Proyecto Completo Java Swing - Apuntes Examen");
        inicializarComponentes();
        configurarVentana();
        crearEventos();
        // Cuando la ventana se abra, pedimos el foco en el panel principal para capturar teclas
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panelPrincipal.requestFocusInWindow();
            }
        });
    }
    
    // MÉTODO PARA INICIALIZAR COMPONENTES
    private void inicializarComponentes() {
        // 1. PANELES (contenedores)
        panelPrincipal = new JPanel(new BorderLayout());
        panelSecundario = new JPanel(new FlowLayout());
        
        // 2. ETIQUETAS (JLabel)
        lblTitulo = new JLabel("PROYECTO SWING COMPLETO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.BLUE);
        
        lblInfo = new JLabel("Información: ");
        
        // 3. CAMPOS DE TEXTO (JTextField y JTextArea)
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        txtArea = new JTextArea(5, 30);
        txtArea.setText("Área de texto...\nPuedes escribir varias líneas aquí");
        scrollArea = new JScrollPane(txtArea);
        
        // 4. BOTONES (JButton) - Diferentes layouts
        btnFlowLayout = new JButton("FlowLayout");
        btnBorderLayout = new JButton("BorderLayout");
        btnGridLayout = new JButton("GridLayout");
        btnAbsolute = new JButton("Sin Layout");
        
        btnColores = new JButton("Cambiar Colores");
        btnEventos = new JButton("Mostrar Eventos");
        btnTabla = new JButton("Mostrar Tabla");
        btnLista = new JButton("Mostrar Lista");
        
        // 5. COMBO BOX (JComboBox)
        String[] colores = {"Rojo", "Verde", "Azul", "Amarillo", "Rosa"};
        comboColores = new JComboBox<>(colores);
        
        // 6. CHECKBOXES (JCheckBox)
        chkOpcion1 = new JCheckBox("Opción 1");
        chkOpcion2 = new JCheckBox("Opción 2");
        
        // 7. RADIO BUTTONS (JRadioButton)
        radioMasculino = new JRadioButton("Masculino");
        radioFemenino = new JRadioButton("Femenino");
        grupoGenero = new ButtonGroup();
        grupoGenero.add(radioMasculino);
        grupoGenero.add(radioFemenino);
        
        // 8. TABLA (JTable)
        String[] columnas = {"Nombre", "Apellido", "Edad", "Ciudad"};
        Object[][] datos = {
            {"Juan", "Pérez", 25, "Madrid"},
            {"Ana", "García", 30, "Barcelona"},
            {"Luis", "Martín", 28, "Valencia"}
        };
        tabla = new JTable(new DefaultTableModel(datos, columnas));
        scrollTabla = new JScrollPane(tabla);
        
        // 9. LISTA (JList)
        String[] elementos = {"Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4"};
        lista = new JList<>(elementos);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollLista = new JScrollPane(lista);

        // 10. ÁREA DE REGISTRO DE TECLAS
        txtKeyLog = new JTextArea(6, 40);
        txtKeyLog.setEditable(false);
        txtKeyLog.setBorder(BorderFactory.createTitledBorder("Registro de teclas (presionar y soltar)"));
        scrollKeyLog = new JScrollPane(txtKeyLog);
    }
    
    // MÉTODO PARA CONFIGURAR LA VENTANA
    private void configurarVentana() {
        // Configuración básica de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar ventana
        setResizable(true);
        
        // Layout principal: BorderLayout
        setLayout(new BorderLayout());
        
        // NORTE: Título
        add(lblTitulo, BorderLayout.NORTH);
        
        // CENTRO: Panel principal con componentes
        crearPanelCentral();
        add(panelPrincipal, BorderLayout.CENTER);
        
        // SUR: Botones de acción
        crearPanelSur();
    }
    
    // MÉTODO PARA CREAR PANEL CENTRAL (IMPORTANTE: Diferentes layouts)
    private void crearPanelCentral() {
        panelPrincipal.setLayout(new BorderLayout());
        
        // Panel OESTE: Formulario
        JPanel panelOeste = new JPanel(new GridLayout(8, 2, 5, 5));
        panelOeste.setBorder(BorderFactory.createTitledBorder("Formulario"));
        panelOeste.setBackground(Color.LIGHT_GRAY);
        
        panelOeste.add(new JLabel("Nombre:"));
        panelOeste.add(txtNombre);
        panelOeste.add(new JLabel("Apellido:"));
        panelOeste.add(txtApellido);
        panelOeste.add(new JLabel("Color:"));
        panelOeste.add(comboColores);
        panelOeste.add(chkOpcion1);
        panelOeste.add(chkOpcion2);
        panelOeste.add(radioMasculino);
        panelOeste.add(radioFemenino);
        panelOeste.add(new JLabel("Layouts:"));
        
        // Panel para botones de layout
        JPanel panelLayouts = new JPanel(new FlowLayout());
        panelLayouts.add(btnFlowLayout);
        panelLayouts.add(btnBorderLayout);
        panelOeste.add(panelLayouts);
        
        panelOeste.add(btnGridLayout);
        panelOeste.add(btnAbsolute);
        
        panelPrincipal.add(panelOeste, BorderLayout.WEST);
        
        // Panel CENTRO: Área de texto
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createTitledBorder("Área de Texto"));
        panelCentro.add(scrollArea, BorderLayout.CENTER);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        
        // Panel ESTE: Tabla y Lista
        JPanel panelEste = new JPanel(new GridLayout(2, 1, 5, 5));
        panelEste.setBorder(BorderFactory.createTitledBorder("Datos"));
        panelEste.add(scrollTabla);
        panelEste.add(scrollLista);
        panelPrincipal.add(panelEste, BorderLayout.EAST);
    }
    
    // MÉTODO PARA CREAR PANEL SUR
    private void crearPanelSur() {
        JPanel panelSur = new JPanel(new FlowLayout());
        panelSur.setBackground(Color.CYAN);
        
        panelSur.add(lblInfo);
        panelSur.add(btnColores);
        panelSur.add(btnEventos);
        panelSur.add(btnTabla);
        panelSur.add(btnLista);
        // Añadimos el registro de teclas en el sur para ver en tiempo real
        panelSur.add(scrollKeyLog);
        
        add(panelSur, BorderLayout.SOUTH);
    }
    
    // MÉTODO PARA CREAR EVENTOS (ActionListener)
    private void crearEventos() {
        // Agregar ActionListener a todos los botones
        btnFlowLayout.addActionListener(this);
        btnBorderLayout.addActionListener(this);
        btnGridLayout.addActionListener(this);
        btnAbsolute.addActionListener(this);
        btnColores.addActionListener(this);
        btnEventos.addActionListener(this);
        btnTabla.addActionListener(this);
        btnLista.addActionListener(this);
        
        // Eventos específicos
        comboColores.addActionListener(this);
        chkOpcion1.addActionListener(this);
        chkOpcion2.addActionListener(this);

        // Registrar KeyListener en el panel principal para capturar pulsaciones globales
        panelPrincipal.setFocusable(true);
        panelPrincipal.addKeyListener(this);
    }
    
    // IMPLEMENTACIÓN DE ActionListener (OBLIGATORIO para eventos)
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        
        // EVENTOS DE LAYOUTS
        if (fuente == btnFlowLayout) {
            mostrarLayoutDemo("FlowLayout", new FlowLayout());
        } else if (fuente == btnBorderLayout) {
            mostrarLayoutDemo("BorderLayout", new BorderLayout());
        } else if (fuente == btnGridLayout) {
            mostrarLayoutDemo("GridLayout", new GridLayout(2, 2));
        } else if (fuente == btnAbsolute) {
            mostrarLayoutDemo("Sin Layout (null)", null);
        }
        
        // EVENTOS DE COLORES
        else if (fuente == btnColores) {
            cambiarColores();
        } else if (fuente == comboColores) {
            String colorSeleccionado = (String) comboColores.getSelectedItem();
            lblInfo.setText("Color seleccionado: " + colorSeleccionado);
        }
        
        // EVENTOS DE INFORMACIÓN
        else if (fuente == btnEventos) {
            mostrarInformacionEventos();
        } else if (fuente == btnTabla) {
            mostrarInformacionTabla();
        } else if (fuente == btnLista) {
            mostrarInformacionLista();
        }
        
        // EVENTOS DE CHECKBOXES
        else if (fuente == chkOpcion1 || fuente == chkOpcion2) {
            actualizarCheckboxes();
        }
    }

    // IMPLEMENTACIÓN DE KeyListener para registrar teclas presionadas y soltadas
    @Override
    public void keyPressed(KeyEvent e) {
        String texto = "Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()) + " (code=" + e.getKeyCode() + ")";
        txtKeyLog.append(texto + "\n");
        txtKeyLog.setCaretPosition(txtKeyLog.getDocument().getLength());
        lblInfo.setText(texto);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String texto = "Tecla soltada: " + KeyEvent.getKeyText(e.getKeyCode()) + " (code=" + e.getKeyCode() + ")";
        txtKeyLog.append(texto + "\n");
        txtKeyLog.setCaretPosition(txtKeyLog.getDocument().getLength());
        lblInfo.setText(texto);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // keyTyped entrega el carácter (si existe)
        char c = e.getKeyChar();
        String texto = "Tecla tipeada: '" + c + "'";
        txtKeyLog.append(texto + "\n");
        txtKeyLog.setCaretPosition(txtKeyLog.getDocument().getLength());
        lblInfo.setText(texto);
    }
    
    // MÉTODOS PARA DEMOSTRAR LAYOUTS
    private void mostrarLayoutDemo(String nombreLayout, LayoutManager layout) {
        JFrame ventanaDemo = new JFrame("Demo: " + nombreLayout);
        ventanaDemo.setSize(400, 300);
        ventanaDemo.setLocationRelativeTo(this);
        
        if (layout != null) {
            ventanaDemo.setLayout(layout);
        } else {
            ventanaDemo.setLayout(null); // Layout absoluto
        }
        
        // Agregar componentes según el layout
        if (layout instanceof FlowLayout) {
            ventanaDemo.add(new JButton("Botón 1"));
            ventanaDemo.add(new JButton("Botón 2"));
            ventanaDemo.add(new JButton("Botón 3"));
        } else if (layout instanceof BorderLayout) {
            ventanaDemo.add(new JButton("NORTH"), BorderLayout.NORTH);
            ventanaDemo.add(new JButton("SOUTH"), BorderLayout.SOUTH);
            ventanaDemo.add(new JButton("CENTER"), BorderLayout.CENTER);
            ventanaDemo.add(new JButton("WEST"), BorderLayout.WEST);
            ventanaDemo.add(new JButton("EAST"), BorderLayout.EAST);
        } else if (layout instanceof GridLayout) {
            ventanaDemo.add(new JButton("1"));
            ventanaDemo.add(new JButton("2"));
            ventanaDemo.add(new JButton("3"));
            ventanaDemo.add(new JButton("4"));
        } else {
            // Layout absoluto
            JButton btn1 = new JButton("Botón 1");
            btn1.setBounds(50, 50, 100, 30);
            JButton btn2 = new JButton("Botón 2");
            btn2.setBounds(200, 100, 100, 30);
            ventanaDemo.add(btn1);
            ventanaDemo.add(btn2);
        }
        
        ventanaDemo.setVisible(true);
    }
    
    // MÉTODO PARA CAMBIAR COLORES
    private void cambiarColores() {
        Color colorFondo = new Color((int)(Math.random() * 256), 
                                   (int)(Math.random() * 256), 
                                   (int)(Math.random() * 256));
        panelSecundario.setBackground(colorFondo);
        
        Color colorTexto = colorFondo.getRed() > 128 ? Color.BLACK : Color.WHITE;
        lblTitulo.setForeground(colorTexto);
        
        lblInfo.setText("Colores cambiados - RGB(" + colorFondo.getRed() + 
                       ", " + colorFondo.getGreen() + ", " + colorFondo.getBlue() + ")");
    }
    
    // MÉTODO PARA MOSTRAR INFORMACIÓN DE EVENTOS
    private void mostrarInformacionEventos() {
        String info = "INFORMACIÓN DE EVENTOS:\n\n";
        info += "Nombre: " + txtNombre.getText() + "\n";
        info += "Apellido: " + txtApellido.getText() + "\n";
        info += "Color: " + comboColores.getSelectedItem() + "\n";
        info += "Opción 1: " + (chkOpcion1.isSelected() ? "Seleccionada" : "No seleccionada") + "\n";
        info += "Opción 2: " + (chkOpcion2.isSelected() ? "Seleccionada" : "No seleccionada") + "\n";
        info += "Género: " + (radioMasculino.isSelected() ? "Masculino" : 
                             radioFemenino.isSelected() ? "Femenino" : "No seleccionado");
        
        JOptionPane.showMessageDialog(this, info, "Información de Eventos", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    // MÉTODO PARA MOSTRAR INFORMACIÓN DE TABLA
    private void mostrarInformacionTabla() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) tabla.getValueAt(filaSeleccionada, 0);
            String apellido = (String) tabla.getValueAt(filaSeleccionada, 1);
            lblInfo.setText("Seleccionado de tabla: " + nombre + " " + apellido);
        } else {
            lblInfo.setText("No hay fila seleccionada en la tabla");
        }
    }
    
    // MÉTODO PARA MOSTRAR INFORMACIÓN DE LISTA
    private void mostrarInformacionLista() {
        String elementoSeleccionado = lista.getSelectedValue();
        if (elementoSeleccionado != null) {
            lblInfo.setText("Seleccionado de lista: " + elementoSeleccionado);
        } else {
            lblInfo.setText("No hay elemento seleccionado en la lista");
        }
    }
    
    // MÉTODO PARA ACTUALIZAR CHECKBOXES
    private void actualizarCheckboxes() {
        String estado = "Checkboxes - ";
        estado += "Op1: " + (chkOpcion1.isSelected() ? "✓" : "✗") + " ";
        estado += "Op2: " + (chkOpcion2.isSelected() ? "✓" : "✗");
        lblInfo.setText(estado);
    }
    
    // MÉTODO MAIN (punto de entrada)
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar la ventana en el hilo de eventos
        SwingUtilities.invokeLater(() -> {
            new JSwingPract1().setVisible(true);
        });
    }
}