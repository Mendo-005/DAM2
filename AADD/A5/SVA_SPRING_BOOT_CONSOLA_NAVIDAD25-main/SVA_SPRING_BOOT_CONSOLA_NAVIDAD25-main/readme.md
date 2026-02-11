# 📝 Spring Boot DATA JPA + CommandLineRunner

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto utiliza una **Arquitectura de capas** profesional para interactuar con una BD de relacional mediante el framework Spring Boot **separando responsabilidades** y haciendo uso de Spring Data JPA.

La aplicación tiene una clase etiquetada con `@SpringBootApplication` que incluye el método main() y que implementa la interfaz `CommandLineRunner` por tratarse de una aplicación de consola.

Dicha clase debe estar en el paquete raíz pues hace un *component scan* desde su paquete hacia abajo en busca de todas las clases etiquetadas con:

- @Component
- @Service (especialización de @Component)
- @Repository (especialización de @Component)
- @Controller (especialización de @Component)
- @RestController (especialización de @Component)
- @Configuration (especialización de @Component)

Cuando encuentra una de esas, la registra como bean dentro en el `ApplicationContext` o lo que es lo mismo el contenedor de Spring que permite crear, configurar, conectar y gestionar todos los objetos (beans) de la aplicación.

Cuando arrancamos la aplicación de Spring Boot mediante la invocación *SpringApplication.run(MiAplicacion.class, args)*, Spring hace generalmente lo siguiente:

1) Lee configuración
2) Crea el ApplicationContext
3) Hace el component scan
4) Registra e instancia los beans identificados
5) Inyecta las dependencias
6) Ejecuta CommandLineRunner

Importante: Ya no creamos objetos con *new*, ahora se las pediremos al contedor de Spring que ya tiene los beans creados mediante la inyección de dependencias. Se recomienda realizar la inyección de dependencias en los constructores en lugar de usar `@autowired`

## 🗣️Analogía con el uso de ORM Hibernate+JPA

| Hibernate clásico | Spring Boot    |
| ----------------- | -------------- |
| modelo            | model          |
| dao               | repository     |
| servicios         | service        |
| JpaUtil           | -desaparece-   |

Dentro de los services...

| Antes (Hibernate + JPA manual)            | Ahora (Spring Boot + Spring Data JPA)                           |
| ----------------------------------------- | --------------------------------------------------------------- |
| EntityManager se crea manualmente         | Spring inyecta automáticamente el `EntityManager` o repositorio |
| Transaction begin/commit/rollback manual  | `@Transactional` gestiona la transacción automáticamente (AOP)  |
| DAO manual                                | Repository (interfaz) proporciona métodos CRUD automáticos      |
| Manejo explícito de recursos (em.close()) | Spring cierra automáticamente los recursos                      |

Y los DAO...

Ya no necesitamos clases DAO, Spring Data genera la implementación automáticamente mediante `Repository`

## 🔧 Tecnologías utilizadas

- Spring Boot 4.1.0
- Spring starter Data JPA
- Java 21+
- Pool HikariCP (integrado en Spring)
- Log slf4j + Logback (integrado en Spring)
- ORM Hibernate 7.2.3 (integrado en Spring)
- Base de datos mariaDB (despliegue portable)
- SQLTools (extensión VSCode)
- Conceptos nuevos (cascade + CascadeType, orphanRemoval, fetch + FetchType)
- Anotaciones nuevas Spring (*@SpringBootApplication, @Repository, @Service, @RestController, @Transactional, @Query*)

## ✅ Características

- Se utilizan clases `POJO`  etiquetadas con *@Entity* como contenedor de datos de la entidad asociada, con atributos de instancia y métodos getter/setter. Representa la tabla de BD como un objeto Java.

- Se utilizan interfaces `JpaRepository` etiquetadas con *@Repository* encargadas de manejar la operativa de persistencia de datos de la entidad asociada, como consultar, guardar, actualizar o eliminar información de una base de datos. A diferencia de los `DAO` utilizados manualmente en ORM, la interfaz nos brinda una serie de métodos sin necesidad de escribir su implementación: *save(), findById(), findAll(), deleteById(), count(), existsById()*. Spring se encarga de generar una implementación dinámica en tiempo de ejecución.
  
- Se utiliza clases `SERVICE`  etiquetadas con *@Service* encargadas de implementar todas las reglas y casos de negocio que atañen a la clase relacionada. Los SERVICE pueden coordinar múltiples repositorios en operaciones transaccionales sin necesidad de gestionar explícitamente las transacciones (begin, commit, rollback) gracias al uso de la etiqueta *@Transactional*. También son el mejor lugar para registrar trazas (logs) de negocio.

- La clase `Main` etiquetada con *@SpringBootApplication* implementa la interface `CommandLineRunner` y desencadena la ejecución de la aplicación en consola mediante la invocación *SpringApplication.run()*. No contiene lógica de negocio ni acceso directo a la BD.

## 📁 Estructura del proyecto

```text
src/
├─ main/
│ ├─ es/ciudadescolar/navidad25/
│ │     ├─ model/
│ │     │    ├─ Carta.java 
│ │     │   └─ Nino.java
│ │     ├─ repository/
│ │     │   ├─ NinoRepository.java 
│ │     │   └─ CartaRepository.java
│ │     ├─ service/
│ │     │   ├─ NinoService.java 
│ │     │   └─ CartaService.java
│ │     │
│ │     └─ Navidad25Application.java
│ │ 
│ └─ resources/
│    ├─ application.properties
│    └─ logback.xml   
├─ .gitignore
├─ pom.xml
└─ readme.md
```

## ▶️ Ejecución de la aplicación

Para ejecutar correctamente la aplicación, debe estar levantada la BD navidad25 en el SGBD de mariaDB (versión portable) pues en esta ocasión, fijaremos la propiedad `spring.jpa.hibernate.ddl-auto` a *none* para que hibernate no cree de antemano las estructuras (tablas) necesarias para persistir nuestras entidades.

## 🧹 correcta de eñes y acentos en PowerShell embebido en VSCode

1) Abrir "Open User Settings (JSON)" con la secuencia Ctrl + Shift + P.  
2) Añadir el siguiente elemento al JSON

    ```json
    "terminal.integrated.profiles.windows": {
    "PowerShell": {
        "source": "PowerShell",
        "args": ["-NoExit", "-Command", "$OutputEncoding = [Console]::InputEncoding = [Console]::OutputEncoding = [System.Text.Encoding]::UTF8"]
    }
    ```
