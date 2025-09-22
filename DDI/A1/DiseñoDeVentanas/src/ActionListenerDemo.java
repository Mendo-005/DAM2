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
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionListenerDemo {
    private JFrame mainFrame;
    private JTextArea textArea;
    private Font currentFont;
    
    public static void main(String[] args) {
        new ActionListenerDemo().createGUI();
    }
    
    public void createGUI() {
        // Crear ventana principal
        mainFrame = new JFrame("Ejemplo Dialog - Eventos y Acciones");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 400);
        mainFrame.setLayout(new BorderLayout());
        
        // Fuente inicial
        currentFont = new Font("Arial", Font.PLAIN, 12);
        
        // Crear área de texto
        textArea = new JTextArea("Texto de ejemplo para modificar.\nPuedes cambiar la fuente, tamaño y estilo.");
        textArea.setFont(currentFont);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Crear menú
        createMenuBar();
        
        // Crear panel de botones
        createButtonPanel();
        
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu fileMenu = new JMenu("Archivo");
        
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
        
        fileMenu.add(newWindow);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        
        // Menú Formato
        JMenu formatMenu = new JMenu("Formato");
        
        JMenuItem fontDialog = new JMenuItem("Cambiar Fuente...");
        fontDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFontDialog();
            }
        });
        
        formatMenu.add(fontDialog);
        
        menuBar.add(fileMenu);
        menuBar.add(formatMenu);
        mainFrame.setJMenuBar(menuBar);
    }
    
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        // Botón para mostrar diálogo simple
        JButton showDialog = new JButton("Mostrar Diálogo");
        showDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSimpleDialog();
            }
        });
        
        // Botón para negrita
        JButton boldButton = new JButton("Negrita");
        boldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleBold();
            }
        });
        
        // Botón para cursiva
        JButton italicButton = new JButton("Cursiva");
        italicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleItalic();
            }
        });
        
        // Botón para aumentar tamaño
        JButton increaseSizeButton = new JButton("Tamaño +");
        increaseSizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFontSize(2);
            }
        });
        
        // Botón para disminuir tamaño
        JButton decreaseSizeButton = new JButton("Tamaño -");
        decreaseSizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFontSize(-2);
            }
        });
        
        buttonPanel.add(showDialog);
        buttonPanel.add(boldButton);
        buttonPanel.add(italicButton);
        buttonPanel.add(increaseSizeButton);
        buttonPanel.add(decreaseSizeButton);
        
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Método para mostrar un diálogo simple
    private void showSimpleDialog() {
        String[] options = {"Aceptar", "Cancelar", "Ayuda"};
        int choice = JOptionPane.showOptionDialog(
            mainFrame,
            "Este es un diálogo de ejemplo.\n¿Qué deseas hacer?",
            "Diálogo de Ejemplo",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0:
                JOptionPane.showMessageDialog(mainFrame, "Has elegido Aceptar");
                break;
            case 1:
                JOptionPane.showMessageDialog(mainFrame, "Has elegido Cancelar");
                break;
            case 2:
                JOptionPane.showMessageDialog(mainFrame, "Ayuda: Este es un programa de ejemplo para estudiar diálogos y eventos en Java Swing");
                break;
        }
    }
    
    // Método para abrir una nueva ventana
    private void openNewWindow() {
        JFrame newWindow = new JFrame("Nueva Ventana");
        newWindow.setSize(300, 200);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel label = new JLabel("Esta es una nueva ventana", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newWindow.dispose();
            }
        });
        
        newWindow.setLayout(new BorderLayout());
        newWindow.add(label, BorderLayout.CENTER);
        newWindow.add(closeButton, BorderLayout.SOUTH);
        
        newWindow.setLocationRelativeTo(mainFrame);
        newWindow.setVisible(true);
    }
    
    // Método para mostrar diálogo de fuente
    private void showFontDialog() {
        String[] fonts = {"Arial", "Times New Roman", "Courier New", "Comic Sans MS"};
        String[] sizes = {"8", "10", "12", "14", "16", "18", "20", "24", "28", "32"};
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JComboBox<String> fontBox = new JComboBox<>(fonts);
        fontBox.setSelectedItem(currentFont.getName());
        
        JComboBox<String> sizeBox = new JComboBox<>(sizes);
        sizeBox.setSelectedItem(String.valueOf(currentFont.getSize()));
        
        JCheckBox boldCheck = new JCheckBox("Negrita", currentFont.isBold());
        JCheckBox italicCheck = new JCheckBox("Cursiva", currentFont.isItalic());
        
        panel.add(new JLabel("Fuente:"));
        panel.add(fontBox);
        panel.add(new JLabel("Tamaño:"));
        panel.add(sizeBox);
        panel.add(boldCheck);
        panel.add(italicCheck);
        
        int result = JOptionPane.showConfirmDialog(
            mainFrame, 
            panel, 
            "Seleccionar Fuente", 
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String fontName = (String) fontBox.getSelectedItem();
            int fontSize = Integer.parseInt((String) sizeBox.getSelectedItem());
            int style = Font.PLAIN;
            
            if (boldCheck.isSelected()) style |= Font.BOLD;
            if (italicCheck.isSelected()) style |= Font.ITALIC;
            
            currentFont = new Font(fontName, style, fontSize);
            textArea.setFont(currentFont);
        }
    }
    
    // Método para alternar negrita
    private void toggleBold() {
        int style = currentFont.getStyle();
        if (currentFont.isBold()) {
            style &= ~Font.BOLD;  // Quitar negrita
        } else {
            style |= Font.BOLD;   // Añadir negrita
        }
        currentFont = new Font(currentFont.getName(), style, currentFont.getSize());
        textArea.setFont(currentFont);
    }
    
    // Método para alternar cursiva
    private void toggleItalic() {
        int style = currentFont.getStyle();
        if (currentFont.isItalic()) {
            style &= ~Font.ITALIC;  // Quitar cursiva
        } else {
            style |= Font.ITALIC;   // Añadir cursiva
        }
        currentFont = new Font(currentFont.getName(), style, currentFont.getSize());
        textArea.setFont(currentFont);
    }
    
    // Método para cambiar tamaño de fuente
    private void changeFontSize(int change) {
        int newSize = currentFont.getSize() + change;
        if (newSize > 6 && newSize < 72) {  // Límites razonables
            currentFont = new Font(currentFont.getName(), currentFont.getStyle(), newSize);
            textArea.setFont(currentFont);
        }
    }
}