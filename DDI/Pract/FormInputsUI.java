import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormInputsUI extends JFrame {

    public FormInputsUI() {

        // ---------------------------
        // CONFIG VENTANA PRINCIPAL
        // ---------------------------
        setTitle("Formulario Completo - UI Profesional");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(15, 15));

        // ===========================
        //       PANEL HEADER
        // ===========================
        JPanel header = new JPanel();
        header.setBackground(new Color(30, 30, 30));
        header.setPreferredSize(new Dimension(700, 70));

        JLabel titulo = new JLabel("Formulario de Entradas Swing");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        header.add(titulo);
        add(header, BorderLayout.NORTH);


        // ===========================
        //     PANEL FORMULARIO
        // ===========================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // -------------- TEXTO --------------
        JLabel labelNombre = new JLabel("Nombre:");
        JTextField textNombre = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelNombre, gbc);
        gbc.gridx = 1;
        formPanel.add(textNombre, gbc);
        row++;

        // -------------- CHECKBOXES --------------
        JLabel labelChecks = new JLabel("Opciones:");
        JCheckBox checkA = new JCheckBox("Opción A");
        JCheckBox checkB = new JCheckBox("Opción B");

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        checkPanel.add(checkA);
        checkPanel.add(checkB);

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelChecks, gbc);
        gbc.gridx = 1;
        formPanel.add(checkPanel, gbc);
        row++;

        // -------------- RADIO BUTTONS --------------
        JLabel labelGenero = new JLabel("Género:");
        JRadioButton rbH = new JRadioButton("Hombre");
        JRadioButton rbM = new JRadioButton("Mujer");

        ButtonGroup group = new ButtonGroup();
        group.add(rbH);
        group.add(rbM);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.add(rbH);
        radioPanel.add(rbM);

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelGenero, gbc);
        gbc.gridx = 1;
        formPanel.add(radioPanel, gbc);
        row++;

        // -------------- COMBO BOX --------------
        JLabel labelPais = new JLabel("País:");
        JComboBox<String> comboPais = new JComboBox<>(new String[]{
            "España", "México", "Colombia", "Argentina"
        });

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelPais, gbc);
        gbc.gridx = 1;
        formPanel.add(comboPais, gbc);
        row++;

        // -------------- LISTA MULTIPLE --------------
        JLabel labelLista = new JLabel("Colores:");
        JList<String> listaColores = new JList<>(new String[]{
            "Rojo", "Verde", "Azul", "Amarillo"
        });
        listaColores.setVisibleRowCount(4);
        listaColores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollList = new JScrollPane(listaColores);

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelLista, gbc);
        gbc.gridx = 1;
        formPanel.add(scrollList, gbc);
        row++;

        // -------------- SLIDER --------------
        JLabel labelSlider = new JLabel("Nivel:");
        JSlider slider = new JSlider(0, 100, 50);

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelSlider, gbc);
        gbc.gridx = 1;
        formPanel.add(slider, gbc);
        row++;

        // -------------- TEXT AREA --------------
        JLabel labelArea = new JLabel("Descripción:");
        JTextArea textArea = new JTextArea(4, 20);
        JScrollPane scrollArea = new JScrollPane(textArea);

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(labelArea, gbc);
        gbc.gridx = 1;
        formPanel.add(scrollArea, gbc);
        row++;

        add(formPanel, BorderLayout.CENTER);


        // ===========================
        //     PANEL DE ACCIONES
        // ===========================
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        JButton btnProcesar = new JButton("Procesar Datos");
        JButton btnLimpiar = new JButton("Limpiar");

        footer.add(btnProcesar);
        footer.add(btnLimpiar);

        add(footer, BorderLayout.SOUTH);


        // ===========================
        //      EVENTO PROCESAR
        // ===========================
        btnProcesar.addActionListener(e -> {

            String nombre = textNombre.getText();
            boolean opcA = checkA.isSelected();
            boolean opcB = checkB.isSelected();
            String genero = rbH.isSelected() ? "Hombre" : rbM.isSelected() ? "Mujer" : "No seleccionado";
            String pais = (String) comboPais.getSelectedItem();
            java.util.List<String> colores = listaColores.getSelectedValuesList();
            int nivel = slider.getValue();
            String descripcion = textArea.getText();

            String resumen = """
                    DATOS RECIBIDOS:

                    Nombre: %s
                    Opción A: %s
                    Opción B: %s
                    Género: %s
                    País: %s
                    Colores: %s
                    Nivel: %d
                    Descripción:
                    %s
                    """.formatted(
                        nombre, opcA, opcB, genero, pais, colores, nivel, descripcion
                    );

            JOptionPane.showMessageDialog(this, resumen);
        });


        // ===========================
        //      EVENTO LIMPIAR
        // ===========================
        btnLimpiar.addActionListener(e -> {
            textNombre.setText("");
            checkA.setSelected(false);
            checkB.setSelected(false);
            group.clearSelection();
            comboPais.setSelectedIndex(0);
            listaColores.clearSelection();
            slider.setValue(50);
            textArea.setText("");
        });

        // Mostrar ventana
        setVisible(true);
    }


    public static void main(String[] args) {
        new FormInputsUI();
    }
}
