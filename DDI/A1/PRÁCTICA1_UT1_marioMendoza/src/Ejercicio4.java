package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ejercicio4 {
    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}

// Ventana principal para capturar datos
class VentanaPrincipal extends JFrame {
    private JTextField campoNombre;
    private JTextField campoApellidos;
    
    public VentanaPrincipal() {
        setTitle("Formulario de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Etiquetas y campos
        panel.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panel.add(campoNombre);
        
        panel.add(new JLabel("Apellidos:"));
        campoApellidos = new JTextField();
        panel.add(campoApellidos);
        
        // Botón enviar
        panel.add(new JLabel()); // espacio vacío
        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear ventana de confirmación
                JFrame ventana = new JFrame("Confirmación");
                ventana.setSize(200, 120);
                ventana.setLocationRelativeTo(null);
                
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new JLabel("Datos enviados", JLabel.CENTER), BorderLayout.CENTER);
                
                JButton btnCerrar = new JButton("Cerrar");
                btnCerrar.addActionListener(event -> ventana.dispose());
                panel.add(btnCerrar, BorderLayout.SOUTH);
                
                ventana.add(panel);
                ventana.setVisible(true);
            }
        });
        panel.add(botonEnviar);
        
        add(panel);
        setVisible(true);
    }
}