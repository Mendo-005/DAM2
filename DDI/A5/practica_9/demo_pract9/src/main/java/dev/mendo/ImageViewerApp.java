package dev.mendo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageViewerApp extends JFrame {
    private DefaultListModel<String> imageListModel; // Modelo para almacenar las rutas de las imágenes
    private JList<String> imageList; // Lista para mostrar las imágenes
    private JLabel imagePreview; // Área de previsualización

    public ImageViewerApp() {
        setTitle("Visualizador de Imágenes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Modelo y lista de imágenes
        imageListModel = new DefaultListModel<>();
        imageList = new JList<>(imageListModel);
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(imageList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));

        // Previsualización de imagen
        imagePreview = new JLabel("Selecciona una imagen", SwingConstants.CENTER);
        imagePreview.setFont(new Font("Arial", Font.ITALIC, 16));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePreview.setHorizontalAlignment(SwingConstants.CENTER);

        // Botón para cargar imágenes
        JButton loadImagesButton = new JButton("Cargar Imágenes");
        loadImagesButton.addActionListener(e -> loadImages());

        // Acción al seleccionar un elemento en la lista
        imageList.addListSelectionListener(e -> previewImage());

        // Añadir componentes al panel principal
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(imagePreview, BorderLayout.CENTER);
        mainPanel.add(loadImagesButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Método para cargar imágenes
    private void loadImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen", "jpg", "png", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                imageListModel.addElement(file.getAbsolutePath());
            }
        }
    }

    // Método para previsualizar la imagen seleccionada
    private void previewImage() {
        String selectedImagePath = imageList.getSelectedValue();
        if (selectedImagePath != null) {
            ImageIcon imageIcon = new ImageIcon(selectedImagePath);
            Image scaledImage = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            imagePreview.setIcon(new ImageIcon(scaledImage));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageViewerApp app = new ImageViewerApp();
            app.setVisible(true);
        });
    }
} 