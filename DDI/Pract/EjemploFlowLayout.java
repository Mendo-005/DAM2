import javax.swing.*;
import java.awt.*;

public class EjemploFlowLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo FlowLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Configuración según UT1.2: Alineación CENTRADA, 20px horizontal, 15px vertical
        //
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

        // Añadimos botones de diferentes tamaños simulados con texto
        frame.add(new JButton("Botón Corto"));
        frame.add(new JButton("Botón Medio"));
        frame.add(new JButton("Botón Mucho Más Largo"));
        frame.add(new JButton("Otro"));
        frame.add(new JButton("Final"));

        frame.setVisible(true);
    }
}