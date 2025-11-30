# VoiceButton Component 🎙️

## Descripción del Proyecto
Este proyecto implementa un **componente visual personalizado para Java Swing** llamado `VoiceButton`. Es un botón inteligente que hereda de `JButton` e integra capacidades de reconocimiento de voz offline.

El componente puede ser activado de dos formas:
1. **Interacción tradicional:** Haciendo clic con el ratón.
2. **Control por voz:** Pronunciando una "palabra clave" definida por el programador.

Este componente es ideal para aplicaciones de accesibilidad o interfaces "manos libres".

---

## 📚 Librerías y Dependencias

Para que el componente funcione, se requieren las siguientes librerías externas. Si usas Maven o Gradle, asegúrate de añadirlas. Si no, debes añadir los `.jar` al *classpath* de tu proyecto.

### 1. Vosk (Motor de Reconocimiento de Voz)
Vosk es un kit de herramientas de reconocimiento de voz offline (funciona sin internet), ligero y rápido.
* **Librería:** `vosk-[version].jar`
* **Función:** Procesa la entrada de audio del micrófono y la convierte a texto.
* **Web:** [https://alphacephei.com/vosk/](https://alphacephei.com/vosk/)

### 2. JNA (Java Native Access)
Vosk está escrito en C/C++, por lo que Java necesita JNA para comunicarse con la librería nativa.
* **Librería:** `jna-[version].jar`
* **Función:** Permite a Java interactuar con las librerías dinámicas del sistema (.dll / .so) de Vosk.

### 3. Google Gson
* **Librería:** `gson-[version].jar`
* **Función:** Vosk devuelve los resultados del reconocimiento en formato JSON. Gson se utiliza para parsear (leer) estos datos y extraer el texto reconocido.

### 4. Java Sound API (Javax Sound)
* **Librería:** Integrada en el JDK (no requiere descarga).
* **Función:** Gestiona la captura de audio desde el micrófono (`TargetDataLine`).

---

## ⚙️ Configuración del Modelo de Voz

El componente **requiere** un modelo de lenguaje descargado localmente para funcionar.

1. Ve a la página de modelos de Vosk: [Vosk Models](https://alphacephei.com/vosk/models).
2. Descarga un modelo ligero, por ejemplo: `vosk-model-small-es-0.42` (para español).
3. Descomprime el archivo descargado.
4. Cambia el nombre de la carpeta descomprimida a `model` (opcional, pero recomendado para simplificar la ruta).
5. Coloca la carpeta dentro de tu proyecto (por ejemplo, en `target/classes/model` o en la raíz del proyecto).

> **Nota:** La ruta que pases al constructor del `VoiceButton` debe apuntar a esta carpeta.

---

## 🚀 Funcionamiento

La clase `VoiceButton` funciona de la siguiente manera:

1. **Inicialización:** Al instanciar el botón, se inicia un hilo secundario (`Thread`) para no congelar la interfaz gráfica (EDT).
2. **Escucha Activa:** El hilo abre el micrófono y comienza a enviar *buffers* de audio al reconocedor Vosk.
3. **Detección:** Cuando Vosk detecta palabras, devuelve un JSON.
4. **Verificación:** El componente analiza el texto. Si la **palabra clave** está contenida en la frase pronunciada:
    * Se imprime un mensaje en consola.
    * Se invoca el método `doClick()`.
    * Se cambia el color del botón temporalmente (feedback visual).
5. **Evento:** Se disparan todos los `ActionListener` asociados al botón, ejecutando el código del usuario.

---

## 💻 Ejemplo de Uso

A continuación se muestra cómo integrar el componente en una aplicación Swing básica:

```java
import es.ciudadescolar.VoiceButton;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demo VoiceButton");
            frame.setSize(300, 200);
            frame.setLayout(new java.awt.FlowLayout());

            // 1. Definir la ruta al modelo descargado
            String rutaModelo = "rutas/a/tu/carpeta/model"; 

            // 2. Crear el botón personalizado
            // Texto: "Guardar", Palabra mágica: "guardar", Modelo: ruta
            VoiceButton btnGuardar = new VoiceButton("Guardar", "guardar", rutaModelo);

            // 3. Añadir la lógica (funciona con clic o con voz)
            btnGuardar.addActionListener(e -> {
                System.out.println("¡Guardando cambios...!");
                JOptionPane.showMessageDialog(frame, "Acción ejecutada correctamente");
            });

            frame.add(btnGuardar);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}