import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EjercicioVentanas01 {
    public static void main(String[] args) {
        
        // Creamos la ventana mediante JFrame
        JFrame frame = new JFrame("Ejercicio 01");

        // Añadimos un Layout para estructurar los elementos de la aplicación
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Tamaño de la ventana
        frame.setSize(1080, 720);
        // Cerrar las pestañas (EXIT_ON_CLOSE)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // El layout del JFrame a FlowLayout
        // frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        // FlowLayout.CENTER: Coloca los componentes centrados
        // 20: Espacio horizontal entre componentes
        // 15: Espacio vertical entre componentes

        // Configurar el GridLayout: 3 filas y 2 columnas
        //frame.setLayout(new GridLayout(3, 2, 10, 10));
        // 10 es el espacio horizontal y vertical entre las celdas

        JPanel mainPanel = new JPanel(new GridLayout(1,2,10,10));
        // Añadimos los botones
        JButton buttonSanchez = new JButton("Pedro Sanchez");
        JButton buttonGerard = new JButton("Gerard Martín");

        JPanel secPanel =new JPanel(new GridLayout(1,3,10,10));
        // Añadimos la caja de texto
        JTextField electionField = new JTextField(20);
        // Añadimos los botones de enviar y borrar voto
        JButton buttonDelete = new JButton("Borrar");
        JButton buttonSend = new JButton("Enviar");
        
        buttonSanchez.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electionField.setText("Pedro Sanchez");
            }
        });
        buttonGerard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electionField.setText("Gerard Martín");
            }
        });
        
        //Añadimos los elementos a la ventana
        mainPanel.add(buttonSanchez);
        mainPanel.add(buttonGerard);
        frame.add(mainPanel);

        secPanel.add(electionField);
        secPanel.add(buttonDelete);
        secPanel.add(buttonSend);
        frame.add(secPanel);
        //Visibilidad de la ventana ¡Importante!
        frame.setVisible(true);
    }
}

/* Solución

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ventanaPrueba {
    public static void main(String[] args) {
        
        // Creamos la ventana mediante JFrame
        JFrame frame = new JFrame("Ejercicio 01");

        // Añadimos un Layout para estructurar los elementos de la aplicación
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Tamaño de la ventana
        frame.setSize(1080, 720);
        // Cerrar las pestañas (EXIT_ON_CLOSE)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // El layout del JFrame a FlowLayout
        // frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        // FlowLayout.CENTER: Coloca los componentes centrados
        // 20: Espacio horizontal entre componentes
        // 15: Espacio vertical entre componentes

        // Configurar el GridLayout: 3 filas y 2 columnas
        //frame.setLayout(new GridLayout(3, 2, 10, 10));
        // 10 es el espacio horizontal y vertical entre las celdas

        JPanel mainPanel = new JPanel(new GridLayout(1,2,10,10));
        // Añadimos los botones
        JButton buttonSanchez = new JButton("Pedro Sanchez");
        JButton buttonGerard = new JButton("Gerard Martín");

        JPanel secPanel =new JPanel(new GridLayout(1,3,10,10));
        // Añadimos la caja de texto
        JTextField electionField = new JTextField(20);
        // Añadimos los botones de enviar y borrar voto
        JButton buttonDelete = new JButton("Borrar");
        JButton buttonSend = new JButton("Enviar");
        
        buttonSanchez.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electionField.setText("Pedro Sanchez");
            }
        });
        buttonGerard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electionField.setText("Gerard Martín");
            }
        });
        
        // 1. Agrupamos los componentes en sus respectivos paneles
        mainPanel.add(buttonSanchez);
        mainPanel.add(buttonGerard);

        secPanel.add(electionField);
        secPanel.add(buttonDelete);
        secPanel.add(buttonSend);

        // 2. Creamos un panel contenedor para apilar los otros dos verticalmente
        JPanel verticalContainer = new JPanel(new GridLayout(2, 1, 0, 10)); // 2 filas, 1 columna, 10px de espacio vertical
        verticalContainer.add(mainPanel);
        verticalContainer.add(secPanel);

        // 3. Añadimos el contenedor principal a la ventana. 
        // Como la ventana tiene FlowLayout.CENTER, centrará nuestro contenedor.
        frame.add(verticalContainer);

        //Visibilidad de la ventana ¡Importante!
        frame.setVisible(true);
    }
}


 */


