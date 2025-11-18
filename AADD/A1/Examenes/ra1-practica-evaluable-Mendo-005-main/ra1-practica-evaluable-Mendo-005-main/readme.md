[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vC1fQ5uK)
# 📝 Práctica evaluable FICHEROS

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto de Java es la plantilla a utilizar para desarrollar la práctica evaluable en la que se precisa interactuar con ficheros de texto, xml y json.

El objetivo es **demostrar suficiencia y autonomía en el tratamiento de ficheros de texto, xml y json en Java** y desarrollar la lógica necesaria para cumplir con los requisitos del enunciado.

## 🔍 Resultado de Aprendizaje (RA) y Criterios de Evaluación (CEs)

**RA1. Desarrolla aplicaciones que gestionan información almacenada en ficheros identificando el campo de aplicación de los mismos y utilizando clases específicas.**

- a) Se han utilizado clases para la gestión de ficheros y directorios.
- c) Se han utilizado clases para recuperar información almacenada en ficheros.
- d) Se han utilizado clases para almacenar información en ficheros.
- e) Se han utilizado clases para realizar conversiones entre diferentes formatos de ficheros.
- f) Se han previsto y gestionado las excepciones.
- g) Se han probado y documentado las aplicaciones desarrolladas.

## 📁 Estructura del proyecto

```text

RA1-PRACT-EVALUABLE/
├── target/                                         # Carpeta donde se ubican .class
├── src/
│   ├──main
│   │    ├──es
│   │    │  └──ciudadescolar       
│   │    │      ├──hospital
│   │    │      │   ├──Medico.java 
│   │    │      │   ├──Cita.java 
│   │    │      │   ├──Constantes.java 
│   │    │      │   ├──HospitalErrorHandler.java 
│   │    │      │   └──Paciente.java  
│   │    │      └──Main.java                        # Clase principal
│   │    └── resources
│   │         └── Logback.xml                       # fichero configuración Log
│   └──test
│       ├──JsonTest.java                            # UTs json
│       ├──XmlValidationTest.java                   # UTs xml
│       └── resources
│            ├── hospital_2526.xsd                  # fichero XSD UTs
│            ├── hospital_Zaragoza_jose_sala.json   # fichero json correcto UTs
│            └──hospital_Zaragoza_jose_sala.xml     # fichero xml correcto UTs
├── hospital_2526.dtd                               # fichero DTD de validación xml entrada
├── hospital_2526.xml                               # fichero XML de entrada
├── hospital_2526.xsd                               # fichero XSD de validación xml salida
├── nuevos_medicos.txt                              # fichero TXT de entrada
├── pom.xml                                         # fichero configuración Maven
└── README.md                                       # Este documento

````

## ✅️ Pruebas Unitarias (Tests Automáticos)

En este proyecto encontrarás una serie de **pruebas unitarias** dentro de la carpeta:

```text
src/test/java/
````

Estas pruebas sirven para **comprobar automáticamente** si tu programa cumple los requisitos funcionales de la práctica. Se trata de aplicar la filosofía de TDD (Test-Driven Development)

⚠️ **No debes modificar estos archivos.**  
🧪 Puedes ejecutarlas en cualquier momento con:

```bash
mvn test
````

✔️ ¿Qué evalúan los tests?

- Que el programa crea los ficheros de salida necesarios (XML/JSON)

- Que el XML se valida correctamente con el XSD proporcionado

- Que el JSON tiene la estructura y contenido requerido 
  
- Comprobaciones específicas de atributos, elementos y valores

✅ ¿Para qué sirven?

- Para que te autoevalúes antes de entregar

- Para saber qué parte de la práctica está bien o mal

- Para obtener pistas cuando algo falla (los mensajes de error te ayudarán)

Cuando ejecutes mvn test, verás algo como:

```txt
[INFO] BUILD SUCCESS
````

✅ Esto significa que todos los tests han pasado 🎉

❌ Si aparece BUILD FAILURE, revisa el apartado de errores:
cada test que falla mostrará un mensaje indicando qué falta o qué no es correcto.

## 🎯 Rúbrica de evaluación

Esta práctica se evaluará teniendo en cuenta fundamentalmente los **requisitos funcionales**, pero también la **calidad de código** y buenas prácticas. La puntuación total es 100 puntos.

### 1️⃣ Requisitos funcionales (75 puntos)

- 📄 **Generación de fichero XML** – 35 pts  
  El programa crea un XML con la estructura correcta incluyendo la validación contra el XSD proporcionado. El contenido es correcto de acuerdo a los datos esperados. Se utiliza el ErrorHandler proporcionado para parsear y generar los XMLs.

- 📊 **Generación de fichero JSON** – 25 pts  
  El programa crea un JSON válido con la estructura correcta que incluye el contenido correcto de acuerdo a los datos esperados.

- 🔢 **Número de elementos** – 15 pts  
  Se inyectan adicionalmente al JSON y XML de salida los nuevos médicos extraidos del fichero TXT.

### 2️⃣ Requisitos no funcionales (25 puntos)

- 📐 **Modularización y claridad del código** – 5 pts  
  Código organizado en métodos y clases coherentes; fácil de entender.

- 📣 **Uso adecuado de logging** – 5 pts  
  Se usa log para informar de eventos relevantes, errores y pasos clave.

- ⚠️ **Manejo correcto de excepciones** – 5 pts  
  Se capturan y gestionan excepciones adecuadas sin interrumpir la ejecución evitando su propagación más allá del main.

- 🔍 **Validaciones previas** – 5 pts  
  Comprobaciones de existencia de ficheros, directorios, datos nulos, etc.

- 🗂️ **Elección de colecciones** – 5 pts  
  Las colecciones elegidas (List, Set, Map…) son adecuadas para la tarea y eficientes.

---

✅ **Total: 100 puntos**

---

## 📝 Operativa de los alumnos con el repositorio

1º. Descomprimir el fichero **VSCode-examen.zip** compartido por el profesor en la carpeta de usuario del alumno (ej. C:\Users\alumno). Se trata de un entorno de desarrollo **portable** e independiente de la máquina: VSCode + JDK + Maven + Git  

  🚨 ⚠️ **IMPORTANTE — NO USAR CARPETAS CON ACENTOS, EÑES NI ESPACIOS** en la ruta donde descomprimir el fichero.  

2º. Abrir la aplicación *VSCode portable* compartido por el profesor. Siempre ejecutando **launch.cmd**. Nunca ejecutando directamente "Code.exe".

3º. Aceptar la invitación del profesor de Github Classroom con la tarea. Esto creará un nuevo repo privado para el alumno en la organización.

4º. Clonar dicho repositorio mediante el comando git clone X siendo X la url del repo (ej. <https://github.com/DAM2-AccesoDatos/XXX.git> ).

**Nota:** Te pedirá que te autentiques con tu usuario y contraseña de GitHub. Hazlo desde el navegador (browser) para autorizar el clonado y posterior subida.

5º. Fijar usuario y email del alumno en el repo de trabajo:

```bash
  git config user.name "XXXX" siendo XXXX el nombre y apellido del alumno
  git config user.email "YYYY" siendo YYYY el correo electrónico del alumno
````

6º. Desarrollar la aplicación solicitada cumpliendo los requisitos indicados. No olvides ir haciendo commit conforme vayas avanzando en el desarrollo.

7º. Entregar la tarea subiendo el repositorio local al remoto. 

---
