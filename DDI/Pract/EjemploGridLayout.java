import javax.swing.*;
import java.awt.*;

public class EjemploGridLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo GridLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Configuración: 3 filas, 2 columnas, separación de 10px
        // [cite: 628, 629, 630, 631, 632]
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        // Añadimos 6 componentes (para llenar las 3x2 celdas)
        // Nota: JButton crea botones y JLabel crea etiquetas [cite: 689, 690]
        frame.add(new JButton("1. Botón"));
        frame.add(new JButton("2. Botón"));
        frame.add(new JLabel("3. Etiqueta (Texto)", SwingConstants.CENTER)); // Centramos texto
        frame.add(new JButton("4. Botón"));
        frame.add(new JTextField("5. Campo Texto")); // [cite: 691]
        frame.add(new JButton("6. Botón"));

        frame.setVisible(true);
    }
}
