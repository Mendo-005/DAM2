package es.ciudadescolar;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GestureDetectorApp extends JFrame {

    static {
        // Cargar la librería nativa de OpenCV
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Error cargando librería nativa: " + e.getMessage());
            // Intentar cargarla desde el classpath
            try {
                nu.pattern.OpenCV.loadLocally();
            } catch (Exception e2) {
                System.err.println("Error con carga alternativa: " + e2.getMessage());
            }
        }
    }

    private JLabel videoLabel;
    private JButton actionButton;
    private CameraWorker cameraWorker;

    public GestureDetectorApp() {

        setTitle("Detección de Gestos con OpenCV y Swing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        videoLabel = new JLabel();
        videoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(videoLabel, BorderLayout.CENTER);

        actionButton = new JButton("Acción por Gesto");
        actionButton.setFont(new Font("Arial", Font.BOLD, 24));
        actionButton.setEnabled(false);

        actionButton.addActionListener(e -> {
            actionButton.setBackground(Color.GREEN);
            Timer t = new Timer(500, ev -> actionButton.setBackground(null));
            t.setRepeats(false);
            t.start();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(actionButton);
        add(bottomPanel, BorderLayout.SOUTH);

        startCamera();
    }

    private void startCamera() {
        // Carga automática con Maven + Bytedeco
        System.out.println("Versión OpenCV cargada: " + Core.VERSION);

        cameraWorker = new CameraWorker();
        cameraWorker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestureDetectorApp().setVisible(true));
    }

    private class CameraWorker extends SwingWorker<Void, Mat> {

        private VideoCapture camera;
        private CascadeClassifier faceCascade;
        private boolean handRaised = false;

        public CameraWorker() {
            // Cargar Haarcascade desde resources
            String xmlPath = getClass().getResource("/haarcascade_frontalface_alt.xml").getPath();
            faceCascade = new CascadeClassifier(xmlPath);

            if (faceCascade.empty()) {
                JOptionPane.showMessageDialog(null, "No se pudo cargar Haarcascade", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            camera = new VideoCapture(0);
            if (!camera.isOpened()) {
                JOptionPane.showMessageDialog(null, "No se pudo abrir la cámara", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            Mat frame = new Mat();
            Mat grayFrame = new Mat();

            while (!isCancelled()) {

                if (!camera.read(frame)) continue;

                Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

                MatOfRect faces = new MatOfRect();
                faceCascade.detectMultiScale(grayFrame, faces);

                boolean gestureDetected = false;

                for (Rect rect : faces.toArray()) {

                    Imgproc.rectangle(frame, rect, new Scalar(0,255,0), 2);

                    Rect handROI = new Rect(rect.x, rect.y - rect.height, rect.width, rect.height / 2);

                    if (handROI.y >= 0 && handROI.y + handROI.height < frame.rows()) {
                        // Región de interés para análisis de mano
                        Imgproc.rectangle(frame, handROI, new Scalar(255,0,0), 2);

                        gestureDetected = true;
                    }
                }

                if (gestureDetected && !handRaised) {
                    handRaised = true;
                    SwingUtilities.invokeLater(() -> actionButton.doClick());
                } else if (!gestureDetected) {
                    handRaised = false;
                }

                publish(frame);
            }

            return null;
        }

        @Override
        protected void process(List<Mat> frames) {
            Mat frame = frames.get(frames.size() - 1);
            videoLabel.setIcon(new ImageIcon(matToBufferedImage(frame)));
        }

        private BufferedImage matToBufferedImage(Mat mat) {
            int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;

            BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
            byte[] data = new byte[mat.cols() * mat.rows() * (int)mat.elemSize()];
            mat.get(0, 0, data);
            image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
            return image;
        }
    }
}
