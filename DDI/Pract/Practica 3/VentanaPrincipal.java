import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


// --- CLASE 1: VENTANA PRINCIPAL (Punto de entrada) ---
// [cite: 8, 9] Clase principal con menú y estructura de datos compartida
public class VentanaPrincipal extends JFrame {

    //  Estructura de datos estática para compartir los empleados entre ventanas
    public static List<Empleado> listaEmpleados = new ArrayList<>();

    public VentanaPrincipal() {
        // Configuración de la ventana
        setTitle("Gestión de Empleados - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Gestión de RRHH", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        // [cite: 16] Botones de navegación requeridos
        JButton btnAgregar = new JButton("Agregar Nuevo Empleado");
        JButton btnListar = new JButton("Ver Lista de Empleados");
        JButton btnSalir = new JButton("Salir");

        // Evento: Abrir ventana de Alta
        btnAgregar.addActionListener(e -> {
            // Instanciamos la segunda clase definida abajo
            new VentanaAgregarEmpleado().setVisible(true);
        });

        // Evento: Abrir ventana de Lista
        btnListar.addActionListener(e -> {
            // Instanciamos la tercera clase definida abajo
            new VentanaListaEmpleados().setVisible(true);
        });

        // Evento: Salir
        btnSalir.addActionListener(e -> System.exit(0));

        add(btnAgregar);
        add(btnListar);
        add(btnSalir);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}

// --- CLASE 2: VENTANA DE ALTA ---
// [cite: 10, 11, 12] Ventana para introducir datos, no es 'public' para poder estar en el mismo archivo
class VentanaAgregarEmpleado extends JFrame {

    public VentanaAgregarEmpleado() {
        setTitle("Alta de Empleado");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10)); // [cite: 17] Diseño de formulario

        // Campos de texto
        add(new JLabel("   Nombre: *"));
        JTextField txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("   Apellidos: *"));
        JTextField txtApellidos = new JTextField();
        add(txtApellidos);

        // [cite: 10, 18] Selector de Departamento (JComboBox)
        add(new JLabel("   Departamento:"));
        String[] deptos = {"Ventas", "IT", "Recursos Humanos"};
        JComboBox<String> cmbDepartamento = new JComboBox<>(deptos);
        add(cmbDepartamento);

        // [cite: 26] Campo extra: Fecha (Creatividad)
        add(new JLabel("   Fecha Contratación:"));
        JTextField txtFecha = new JTextField("Hoy");
        add(txtFecha);

        // [cite: 10, 23] Componente JCheckBox Personalizado
        add(new JLabel("   Estado del Empleado:"));
        JCheckBox chkActivo = new JCheckBox("Inactivo");
        chkActivo.setOpaque(true); // Vital para ver el color de fondo
        chkActivo.setBackground(Color.RED);
        chkActivo.setForeground(Color.WHITE);

        // Lógica de cambio de color: Rojo (Inactivo) <-> Verde (Activo)
        chkActivo.addItemListener(e -> {
            if (chkActivo.isSelected()) {
                chkActivo.setBackground(Color.GREEN);
                chkActivo.setForeground(Color.BLACK);
                chkActivo.setText("Activo");
            } else {
                chkActivo.setBackground(Color.RED);
                chkActivo.setForeground(Color.WHITE);
                chkActivo.setText("Inactivo");
            }
        });
        add(chkActivo);

        // Botones de acción
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cerrar");

        btnCancelar.addActionListener(e -> dispose());

        // [cite: 24] Validación y Guardado
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();

            if (nombre.isEmpty() || apellidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Error: Nombre y Apellidos son obligatorios.", 
                    "Validación", JOptionPane.ERROR_MESSAGE);
            } else {
                // Se crea el objeto Empleado (asumiendo que existe la clase Empleado)
                Empleado nuevoEmp = new Empleado(
                    nombre,
                    apellidos,
                    (String) cmbDepartamento.getSelectedItem(),
                    chkActivo.isSelected(),
                    txtFecha.getText()
                );

                //  Guardar en el ArrayList estático de la ventana principal
                VentanaPrincipal.listaEmpleados.add(nuevoEmp);
                
                JOptionPane.showMessageDialog(this, "¡Empleado guardado con éxito!");
                dispose(); // Cerramos la ventana al guardar
            }
        });

        add(btnGuardar);
        add(btnCancelar);
    }
}

// --- CLASE 3: VENTANA DE LISTA ---
// [cite: 13, 14] Ventana para visualizar los datos
class VentanaListaEmpleados extends JFrame {

    public VentanaListaEmpleados() {
        setTitle("Listado de Personal");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // [cite: 13] Área de texto para mostrar la lista
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaTexto.setMargin(new Insets(10, 10, 10, 10));

        // Construcción del reporte
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE EMPLEADOS ===\n\n");
        
        if (VentanaPrincipal.listaEmpleados.isEmpty()) {
            sb.append("No hay empleados registrados en el sistema.");
        } else {
            for (Empleado emp : VentanaPrincipal.listaEmpleados) {
                sb.append(emp.toString()).append("\n");
                sb.append("-----------------------------------------\n");
            }
        }
        areaTexto.setText(sb.toString());

        // ScrollPane por si la lista es larga
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Botón inferior
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(e -> dispose());
        add(btnVolver, BorderLayout.SOUTH);
    }
}