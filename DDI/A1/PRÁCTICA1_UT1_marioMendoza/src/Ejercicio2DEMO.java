package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Ejercicio 2: Interfaz gráfica de calculadora usando GridLayout
 * Implementa un teclado numérico y operaciones básicas en forma de rejilla
 * Autor: Mario Mendoza
 */
public class Ejercicio2DEMO {
    
    public static void main(String[] args) {
        // Crear e inicializar la calculadora
        SwingUtilities.invokeLater(() -> {
            new CalculadoraGridLayout();
        });
    }
}

/**
 * Clase principal de la calculadora que utiliza GridLayout
 * para organizar los botones en forma de rejilla
 */
class CalculadoraGridLayout extends JFrame implements ActionListener {
    
    // Componentes de la interfaz
    private JTextField pantalla;
    private JButton[] botonesNumeros;
    private JButton[] botonesOperaciones;
    private JButton btnIgual, btnClear, btnBorrar;
    
    // Variables para los cálculos
    private double numero1 = 0, numero2 = 0, resultado = 0;
    private String operacion = "";
    private boolean nuevaOperacion = true;
    
    public CalculadoraGridLayout() {
        inicializarVentana();
        crearComponentes();
        mostrarVentana();
    }
    
    /**
     * Configura las propiedades básicas de la ventana
     */
    private void inicializarVentana() {
        setTitle("Calculadora - GridLayout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Crea y organiza todos los componentes usando GridLayout
     */
    private void crearComponentes() {
        setLayout(new BorderLayout(5, 5));
        
        // Crear la pantalla de la calculadora
        crearPantalla();
        
        // Crear el panel principal con los botones usando GridLayout
        crearPanelBotones();
        
        // Añadir márgenes
        ((JComponent) getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }
    
    /**
     * Crea la pantalla de visualización de números y resultados
     */
    private void crearPantalla() {
        pantalla = new JTextField("0");
        pantalla.setFont(new Font("Arial", Font.BOLD, 24));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        pantalla.setBackground(Color.WHITE);
        pantalla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        pantalla.setPreferredSize(new Dimension(300, 60));
        
        add(pantalla, BorderLayout.NORTH);
    }
    
    /**
     * Crea el panel de botones usando GridLayout
     * Distribución en rejilla de 5x4 (5 filas, 4 columnas)
     */
    private void crearPanelBotones() {
        JPanel panelPrincipal = new JPanel(new GridLayout(5, 4, 5, 5));
        
        // Inicializar arrays de botones
        botonesNumeros = new JButton[10];
        botonesOperaciones = new JButton[4];
        
        // Crear botones numéricos (0-9)
        for (int i = 0; i < 10; i++) {
            botonesNumeros[i] = new JButton(String.valueOf(i));
            botonesNumeros[i].setFont(new Font("Arial", Font.BOLD, 18));
            botonesNumeros[i].addActionListener(this);
            botonesNumeros[i].setBackground(new Color(240, 240, 240));
        }
        
        // Crear botones de operaciones
        String[] operaciones = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            botonesOperaciones[i] = new JButton(operaciones[i]);
            botonesOperaciones[i].setFont(new Font("Arial", Font.BOLD, 18));
            botonesOperaciones[i].addActionListener(this);
            botonesOperaciones[i].setBackground(new Color(200, 220, 255));
        }
        
        // Crear botones especiales
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
        
        // DISTRIBUCIÓN EN REJILLA (GridLayout 5x4):
        // Fila 1: C, ←, /, *
        panelPrincipal.add(btnClear);
        panelPrincipal.add(btnBorrar);
        panelPrincipal.add(botonesOperaciones[3]); // /
        panelPrincipal.add(botonesOperaciones[2]); // *
        
        // Fila 2: 7, 8, 9, -
        panelPrincipal.add(botonesNumeros[7]);
        panelPrincipal.add(botonesNumeros[8]);
        panelPrincipal.add(botonesNumeros[9]);
        panelPrincipal.add(botonesOperaciones[1]); // -
        
        // Fila 3: 4, 5, 6, +
        panelPrincipal.add(botonesNumeros[4]);
        panelPrincipal.add(botonesNumeros[5]);
        panelPrincipal.add(botonesNumeros[6]);
        panelPrincipal.add(botonesOperaciones[0]); // +
        
        // Fila 4: 1, 2, 3, =
        panelPrincipal.add(botonesNumeros[1]);
        panelPrincipal.add(botonesNumeros[2]);
        panelPrincipal.add(botonesNumeros[3]);
        panelPrincipal.add(btnIgual);
        
        // Fila 5: 0 (ocupa 2 columnas), punto decimal, espacio
        panelPrincipal.add(botonesNumeros[0]);
        panelPrincipal.add(new JLabel("")); // Espacio vacío
        panelPrincipal.add(new JLabel("")); // Espacio vacío  
        panelPrincipal.add(new JLabel("")); // Espacio vacío
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    /**
     * Maneja todos los eventos de los botones (ActionListener)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        try {
            // Manejo de números (0-9)
            if (esNumero(comando)) {
                manejarNumero(comando);
            }
            // Manejo de operaciones (+, -, *, /)
            else if (esOperacion(comando)) {
                manejarOperacion(comando);
            }
            // Botón igual (=)
            else if (comando.equals("=")) {
                calcularResultado();
            }
            // Botón limpiar (C)
            else if (comando.equals("C")) {
                limpiarCalculadora();
            }
            // Botón borrar (←)
            else if (comando.equals("←")) {
                borrarUltimoCaracter();
            }
        } catch (Exception ex) {
            pantalla.setText("Error");
            nuevaOperacion = true;
        }
    }
    
    /**
     * Verifica si el comando es un número
     */
    private boolean esNumero(String comando) {
        try {
            Integer.parseInt(comando);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Verifica si el comando es una operación matemática
     */
    private boolean esOperacion(String comando) {
        return comando.equals("+") || comando.equals("-") || 
               comando.equals("*") || comando.equals("/");
    }
    
    /**
     * Maneja la entrada de números
     */
    private void manejarNumero(String numero) {
        if (nuevaOperacion) {
            pantalla.setText(numero);
            nuevaOperacion = false;
        } else {
            String textoActual = pantalla.getText();
            if (!textoActual.equals("0")) {
                pantalla.setText(textoActual + numero);
            } else {
                pantalla.setText(numero);
            }
        }
    }
    
    /**
     * Maneja las operaciones matemáticas
     */
    private void manejarOperacion(String nuevaOperacion) {
        if (!operacion.isEmpty() && !this.nuevaOperacion) {
            calcularResultado();
        }
        
        numero1 = Double.parseDouble(pantalla.getText());
        operacion = nuevaOperacion;
        this.nuevaOperacion = true;
    }
    
    /**
     * Realiza el cálculo y muestra el resultado
     */
    private void calcularResultado() {
        if (!operacion.isEmpty()) {
            numero2 = Double.parseDouble(pantalla.getText());
            
            switch (operacion) {
                case "+":
                    resultado = numero1 + numero2;
                    break;
                case "-":
                    resultado = numero1 - numero2;
                    break;
                case "*":
                    resultado = numero1 * numero2;
                    break;
                case "/":
                    if (numero2 != 0) {
                        resultado = numero1 / numero2;
                    } else {
                        pantalla.setText("Error: División por 0");
                        limpiarCalculadora();
                        return;
                    }
                    break;
            }
            
            // Mostrar resultado (sin decimales si es un número entero)
            if (resultado == (long) resultado) {
                pantalla.setText(String.valueOf((long) resultado));
            } else {
                pantalla.setText(String.valueOf(resultado));
            }
            
            operacion = "";
            nuevaOperacion = true;
        }
    }
    
    /**
     * Limpia completamente la calculadora
     */
    private void limpiarCalculadora() {
        pantalla.setText("0");
        numero1 = 0;
        numero2 = 0;
        resultado = 0;
        operacion = "";
        nuevaOperacion = true;
    }
    
    /**
     * Borra el último carácter introducido
     */
    private void borrarUltimoCaracter() {
        String textoActual = pantalla.getText();
        if (textoActual.length() > 1) {
            pantalla.setText(textoActual.substring(0, textoActual.length() - 1));
        } else {
            pantalla.setText("0");
            nuevaOperacion = true;
        }
    }
    
    /**
     * Hace visible la ventana
     */
    private void mostrarVentana() {
        setVisible(true);
    }
}
