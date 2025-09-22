import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class BorderLayoutDemo {
    public static void main(String[] args) {
        
        // Creamos el Frame
        JFrame frame = new JFrame("BorderLayout Simple");
        
        // Configuración básica del frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Establecer BorderLayout
        frame.setLayout(new BorderLayout());
        
        // Añadir botones en las 5 regiones
        frame.add(new JButton("NORTH"), BorderLayout.NORTH);
        frame.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        frame.add(new JButton("EAST"), BorderLayout.EAST);
        frame.add(new JButton("WEST"), BorderLayout.WEST);
        frame.add(new JButton("CENTER"), BorderLayout.CENTER);
        
        // Mostramos la ventana (frame)
        frame.setVisible(true);
    }
}
