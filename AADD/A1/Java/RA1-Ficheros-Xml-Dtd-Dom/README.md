# 📝 Ficheros XML/DTD - DOM

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto de Java demuestra el tratamiento de ficheros XMLs para la lectura (*parser*) y escritura mediante la API DOM (org.w3c.dom). En ambos casos, el fichero de entrada y de salida se validan contra un DTD.

El objetivo es **familiarizarse con el procesamiento de ficheros XML con la API DOM** y aprender tanto a obtener información almacenada en dichos ficheros como a crear nuestros propios ficheros XML con la información solicitada. Habitualmente, la generación de un XML vendrá supeditado a un fichero validador que servirá como *acuerdo de interfaz* con otras aplicaciones o sistemas. En este caso concreto, dispondremos de un DTD para asgurar el formato de la información del XML de salida.

## ✅ Características

- No se utilizan librerías adicionales.
- Se utiliza una clase Alumno para la demostración del proceso de recuperación de información y volcado de la misma.

## 📁 Estructura del proyecto

```text

RA1-XML-DOM_1/
├── lib/                # vacío
├── bin/                # Carpeta donde se ubican .class
├── src/
│   └──es
│      └──ciudadescolar       
│         ├──util
|         │  ├──Alumno.java 
│         │  ├──XmlManager.java  # clase interacción con ficheros XML (parsea y genera) 
│         │  └──AlumnoErrorHandler.java   # clase gestora de errores durante parseo/generación de XML.
│         └──Programa.java                # Clase principal 
└── README.md                             # Este documento

````

## ▶️ ¿Cómo probar la funcionalidad?

Debes ubicar el xml "alumnos.xml" a parsear y el DTD "alumnos.dtd" que lo valida en el directorio de trabajo del proyecto. Así mismo, debes ubicar el DTD "alumno2.dtd" que permitirá validar el fichero de salida que genera la aplicación "alumnos2.xml"
Tras ejecutar el programa principal de este mismo repositorio, se mostrarán los alumnos por consola y se generá un xml de salida "alumnos2.xml" validable con el DTD proporcionado "alumnos2.dtd". El fichero de salida se sobreescribe con cada ejecución.

---
