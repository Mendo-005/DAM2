import javax.swing.*;
import java.awt.event.*;

public class EjemploBasico {
    public static void main(String[] args) {
        // 1. Crear el contenedor principal (JFrame) [cite: 57]
        JFrame frame = new JFrame("Control Exacto");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // IMPORTANTE: Para posicionamiento exacto, desactivamos el Layout
        frame.setLayout(null); 

        // 2. Crear Componentes [cite: 328]
        JLabel etiqueta = new JLabel("Introduce tu nombre:");
        JTextField campoTexto = new JTextField();
        JButton boton = new JButton("Saludar"); // [cite: 329]

        // 3. Posicionamiento Exacto: setBounds(x, y, ancho, alto)
        // Colocamos la etiqueta en la coordenada 50, 50
        etiqueta.setBounds(50, 50, 200, 30);
        
        // Colocamos el campo de texto debajo
        campoTexto.setBounds(50, 90, 150, 30);
        
        // Colocamos el botón al lado del texto
        boton.setBounds(210, 90, 100, 30);

        // 4. Añadir evento al botón (ActionListener) [cite: 358, 406]
        // Usamos una clase anónima como explica tu PDF "addActionListener"
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción: Mostrar un diálogo con el texto escrito [cite: 363]
                String nombre = campoTexto.getText();
                JOptionPane.showMessageDialog(frame, "Hola, " + nombre);
            }
        });

        // 5. Añadir componentes al frame y hacer visible
        frame.add(etiqueta);
        frame.add(campoTexto);
        frame.add(boton);
        
        frame.setVisible(true);
    }
}