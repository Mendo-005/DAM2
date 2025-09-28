package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ejercicio5 {
    public static void main(String[] args) {
        new VentanaCambioTexto();
    }
}

class VentanaCambioTexto extends JFrame {
    private JButton boton2;
    private JButton boton3;
    private int contador = 0;
    
    public VentanaCambioTexto() {
        setTitle("Cambiar Texto de Botones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botón contador
        boton2 = new JButton("Contador: 0");
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contador++;
                boton2.setText("Contador: " + contador);
            }
        });
        
        // Botón que cambia de color y texto
        boton3 = new JButton("Cambiar color");
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (boton3.getText().equals("Cambiar color")) {
                    boton3.setText("¡Rojo!");
                    boton3.setBackground(Color.RED);
                    boton3.setForeground(Color.WHITE);
                } else {
                    boton3.setText("Cambiar color");
                    boton3.setBackground(null);
                    boton3.setForeground(Color.BLACK);
                }
            }
        });
        
        panel.add(boton2);
        panel.add(boton3);
        
        add(panel);
        setVisible(true);
    }
}
