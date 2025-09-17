import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ventana {
 public static void main(String[] args) {

 // Crear una nueva ventana (JFrame). JFrame es el contenedor principal de la aplicación gráfica.
 JFrame frame = new JFrame("Mi Primera Ventana + Modificación"); // Se establece el título de la ventana en el constructor.

 // Establecer el Layout Manager. FlowLayout coloca los componentes uno al lado del otro.
 frame.setLayout(new FlowLayout());
 
 // Establecer el tamaño de la ventana en píxeles (ancho x alto).
 frame.setSize(300, 200); 
 
 // Configurar la operación de cierre. EXIT_ON_CLOSE asegura que la aplicación se termine al cerrar la ventana.
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

 // Crear un nuevo botón (JButton). Este componente permite al usuario realizar una acción al hacer clic.
 JButton button = new JButton("Haz clic aquí"); // El texto del botón se pasa como argumento al constructor.

 // Crear un nuevo campo de texto (JTextField). Es un área donde el usuario puede escribir una línea de texto.
 JTextField textField = new JTextField(20); // El número 20 indica el ancho preferido del campo, en columnas de texto.

 // Agregar el campo de texto y el botón a la ventana (al contenedor JFrame).
 frame.add(textField);
 frame.add(button);

 // Añadir un ActionListener para manejar el evento de clic del botón
 button.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
 textField.setText("¡Botón presionado!"); // Cambiar el texto cuando se haga clic
 }
 });
 // Hacer la ventana visible
 frame.setVisible(true);
 }
}
