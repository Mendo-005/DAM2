import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GridBagLayoutSimple {
    public static void main(String[] args) {
        
        // Creamos el Frame
        JFrame frame = new JFrame("GridBagLayout Simple");
        
        // Configuración básica del frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        // Establecer GridBagLayout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Espaciado entre componentes
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Etiqueta "Nombre:"
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        frame.add(new JLabel("Nombre:"), gbc);
        
        // Campo de texto para el nombre
        gbc.gridx = 1; // Columna 1
        gbc.gridy = 0; // Fila 0
        frame.add(new JTextField(15), gbc);
        
        // Etiqueta "Email:"
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 1; // Fila 1
        frame.add(new JLabel("Email:"), gbc);
        
        // Campo de texto para el email
        gbc.gridx = 1; // Columna 1
        gbc.gridy = 1; // Fila 1
        frame.add(new JTextField(15), gbc);
        
        // Botón que ocupa 2 columnas
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 2; // Fila 2
        gbc.gridwidth = 2; // Ocupa 2 columnas
        frame.add(new JButton("Enviar"), gbc);
        
        // Mostramos la ventana
        frame.setVisible(true);
    }
}