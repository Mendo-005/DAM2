import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EjemploAvanzado extends JFrame {

    // Creamos un componente simple (un panel) que será nuestro "jugador"
    JPanel cuadrado;

    public EjemploAvanzado() {
        setTitle("Eventos de Ratón y Teclado");
        setSize(500, 500);
        setLayout(null); // Usamos null para poder mover el cuadrado libremente
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 1. Configuración del objeto "Cuadrado"
        cuadrado = new JPanel();
        cuadrado.setBounds(200, 200, 50, 50); // Posición inicial
        cuadrado.setBackground(Color.RED);
        add(cuadrado);

        // 2. Implementar MOUSE LISTENER (Ratón) en el cuadrado
        cuadrado.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic, cambia a color aleatorio
                cuadrado.setBackground(new Color((int)(Math.random() * 0x1000000)));
                System.out.println("Has hecho clic en: " + e.getX() + ", " + e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Al pasar el ratón por encima, crece un poco
                cuadrado.setSize(60, 60);
                setTitle("¡Ratón dentro!");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Al salir el ratón, vuelve a tamaño normal
                cuadrado.setSize(50, 50);
                setTitle("Ratón fuera");
            }

            // Métodos obligatorios de la interfaz (aunque no los usemos)
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });

        // 3. Implementar KEY LISTENER (Teclado) en la Ventana
        // Añadimos el listener al Frame para detectar teclas globalmente
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Obtenemos la posición actual
                int x = cuadrado.getX();
                int y = cuadrado.getY();
                int velocidad = 10;

                // Detectamos qué tecla se pulsó usando constantes de KeyEvent
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    y -= velocidad; break;
                    case KeyEvent.VK_DOWN:  y += velocidad; break;
                    case KeyEvent.VK_LEFT:  x -= velocidad; break;
                    case KeyEvent.VK_RIGHT: x += velocidad; break;
                }
                
                // Actualizamos la posición "exacta"
                cuadrado.setLocation(x, y);
            }

            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });

        // IMPORTANTE: Para que el KeyListener funcione, la ventana debe tener el "foco"
        this.setFocusable(true);
    }

    public static void main(String[] args) {
        new EjemploAvanzado().setVisible(true);
    }
}