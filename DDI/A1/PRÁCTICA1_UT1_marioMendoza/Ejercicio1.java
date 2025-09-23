import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Ejercicio1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame(" Compra de Entradas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> System.out.println("Compra Cancelada"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cancelarButton, BorderLayout.WEST);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(e -> openNewWindow());
        frame.add(comprarButton, BorderLayout.EAST);
        frame.setVisible(true);
    }
    

    private static void openNewWindow() {
        JFrame newFrame = new JFrame("Nueva Ventana");
        newFrame.setSize(300, 200);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setVisible(true);
    }



}

