import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class EjemploFocus {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ejemplo FocusListener");
        JTextField campoTexto = new JTextField(20);

        campoTexto.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("¡El campo ha ganado el foco!");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("El campo perdió el foco.");
            }
        });

        ventana.add(campoTexto);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
    }
}