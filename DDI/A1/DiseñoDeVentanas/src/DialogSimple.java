import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogSimple {
    private JFrame mainFrame;
    private JTextArea textArea;
    private Font currentFont;
    
    public static void main(String[] args) {
        new DialogSimple().createGUI();
    }
    
    public void createGUI() {
        // Crear ventana principal
        mainFrame = new JFrame("Dialog Simple - Eventos y Fuentes");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 400);
        mainFrame.setLayout(new BorderLayout());
        
        // Fuente inicial
        currentFont = new Font("Arial", Font.PLAIN, 14);
        
        // Crear área de texto
        textArea = new JTextArea("Texto de ejemplo.\nPuedes cambiar la fuente y el tamaño.");
        textArea.setFont(currentFont);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Crear menú
        createMenu();
        
        // Crear botones
        createButtons();
        
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        
        JMenuItem newWindow = new JMenuItem("Nueva Ventana");
        newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewWindow();
            }
        });
        
        JMenuItem exit = new JMenuItem("Salir");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menu.add(newWindow);
        menu.add(exit);
        menuBar.add(menu);
        mainFrame.setJMenuBar(menuBar);
    }
    
    private void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        // Botón para mostrar diálogo
        JButton dialogButton = new JButton("Mostrar Diálogo");
        dialogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });
        
        // Botón para negrita
        JButton boldButton = new JButton("Negrita");
        boldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleBold();
            }
        });
        
        // Botón para aumentar tamaño
        JButton biggerButton = new JButton("Más Grande");
        biggerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFontSize(2);
            }
        });
        
        // Botón para reducir tamaño
        JButton smallerButton = new JButton("Más Pequeño");
        smallerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFontSize(-2);
            }
        });
        
        // Botón para cambiar fuente
        JButton fontButton = new JButton("Cambiar Fuente");
        fontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFont();
            }
        });
        
        buttonPanel.add(dialogButton);
        buttonPanel.add(boldButton);
        buttonPanel.add(biggerButton);
        buttonPanel.add(smallerButton);
        buttonPanel.add(fontButton);
        
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void showDialog() {
        String message = "Este es un diálogo de ejemplo.\n¿Qué quieres hacer?";
        String[] options = {"Aceptar", "Cancelar"};
        
        int result = JOptionPane.showOptionDialog(
            mainFrame,
            message,
            "Diálogo de Ejemplo",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (result == 0) {
            JOptionPane.showMessageDialog(mainFrame, "Has elegido Aceptar");
        } else if (result == 1) {
            JOptionPane.showMessageDialog(mainFrame, "Has elegido Cancelar");
        }
    }
    
    private void openNewWindow() {
        JFrame newWindow = new JFrame("Nueva Ventana");
        newWindow.setSize(300, 200);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLayout(new BorderLayout());
        
        JLabel label = new JLabel("Esta es una nueva ventana", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newWindow.dispose();
            }
        });
        
        newWindow.add(label, BorderLayout.CENTER);
        newWindow.add(closeButton, BorderLayout.SOUTH);
        newWindow.setLocationRelativeTo(mainFrame);
        newWindow.setVisible(true);
    }
    
    private void toggleBold() {
        int style = currentFont.getStyle();
        if (currentFont.isBold()) {
            style = Font.PLAIN;  // Quitar negrita
        } else {
            style = Font.BOLD;   // Poner negrita
        }
        currentFont = new Font(currentFont.getName(), style, currentFont.getSize());
        textArea.setFont(currentFont);
    }
    
    private void changeFontSize(int change) {
        int newSize = currentFont.getSize() + change;
        if (newSize > 8 && newSize < 48) {  // Límites
            currentFont = new Font(currentFont.getName(), currentFont.getStyle(), newSize);
            textArea.setFont(currentFont);
        }
    }
    
    private void changeFont() {
        String[] fonts = {"Arial", "Times New Roman", "Courier New"};
        String selectedFont = (String) JOptionPane.showInputDialog(
            mainFrame,
            "Selecciona una fuente:",
            "Cambiar Fuente",
            JOptionPane.QUESTION_MESSAGE,
            null,
            fonts,
            currentFont.getName()
        );
        
        if (selectedFont != null) {
            currentFont = new Font(selectedFont, currentFont.getStyle(), currentFont.getSize());
            textArea.setFont(currentFont);
        }
    }
}