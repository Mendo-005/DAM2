# Aplicación Swing con Control por Voz

Este proyecto demuestra cómo crear una interfaz gráfica básica en Java Swing que puede ser controlada mediante comandos de voz. Utiliza la API de reconocimiento de voz **Vosk** para procesar los comandos del usuario de forma offline.

## Tecnologías Utilizadas

- **Java 11+**
- **Maven** (para la gestión de dependencias y construcción)
- **Swing** (para la interfaz gráfica)
- **Vosk API** (para el reconocimiento de voz)

## Requisitos Previos

1.  **JDK 11 o superior**: Asegúrate de tener instalado un JDK compatible.
2.  **Apache Maven**: Necesario para compilar y ejecutar el proyecto.
3.  **Micrófono**: La aplicación necesita acceso al micrófono de tu ordenador.

## Configuración y Ejecución

Sigue estos pasos para poner en marcha la aplicación:

### Paso 1: Clonar o Descargar el Proyecto

Si tienes el código fuente, asegúrate de que la estructura de directorios es la correcta.

### Paso 2: Descargar el Modelo de Lenguaje de Vosk

Este es el paso más importante. La aplicación necesita un modelo de lenguaje para entender el español.

1.  Ve a la página de modelos de Vosk: [https://alphacephei.com/vosk/models](https://alphacephei.com/vosk/models)
2.  Busca y descarga un modelo pequeño en español. Por ejemplo: **`vosk-model-small-es-0.42.zip`**.
3.  **Descomprime** el archivo `.zip`.
4.  Copia la carpeta descomprimida (que se llamará algo como `vosk-model-small-es-0.42`) y renómbrala a **`model`**.
5.  Pega esta carpeta `model` dentro del directorio `src/main/resources/` de tu proyecto.

La estructura final debe verse así: