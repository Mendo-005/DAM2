import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EjemploMouseMover {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ejemplo Mouse Mover");
        JPanel panel = new JPanel();
        JLabel etiqueta = new JLabel("Mueve el mouse sobre este panel");

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Se ejecuta cuando el mouse se mueve mientras se mantiene presionado un botón
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                etiqueta.setText("Mouse en: X=" + e.getX() + " Y=" + e.getY());
            }
        });

        panel.add(etiqueta);
        ventana.add(panel);
        ventana.setSize(300, 200);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}