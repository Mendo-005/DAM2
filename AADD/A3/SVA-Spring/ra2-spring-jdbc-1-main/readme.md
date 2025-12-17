# 📝 Bases de Datos Relacionales - Spring JDBC 1

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto es un ejemplo de aplicación Java que utiliza **Spring JDBC** (sin Spring Boot) para interactuar con una BD de MySQL. Permite realizar operaciones básicas como consultas con y sin parámetros, inserciones, actualizaciones, borrado y manejo de transacciones.

Se utilizan patrones y utilidades de Spring como `JdbcTemplate`, `TransactionTemplate`, `DataSourceTransactionManager` y `RowMapper`, así como un archivo de configuración  **.env** con los detalles de la conexión para evitar filtrar datos confidenciales. También se tracean los eventos más significativos mediante logging con **Logback**.

El objetivo es **familiarizarse con la utilización del framework Spring con JDBC y reconocer los beneficios de su uso** para interactuar con bases de datos relacionales frente al uso de la API nativa JDBC de Java.

---

## Tecnologías utilizadas

- Java 17+
- Spring JDBC
- dotenv-java
- Logback
- Maven
- Base de datos MySQL
- SqlTools (extensión VSCode) + Driver Mysql
- JdbcTemplate, TransactionTemplate,  SimpleJdbcCall

---

## ✅ Características

- La aplicación interactua con la base de datos *Sakila* de MySQL.

- Se utiliza una clase POJO `Actor` como contenedor de datos de la entidad asociada, con variables de instancia y métodos getter/setter.

- En Spring JDBC, cuando hacemos una consulta con `JdbcTemplate`, cada fila recuperada del ResultSet debe convertirse en un objeto Java. Como Spring no sabe mapear las columnas de la tabla Actor de la BD a la clase `Actor`, se lo indicamos mediante la clase `ActorRowMapper`.

- Se implementa un datasource `DriverManagerDataSource` como fábrica de conexiones por lo que con cada operación realizada por JdbcTemplate se crea y cierra una conexión (ciertamente ineficiente). En posteriores versiones, se implementará un pool de conexiones.

## 📁 Estructura del proyecto

```text
src/
├─ main/
│ ├─ es/ciudadescolar/
│ │ ├─ repository/rowmapper
│ │ │ └─ ActorRowMapper.java
│ │ ├─ model/
│ │ │ └─ Actor.java
│ │ ├─ transaction/
│ │ │ └─ BorrarPeliculasCallBack.java
│ │ └─ App.java
│ └─ resources/
│    └─ logback.xml
├─ .gitignore
├─ .evn
├─ .evn.ejemplo
├─ pom.xml
└─ readme.md
```

---

## ▶️ ¿Cómo probar la funcionalidad?

Asegúrate de configurar correctamente la conexión en el fichero .env (driver, url y credenciales) antes de ejecutar la aplicación.

---
