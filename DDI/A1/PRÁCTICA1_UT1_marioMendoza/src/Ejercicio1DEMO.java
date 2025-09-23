package src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.UIManager;

/**
 * Ejercicio 1 - Conexión entre ventanas para compra de entradas
 * Esta clase implementa dos ventanas conectadas:
 * 1. Ventana de confirmación de compra con botones "Cancelar" y "Pagar"
 * 2. Ventana de selección de método de pago con JRadioButtons
 * 
 * @author Mario Mendoza
 */
public class Ejercicio1DEMO {
    
    public static void main(String[] args) {
        // Crear y mostrar la ventana principal de confirmación
        SwingUtilities.invokeLater(() -> new VentanaConfirmacion());
    }
}

/**
 * Primera ventana: Confirmación de compra de entradas para gala benéfica
 */
class VentanaConfirmacion extends JFrame {
    
    public VentanaConfirmacion() {
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Configuración básica de la ventana
        setTitle("Compra de Entradas - Gala Benéfica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);
        
        // Panel principal con BorderLayout
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con información
        JPanel panelInfo = createInfoPanel();
        add(panelInfo, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = createButtonPanel();
        add(panelBotones, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * Crea el panel con la información de la gala benéfica
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título principal
        JLabel titulo = new JLabel("GALA BENÉFICA 2025");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);
        
        // Información del evento
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("Concierto solidario"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("15 de Octubre, 2025"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Hora:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("20:00 hrs"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        JLabel precio = new JLabel("€35.00");
        precio.setFont(new Font("Arial", Font.BOLD, 14));
        precio.setForeground(Color.BLUE);
        panel.add(precio, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel con los botones de acción
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirmar cancelación
                int opcion = JOptionPane.showConfirmDialog(
                    VentanaConfirmacion.this,
                    "¿Está seguro de que desea cancelar la compra?",
                    "Confirmar cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (opcion == JOptionPane.YES_OPTION) {
                    System.out.println("Compra cancelada por el usuario");
                    System.exit(0);
                }
            }
        });
        
        // Botón Pagar - AQUÍ SE ASOCIA EL EVENT LISTENER PRINCIPAL
        JButton btnPagar = new JButton("Pagar");
        btnPagar.setPreferredSize(new Dimension(100, 35));
        btnPagar.setBackground(new Color(34, 139, 34));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir ventana de métodos de pago
                new VentanaMetodosPago(VentanaConfirmacion.this);
            }
        });
        
        panel.add(btnCancelar);
        panel.add(btnPagar);
        
        return panel;
    }
}

/**
 * Segunda ventana: Selección de método de pago
 */
class VentanaMetodosPago extends JFrame {
    
    private JRadioButton rbTarjeta, rbEfectivo, rbTransferencia;
    private ButtonGroup grupoMetodos;
    private JFrame ventanaPadre;
    
    public VentanaMetodosPago(JFrame ventanaPadre) {
        this.ventanaPadre = ventanaPadre;
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Configuración básica de la ventana
        setTitle("Método de Pago");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(ventanaPadre); // Centrar respecto a la ventana padre
        setResizable(false);
        
        // Layout principal
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con título
        JPanel panelTitulo = createTitlePanel();
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con opciones de pago
        JPanel panelOpciones = createPaymentOptionsPanel();
        add(panelOpciones, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = createPaymentButtonPanel();
        add(panelBotones, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * Crea el panel del título
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        
        JLabel titulo = new JLabel("Seleccione su método de pago");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titulo);
        return panel;
    }
    
    /**
     * Crea el panel con las opciones de pago usando JRadioButton
     */
    private JPanel createPaymentOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 10, 8, 10);
        
        // Crear los JRadioButton para los métodos de pago
        rbTarjeta = new JRadioButton("💳 Tarjeta de crédito/débito");
        rbTarjeta.setSelected(true); // Opción por defecto
        
        rbEfectivo = new JRadioButton("💵 Efectivo (pago en taquilla)");
        
        rbTransferencia = new JRadioButton("🏦 Transferencia bancaria");
        
        // Agrupar los radio buttons (solo uno seleccionable)
        grupoMetodos = new ButtonGroup();
        grupoMetodos.add(rbTarjeta);
        grupoMetodos.add(rbEfectivo);
        grupoMetodos.add(rbTransferencia);
        
        // Añadir al panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(rbTarjeta, gbc);
        
        gbc.gridy = 1;
        panel.add(rbEfectivo, gbc);
        
        gbc.gridy = 2;
        panel.add(rbTransferencia, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel con los botones de acción
     */
    private JPanel createPaymentButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
        
        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(90, 30));
        btnVolver.addActionListener(e -> dispose()); // Cerrar esta ventana
        
        // Botón Confirmar Pago
        JButton btnConfirmar = new JButton("Confirmar Pago");
        btnConfirmar.setPreferredSize(new Dimension(130, 30));
        btnConfirmar.setBackground(new Color(34, 139, 34));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarPago();
            }
        });
        
        panel.add(btnVolver);
        panel.add(btnConfirmar);
        
        return panel;
    }
    
    /**
     * Procesa el pago según el método seleccionado
     */
    private void procesarPago() {
        String metodoSeleccionado = "";
        String mensaje = "";
        
        // Determinar qué método de pago está seleccionado
        if (rbTarjeta.isSelected()) {
            metodoSeleccionado = "Tarjeta de crédito/débito";
            mensaje = "Redirigiendo a la pasarela de pago segura...";
        } else if (rbEfectivo.isSelected()) {
            metodoSeleccionado = "Efectivo";
            mensaje = "Puede recoger sus entradas en taquilla el día del evento.";
        } else if (rbTransferencia.isSelected()) {
            metodoSeleccionado = "Transferencia bancaria";
            mensaje = "Se le enviarán los datos bancarios por email.";
        }
        
        // Mostrar confirmación
        String mensajeCompleto = String.format(
            "Método de pago seleccionado: %s\n\n%s\n\n¡Gracias por su compra!",
            metodoSeleccionado, mensaje
        );
        
        JOptionPane.showMessageDialog(
            this,
            mensajeCompleto,
            "Pago Procesado",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Registrar en consola
        System.out.println("=== PAGO PROCESADO ===");
        System.out.println("Método: " + metodoSeleccionado);
        System.out.println("Cantidad: €35.00");
        System.out.println("Estado: Confirmado");
        
        // Cerrar ambas ventanas
        dispose();
        ventanaPadre.dispose();
    }
}

