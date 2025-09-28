package src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import VentanaConfirmacion;
import VentanaMetodosPago;

public class Ejercicio1 {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaConfirmacion());
    }
}

class VentanaConfirmacion extends JFrame {
    
    public VentanaConfirmacion() {
        configurarVentana();
        crearContenido();
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Compra de Entradas");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    private void crearContenido() {
        add(crearPanelInformacion(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titulo = new JLabel("GALA BENÉFICA 2025");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        
        panel.add(Box.createVerticalStrut(10));
        
        JLabel detalles = new JLabel("<html><center>Concierto solidario<br>15 de Octubre, 2025 - 20:00h<br><b>Precio: €35.00</b></center></html>");
        detalles.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(detalles);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                    VentanaConfirmacion.this,
                    "¿Seguro que quiere cancelar?",
                    "Cancelar compra",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        JButton btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMetodosPago(VentanaConfirmacion.this);
            }
        });
        
        panel.add(btnCancelar);
        panel.add(btnPagar);
        
        return panel;
    }
}

class VentanaMetodosPago extends JFrame {
    
    private JRadioButton rbTarjeta, rbEfectivo, rbTransferencia;
    private ButtonGroup grupoMetodos;
    private JFrame ventanaPadre;
    
    public VentanaMetodosPago(JFrame ventanaPadre) {
        this.ventanaPadre = ventanaPadre;
        configurarVentana();
        crearContenido();
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Método de Pago");
        setSize(300, 200);
        setLocationRelativeTo(ventanaPadre);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    private void crearContenido() {
        add(crearOpcionesPago(), BorderLayout.CENTER);
        add(crearBotonesPago(), BorderLayout.SOUTH);
    }
    
    private JPanel crearOpcionesPago() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Seleccione método de pago:");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        
        rbTarjeta = new JRadioButton("Tarjeta de crédito");
        rbTarjeta.setSelected(true);
        
        rbEfectivo = new JRadioButton("Efectivo en taquilla");
        
        rbTransferencia = new JRadioButton("Transferencia bancaria");
        
        grupoMetodos = new ButtonGroup();
        grupoMetodos.add(rbTarjeta);
        grupoMetodos.add(rbEfectivo);
        grupoMetodos.add(rbTransferencia);
        
        panel.add(rbTarjeta);
        panel.add(rbEfectivo);
        panel.add(rbTransferencia);
        
        return panel;
    }
    
    private JPanel crearBotonesPago() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        
        JButton btnConfirmar = new JButton("Confirmar Pago");
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
    
    private void procesarPago() {
        String metodoPago = "";
        
        if (rbTarjeta.isSelected()) {
            metodoPago = "Tarjeta de crédito";
        } else if (rbEfectivo.isSelected()) {
            metodoPago = "Efectivo en taquilla";
        } else if (rbTransferencia.isSelected()) {
            metodoPago = "Transferencia bancaria";
        }
        
        String mensaje = "Pago confirmado!\n\nMétodo: " + metodoPago + "\nPrecio: €35.00";
        JOptionPane.showMessageDialog(this, mensaje, "Compra realizada", JOptionPane.INFORMATION_MESSAGE);
        
        System.out.println("Pago procesado: " + metodoPago + " - €35.00");
        
        dispose();
        ventanaPadre.dispose();
    }
}

