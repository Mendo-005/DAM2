# 📝 ORM - Hibernate + JPA + Relaciones

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto utiliza una **Arquitectura de capas** profesional para interactuar con una BD de relacional **separando responsabilidades**. La clase Main inicia todo, las clases Service gestionan el negocio, las clases DAO persisten los datos, las clases POJO (Entity) representan los datos y una clase JPAUtil gestiona EMF/EM. Tal y como se introdujo en el proyecto [RA3-ORM-HIBERNATE1-avanzado](https://github.com/DAM2-AccesoDatos/ra3-orm-hibernate1-avanzado)

En este nuevo proyecto se introduce el concepto de *relación entre entidades*. Concretamente el proyecto va evolucionando añadiendo las distintas relaciones existentes. En cada escenarios se creará un `tag` (etiqueta) para poder hacer checkout cuando necesitemos recuperar un escenario concreto.

1) **relación 1:1 unidireccional**: entre una entidad fuerte como es Alumno y una entidad debil como es Direccion. La dirección no tiene sentido sin el alumno al que acompaña y por ese motivo, nunca accederemos a una dirección directamente, siempre lo haremos desde el alumno (la unidireccionalidad va desde `Alumno` a `Direccion`). Nunca crearemos un DAO ni un SERVICE asociado a una entidad débil dado que jamás guardaremos una dirección aislada en nuestra BD ni tendremos lógica propia para una dirección.
2) **relación 1:1 bidireccional**: entre entidades fuertes como es Alumno y Expediente. En este caso, tanto `Alumno` como `ExpedienteAcademico` tienen vida propia independiente y tendrán cada uno su lógica propia. Así que debemos poder acceder al alumno desde expediente y al expediente desde el alumno. Ambos son susceptibles de tener por tanto su propio DAO y SERVICE.
3) **relación 1:N unidireccional**: entre entidades fuertes como es Alumno y Examen. En este caso, tanto `Alumno` como `Examen` tienen vida propia independiente y tendrán cada uno su lógica propia pero en este caso podríamos considerar sólo necesario implementar la unidireccionalidad entre Alumno y Examen, es decir, desde alumno poder recuperar todos sus exámenes.
4) **relación 1:N bidireccional**: entre las mismas entidades fuertes `Alumno` como `Examen`. En este caso podríamos considerar necesario implementar la bidireccionalidad, es decir, desde alumno poder recuperar todos sus exámenes y desde cada examen poder recuperar el alumno que lo hizo.
5) **relación N:M bidireccional** SIN atributos: entre entidades fuertes como Alumno y Modulo. En este caso, tanto `Alumno` como `Modulo` tienen vida propia independiente y tendrán cada uno su lógica propia. Cuando hay N:M siempre se suele implementar la bidireccionalidad. Es decir, desde alumno poder recuperar todos los módulos en los que está matriculado y desde cada módulo poder recuperar todos los alumnos matriculados.
6) **relación N:M bidireccional** CON atributos: entre las mismas entidades fuertes  `Alumno` como `Modulo`. Mismos escenario de antes salvo que en la relación hay uno o varios atributos. Por ejemplo la nota final que saca un alumno concreto en un módulo concreto.

## Versiones del proyecto (TAGs)

- **v1.0-1to1-unidir** → Alumno ── 1:1 ── Direccion (unidireccional)
- **v1.1-1to1-bidir**  → Alumno ── 1:1 ── ExpedienteAcademico (bidireccional)
- **v2.0-1toN-unidir** → Alumno ── 1:N ── Examen (unidireccional)
- **v2.1-1toN-bidir**  → Alumno ── 1:N ── Examen (bidireccional)
- **v3.0-NtoM-bidir**  → Alumno ── N:N ── Modulo (bidireccional)
- **v3.0-NtoM-bidir-atrib**  → Alumno ── N:N ── Modulo (bidireccional)

## Operativa básica con TAGs

- Para crear un tag con comentario (debe haber un commit previo):

  ```text
    git tag -a v1.0-1to1-unidir -m "Relación 1:1 unidireccional Alumno-Direccion"
  ```

- Para crear un tag a partir de commit concreto (ej. id=1a2b3c4d):

  ```text
    git tag -a v1.0-1to1-unidir 1a2b3c4d -m "Relación 1:1 unidireccional Alumno-Direccion"
  ```

- Para ir a un tag concreto:

  ```text
  git checkout v1.0-1to1-unidir
  ```

- Para subir los tags al repositorio remoto vinculado (GitHub):

  ```text
  git push origin v1.0-1to1-unidir
  ```

- Borrar un tag (si te equivocas) en local:
  
  ```text
  git tag -d v1.0-1to1-unidir
  ```

## 🔧 Tecnologías utilizadas

- Maven + Java 17+
- Pool HikariCP
- Log slf4j + Logback
- ORM Hibernate + JPA
- Base de datos H2 (modo persistente)
- Database Client (extensión VSCode) + Driver H2
- JPA (EntityManagerFactory, EntityManager, EntityTransaction, TypedQuery)
- Conceptos nuevos (cascade + CascadeType, orphanRemoval, fetch + FetchType)
- Anotaciones nuevas JPA (*@OneToOne,@OneToMany,@ManyToOne,@ManyToMany, @JoinColumn, @JoinTable, @Embeddable, @EmbeddedId, @MapsId*)

## ✅ Características

- Se utilizan clases `POJO` como contenedor de datos de la entidad asociada, con atributos de instancia y métodos getter/setter. Representa la tabla de BD como un objeto Java.

- Se utilizan clases `DAO` encargadas de encapsular y manejar la operativa de persistencia de datos de la entidad asociada, como consultar, guardar, actualizar o eliminar información de una base de datos.
  
- Se utiliza clases `SERVICE` encargadas de implementar todas las reglas y casos de negocio que atañen a la clase relacionada. Los SERVICE pueden coordinar múltiples DAOs en operaciones transaccionales y son los responsables de gestionar transacciones (begin, commit, rollback) también son el mejor lugar para registrar trazas (logs) de negocio.

- Se utiliza una clase de utilería `JPAUtil` encargada de crear y mantener un único EntityManagerFactory (EMF), de proveer EntityManager y finalmente de cerrar EMF al finalizar la aplicación.

- La clase `Main` solo se encarga de iniciar la aplicación, manejar excepciones de alto nivel y cerrar recursos generales antes de finalizar (EMF). No contiene lógica de negocio o acceso directo a la BD.

## 📁 Estructura del proyecto

```text
src/
├─ main/
│ ├─ es/ciudadescolar/
│ │ ├─ persistencia/
│ │ │   └─ AlumnoDAO.java
│ │ ├─ modelo/
│ │ │   ├─ Direccion.java 
│ │ │   └─ Alumno.java
│ │ ├─ servicios/
│ │ │   └─ AlumnoServicio.java
│ │ ├─ util/
│ │ │   └─ JPAUtil.java
│ │ └─ Main.java
│ └─ resources/
│    ├─ META-INF
│    │    └─ persistence.xml   
│    └─ logback.xml
├─ .gitignore
├─ pom.xml
└─ readme.md
```
