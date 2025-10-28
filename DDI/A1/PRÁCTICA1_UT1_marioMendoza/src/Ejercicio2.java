import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Ejercicio2 {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraGridLayout();
        });
    }
}

// GridLayout
class CalculadoraGridLayout extends JFrame implements ActionListener {
    
    private JTextField pantalla;
    private JButton[] botonesNumeros;
    private JButton[] botonesOperaciones;
    private JButton btnIgual, btnClear, btnBorrar;
    
    public CalculadoraGridLayout() {
        inicializarVentana();
        crearComponentes();
        mostrarVentana();
    }
    
    private void inicializarVentana() {
        setTitle("Demo GridLayout - Diseño de Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void crearComponentes() {
        setLayout(new BorderLayout(5, 5));
        
        crearPantalla();
        crearPanelBotones();
        
        ((JComponent) getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }
    
    private void crearPantalla() {
        pantalla = new JTextField("Demo GridLayout");
        pantalla.setFont(new Font("Arial", Font.BOLD, 24));
        pantalla.setHorizontalAlignment(JTextField.CENTER);
        pantalla.setEditable(false);
        pantalla.setBackground(Color.WHITE);
        pantalla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        pantalla.setPreferredSize(new Dimension(300, 60));
        
        add(pantalla, BorderLayout.NORTH);
    }
    
    // GridLayout 5x4
    private void crearPanelBotones() {
        JPanel panelPrincipal = new JPanel(new GridLayout(5, 4, 5, 5));
        
        botonesNumeros = new JButton[10];
        botonesOperaciones = new JButton[4];
        
        // Botones números
        for (int i = 0; i < 10; i++) {
            botonesNumeros[i] = new JButton(String.valueOf(i));
            botonesNumeros[i].setFont(new Font("Arial", Font.BOLD, 18));
            botonesNumeros[i].addActionListener(this);
            botonesNumeros[i].setBackground(new Color(240, 240, 240));
        }
        
        // Botones operaciones
        String[] operaciones = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            botonesOperaciones[i] = new JButton(operaciones[i]);
            botonesOperaciones[i].setFont(new Font("Arial", Font.BOLD, 18));
            botonesOperaciones[i].addActionListener(this);
            botonesOperaciones[i].setBackground(new Color(200, 220, 255));
        }
        
        // Botones especiales
        btnClear = new JButton("C");
        btnClear.setFont(new Font("Arial", Font.BOLD, 18));
        btnClear.setBackground(new Color(255, 200, 200));
        btnClear.addActionListener(this);
        
        btnBorrar = new JButton("←");
        btnBorrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnBorrar.setBackground(new Color(255, 220, 200));
        btnBorrar.addActionListener(this);
        
        btnIgual = new JButton("=");
        btnIgual.setFont(new Font("Arial", Font.BOLD, 18));
        btnIgual.setBackground(new Color(200, 255, 200));
        btnIgual.addActionListener(this);
        
        // Distribución en rejilla 5x4
        panelPrincipal.add(btnClear);
        panelPrincipal.add(btnBorrar);
        panelPrincipal.add(botonesOperaciones[3]);
        panelPrincipal.add(botonesOperaciones[2]);
        
        panelPrincipal.add(botonesNumeros[7]);
        panelPrincipal.add(botonesNumeros[8]);
        panelPrincipal.add(botonesNumeros[9]);
        panelPrincipal.add(botonesOperaciones[1]); 
        
        panelPrincipal.add(botonesNumeros[4]);
        panelPrincipal.add(botonesNumeros[5]);
        panelPrincipal.add(botonesNumeros[6]);
        panelPrincipal.add(botonesOperaciones[0]); 
        
        panelPrincipal.add(botonesNumeros[1]);
        panelPrincipal.add(botonesNumeros[2]);
        panelPrincipal.add(botonesNumeros[3]);
        panelPrincipal.add(btnIgual);
        
        panelPrincipal.add(botonesNumeros[0]);
        panelPrincipal.add(new JLabel(""));
        panelPrincipal.add(new JLabel(""));
        panelPrincipal.add(new JLabel(""));
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if (comando.equals("C")) {
            pantalla.setText("Demo GridLayout");
        } else {
            pantalla.setText("Presionaste: " + comando);
        }
    }
    
    private void mostrarVentana() {
        setVisible(true);
    }
}
