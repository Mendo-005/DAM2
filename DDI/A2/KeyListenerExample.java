package A2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class KeyListenerExample extends JFrame implements KeyListener {
    
    private JTextField textField;
    private JLabel label;
    private JLabel contador;
    private int contadorValor = 0;

    public KeyListenerExample() {
        // Configurar el JFrame
        setTitle("Ejemplo de KeyListener");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Crear el campo de texto
        textField = new JTextField(20);
        // El KeyListener se añadirá después de construir el objeto

        // Crear una etiqueta para mostrar las teclas presionadas
        label = new JLabel("Presiona una tecla...");

        // Crea un contador que suma uno cada vez que se trecea o separa una letra
        contador = new JLabel("0");

        add(textField);
        add(label);
        add(contador);
        add(label);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método que se invoca cuando se presiona una tecla
    @Override
    public void keyPressed(KeyEvent e) {
        //label.setText("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    // Método que se invoca cuando se libera una tecla
    @Override
    public void keyReleased(KeyEvent e) {
        //label.setText("Tecla liberada: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    // Método que se invoca cuando se escribe un carácter
    @Override
    public void keyTyped(KeyEvent e) {
        label.setText("Carácter escrito: " + e.getKeyChar());
        contadorValor++;
        contador.setText(String.valueOf(contadorValor));

    }

    public static void main(String[] args) {
        // Crear la ventana del ejemplo
        KeyListenerExample frame = new KeyListenerExample();
        frame.textField.addKeyListener(frame);  // Añadir el KeyListener después de construir el objeto
    }
}

