import javax.swing.JButton;
import javax.swing.JFrame;

public class JavaSPractica extends JFrame{
    
    public static JFrame generarPanel()
    {
        JFrame panelPrincipal = new JFrame("Practica 1");
        panelPrincipal.setSize(600,400);
        panelPrincipal.setDefaultCloseOperation(EXIT_ON_CLOSE);
        panelPrincipal.setLayout(null);

        JButton btn1 = new JButton("Cerrar");
        btn1.addActionListener(e -> System.out.println("boton 1 presionado ") );


        panelPrincipal.add(btn1);
        

        return panelPrincipal;
    }
    

    public static void main(String[] args) {

        JFrame panel = generarPanel();
        panel.setVisible(true);
    }
    
}
