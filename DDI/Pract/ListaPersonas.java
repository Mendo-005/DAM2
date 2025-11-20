import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;

public class ListaPersonas extends JFrame {

    private DefaultListModel<String> modeloLista; // El "cerebro" de la lista
    private JList<String> listaVisual;            // La parte visual
    private JTextField campoNombre;
    private JButton botonAgregar;
    private JButton botonEliminar;

    public ListaPersonas() {
        setTitle("Gestión de Personas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Usamos BorderLayout para organizar la ventana [cite: 684]
        setLayout(new BorderLayout());

        // --- 1. Crear el Panel Superior (Input) ---
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());

        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new GridLayout(2,1,10, 10));

        campoNombre = new JTextField(15); // [cite: 691]
        botonAgregar = new JButton("Añadir Persona"); // [cite: 689]
        botonEliminar = new JButton("Eliminar Persona"); // [cite: 689]

        panelSuperior.add(new JLabel("Nombre:"));
        panelSuperior.add(campoNombre);
        panelLateral.add(botonAgregar);
        panelLateral.add(botonEliminar);

        panelSuperior.add(panelLateral);
        add(panelSuperior, BorderLayout.NORTH); // Ponemos el panel arriba

        // --- 2. Crear la Lista Central ---
        modeloLista = new DefaultListModel<>();
        listaVisual = new JList<>(modeloLista);
        
        // JScrollPane permite hacer scroll si hay mucha gente
        add(new JScrollPane(listaVisual), BorderLayout.CENTER);

        // --- 3. Lógica del Botón (Evento) ---
        // Usamos addActionListener como indica tu documento [cite: 268, 718]
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText().trim();

                // Validación simple: si no está vacío
                if (!nombre.isEmpty()) {
                    modeloLista.addElement(nombre); // Añadimos al modelo
                    campoNombre.setText("");        // Limpiamos el campo
                    campoNombre.requestFocus();     // Devolvemos el foco para seguir escribiendo
                } else {
                    // Mostramos un diálogo de error [cite: 727, 743]
                    JOptionPane.showMessageDialog(ListaPersonas.this, "Escribe un nombre primero");
                }
            }
        });

        // Asegúrate de que 'modeloLista' sea accesible aquí (variable de instancia o final)
        //listaVisual.addListSelectionListener(new ListSelectionListener() {
        //    @Override
        //    public void valueChanged(ListSelectionEvent e) {
        //        
        //        // 1. Evitamos eventos intermedios y comprobamos que la selección no esté vacía
        //        if (!e.getValueIsAdjusting() && !listaVisual.isSelectionEmpty()) {
        //            
        //            // 2. Obtenemos el valor y el índice
        //            String seleccionado = listaVisual.getSelectedValue();
        //            int indice = listaVisual.getSelectedIndex();
        //        
        //            // 3. IMPORTANTE: Borramos del MODELO, no de la lista visual
        //            if (seleccionado != null) {
        //                modeloLista.removeElementAt(indice); 
        //                // O también: modeloLista.removeElement(seleccionado);
        //                
        //                System.out.println("Acabas de eliminar: " + seleccionado);
        //            }
        //        }
        //    }
        //});
        
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validación simple: si no está vacío
                if (!listaVisual.isSelectionEmpty()) 
                {
                    String nombreAEliminar = listaVisual.getSelectedValue();
                    int indice = listaVisual.getSelectedIndex();

                    if (nombreAEliminar != null) 
                    {
                        modeloLista.removeElementAt(indice);
                    }

                } else {
                    // Mostramos un diálogo de error [cite: 727, 743]
                    JOptionPane.showMessageDialog(ListaPersonas.this, "Selecciona un nombre primero");
                }
            }
        });
    }

    public static void main(String[] args) {
        // Es buena práctica iniciar Swing así, aunque new ListaPersonas().setVisible(true) también sirve
        SwingUtilities.invokeLater(() -> {
            new ListaPersonas().setVisible(true);
        });
    }
}
