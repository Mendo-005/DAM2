import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EjercicioVentanas01 {
    public static void main(String[] args) {
        
        // Creamos la ventana mediante JFrame
        JFrame frame = new JFrame("Ejercicio 01");

        // Añadimos un Layout para estructurar los elementos de la aplicación
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Tamaño de la ventana
        frame.setSize(1080, 720);
        // Cerrar las pestañas (EXIT_ON_CLOSE)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // El layout del JFrame a FlowLayout
        // frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        // FlowLayout.CENTER: Coloca los componentes centrados
        // 20: Espacio horizontal entre componentes
        // 15: Espacio vertical entre componentes

        // Configurar el GridLayout: 3 filas y 2 columnas
        //frame.setLayout(new GridLayout(3, 2, 10, 10));
        // 10 es el espacio horizontal y vertical entre las celdas

        JPanel mainPanel = new JPanel(new GridLayout(1,2,10,10));
        // Añadimos los botones
        JButton buttonSanchez = new JButton("Pedro Sanchez");
        JButton buttonGerard = new JButton("Gerard Martín");

        JPanel secPanel =new JPanel(new GridLayout(1,3,10,10));
        // Añadimos la caja de texto
        JTextField electionField = new JTextField(20);
        // Añadimos los botones de enviar y borrar voto
        JButton buttonDelete = new JButton("Borrar");
        JButton buttonSend = new JButton("Enviar");
        
        buttonSanchez.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electionField.setText("Pedro Sanchez");
            }
        });
        buttonGerard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electionField.setText("Gerard Martín");
            }
        });

        //Añadimos los elementos a la ventana
        mainPanel.add(buttonSanchez);
        mainPanel.add(buttonGerard);
        frame.add(mainPanel);

        secPanel.add(electionField);
        secPanel.add(buttonDelete);
        secPanel.add(buttonSend);
        frame.add(secPanel);

        // Creamos un contenedor que nos permita mantener un panel debajo de otro
        JPanel verticalContainer = new JPanel(new GridLayout(2, 1, 0, 10)); // 2 filas, 1 columna, 10px de espacio vertical
        verticalContainer.add(mainPanel);
        verticalContainer.add(secPanel);

        // Añadimos el contenedor principal a la ventana. 
        // Como la ventana tiene FlowLayout.CENTER, centrará nuestro contenedor.
        frame.add(verticalContainer);

        //Visibilidad de la ventana ¡Importante!
        frame.setVisible(true);
    }
}
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//
//public class EjercicioVentanas01Mejorado extends JFrame {
//    // Componentes como campos de clase para mejor accesibilidad
//    private JTextField electionField;
//    private JButton btnSanchez, btnGerard, btnDelete, btnSend;
//    
//    public EjercicioVentanas01Mejorado() {
//        initializeComponents();
//        setupLayout();
//        setupEventHandlers();
//        configureFrame();
//    }
//    
//    private void initializeComponents() {
//        // Inicialización de componentes con nombres más descriptivos
//        btnSanchez = new JButton("Pedro Sánchez");
//        btnGerard = new JButton("Gerard Martín");
//        electionField = new JTextField(20);
//        electionField.setEditable(false); // Solo lectura para evitar entrada manual
//        btnDelete = new JButton("Borrar");
//        btnSend = new JButton("Enviar");
//    }
//    
//    private void setupLayout() {
//        // BorderLayout es más eficiente que FlowLayout para layouts complejos
//        setLayout(new BorderLayout(10, 10));
//        
//        // Panel superior para botones de candidatos
//        JPanel candidatesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
//        candidatesPanel.setBorder(BorderFactory.createTitledBorder("Selecciona tu candidato"));
//        candidatesPanel.add(btnSanchez);
//        candidatesPanel.add(btnGerard);
//        
//        // Panel inferior para campo de texto y acciones
//        JPanel actionPanel = new JPanel(new BorderLayout(5, 0));
//        actionPanel.setBorder(BorderFactory.createTitledBorder("Tu voto"));
//        
//        // Sub-panel para botones de acción
//        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
//        buttonsPanel.add(btnDelete);
//        buttonsPanel.add(btnSend);
//        
//        actionPanel.add(electionField, BorderLayout.CENTER);
//        actionPanel.add(buttonsPanel, BorderLayout.EAST);
//        
//        // Contenedor principal con padding
//        JPanel mainContainer = new JPanel(new BorderLayout(0, 15));
//        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        mainContainer.add(candidatesPanel, BorderLayout.NORTH);
//        mainContainer.add(actionPanel, BorderLayout.CENTER);
//        
//        add(mainContainer, BorderLayout.CENTER);
//    }
//    
//    private void setupEventHandlers() {
//        // Uso de lambdas para código más limpio y legible
//        btnSanchez.addActionListener(e -> selectCandidate("Pedro Sánchez"));
//        btnGerard.addActionListener(e -> selectCandidate("Gerard Martín"));
//        
//        // Funcionalidad para el botón borrar
//        btnDelete.addActionListener(e -> clearSelection());
//        
//        // Funcionalidad para el botón enviar
//        btnSend.addActionListener(this::submitVote);
//    }
//    
//    private void selectCandidate(String candidateName) {
//        electionField.setText(candidateName);
//        // Feedback visual: deshabilitar el botón seleccionado temporalmente
//        resetButtonStates();
//        if (candidateName.equals("Pedro Sánchez")) {
//            btnSanchez.setEnabled(false);
//        } else {
//            btnGerard.setEnabled(false);
//        }
//    }
//    
//    private void clearSelection() {
//        electionField.setText("");
//        resetButtonStates();
//    }
//    
//    private void resetButtonStates() {
//        btnSanchez.setEnabled(true);
//        btnGerard.setEnabled(true);
//    }
//    
//    private void submitVote(ActionEvent e) {
//        String selectedCandidate = electionField.getText().trim();
//        if (selectedCandidate.isEmpty()) {
//            JOptionPane.showMessageDialog(this, 
//                "Por favor, selecciona un candidato antes de enviar.", 
//                "Aviso", 
//                JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        
//        int result = JOptionPane.showConfirmDialog(this,
//            "¿Confirmas tu voto por " + selectedCandidate + "?",
//            "Confirmar voto",
//            JOptionPane.YES_NO_OPTION);
//            
//        if (result == JOptionPane.YES_OPTION) {
//            JOptionPane.showMessageDialog(this,
//                "Voto registrado correctamente para: " + selectedCandidate,
//                "Voto confirmado",
//                JOptionPane.INFORMATION_MESSAGE);
//            clearSelection();
//        }
//    }
//    
//    private void configureFrame() {
//        setTitle("Sistema de Votación - Ejercicio 01");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(600, 300); // Tamaño más apropiado para el contenido
//        setLocationRelativeTo(null); // Centrar en pantalla
//        setResizable(false); // Evitar redimensionado para mantener diseño
//    }
//    
//    public static void main(String[] args) {
//    // Usar SwingUtilities.invokeLater para thread safety
//    SwingUtilities.invokeLater(() -> {
//        // Configurar Look and Feel nativo del sistema
//        try {
//            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        new EjercicioVentanas01Mejorado().setVisible(true);
//    });
//}
//}