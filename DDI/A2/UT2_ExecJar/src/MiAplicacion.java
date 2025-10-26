import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MiAplicacion extends JFrame {

    public MiAplicacion() {
        super("Aplicación Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Instancia la clase del JAR, que es un botón redondo
        RoundButton roundButton = new RoundButton("Presiona");

        JPanel panel = new JPanel();
        panel.add(roundButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MiAplicacion().setVisible(true);
        });
    }
}
