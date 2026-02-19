drop database IF EXISTS peliculas_orm_2425;
CREATE DATABASE IF NOT EXISTS peliculas_orm_2425;

USE peliculas_orm_2425;

CREATE TABLE Alumnos (
    id_alumno INT AUTO_INCREMENT PRIMARY KEY,
    nombreyapellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE
);

CREATE TABLE alumnoscontactos (
	id_contacto INT AUTO_INCREMENT PRIMARY KEY,
    id_alumno INT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    CONSTRAINT fk_contacto_alumno FOREIGN KEY (id_alumno) REFERENCES Alumnos(id_alumno) 
);

CREATE TABLE Empresas (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    sector VARCHAR(100),
    direccion VARCHAR(255),
    email_contacto VARCHAR(100),
    telefono_contacto VARCHAR(15)
);

CREATE TABLE Tutores (
    id_tutor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    tipo ENUM('Instituto', 'Empresa') NOT NULL,
	empresa INT,
	CONSTRAINT fk_tutor_empresa FOREIGN KEY (empresa) REFERENCES Empresas(id_empresa) 
);

CREATE TABLE Practicas (
    id_practica INT AUTO_INCREMENT PRIMARY KEY,
    id_alumno INT NOT NULL,
    id_tutor_instituto INT NOT NULL,
    id_tutor_empresa INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    horas_totales INT NOT NULL DEFAULT 370,
    evaluacion_empresa ENUM('APTO', 'NO APTO'),
    evaluacion_instituto ENUM('APTO', 'NO APTO'),
    CONSTRAINT fk_practica_alumno FOREIGN KEY (id_alumno) REFERENCES Alumnos(id_alumno),
    CONSTRAINT fk_practica_tutor_insti FOREIGN KEY (id_tutor_instituto) REFERENCES Tutores(id_tutor),
    CONSTRAINT fk_practica_tutor_emp FOREIGN KEY (id_tutor_empresa) REFERENCES Tutores(id_tutor)
);

CREATE TABLE Habilidades (
    id_habilidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE Alumnos_Habilidades (
    id_alumno INT NOT NULL,
    id_habilidad INT NOT NULL,
    PRIMARY KEY (id_alumno, id_habilidad),
    CONSTRAINT fk_alumno_habilidad_alumno FOREIGN KEY (id_alumno) REFERENCES Alumnos(id_alumno),
    CONSTRAINT fk_alumno_habilidad_habilidad FOREIGN KEY (id_habilidad) REFERENCES Habilidades(id_habilidad)
);


INSERT INTO Alumnos (nombreyapellido, fecha_nacimiento) VALUES ('Carlos Alonso', '2000-05-15');
INSERT INTO Alumnos (nombreyapellido, fecha_nacimiento) VALUES ('Ana Sanz', '1999-11-20');
INSERT INTO Alumnos (nombreyapellido, fecha_nacimiento) VALUES ('Luis Roca', '2001-03-10');
INSERT INTO Alumnos (nombreyapellido, fecha_nacimiento) VALUES ('María Díaz','2002-07-25');

INSERT INTO alumnoscontactos (id_alumno, email, telefono) VALUES (1, 'carlos.perez@example.com', '123456789');
INSERT INTO alumnoscontactos (id_alumno, email, telefono) VALUES (2, 'ana.lopez@example.com', '987654321');
INSERT INTO alumnoscontactos (id_alumno, email, telefono) VALUES (3, 'luis.martinez@example.com', '654321987');
INSERT INTO alumnoscontactos (id_alumno, email, telefono) VALUES (4, 'maria.gonzalez@example.com', '321987654');

INSERT INTO Empresas (nombre, sector, direccion, email_contacto, telefono_contacto) VALUES ('IES Ciudad Escolar', 'Informática', 'Vía de Servicio, Km 12,800', 'ies.ciudadescolar.madrid@educa.madrid.org', '917341244');
INSERT INTO Empresas (nombre, sector, direccion, email_contacto, telefono_contacto) VALUES ('Tech Solutions', 'Tecnología', 'Parque Empresarial 12', 'contacto@techsolutions.com', '555123456');
INSERT INTO Empresas (nombre, sector, direccion, email_contacto, telefono_contacto) VALUES ('Green Energy', 'Energía Renovable', 'Calle Verde 34', 'info@greenenergy.com', '555987654');
INSERT INTO Empresas (nombre, sector, direccion, email_contacto, telefono_contacto) VALUES ('Consulting Group', 'Consultoría', 'Avenida Negocios 56', 'consultas@consultinggroup.com', '555654321');
INSERT INTO Empresas (nombre, sector, direccion, email_contacto, telefono_contacto) VALUES ('Innovatech', 'Innovación', 'Calle Innovación 78', 'innovate@innovatech.com', '555321987');

INSERT INTO Tutores (nombre, apellido, email, telefono, tipo, empresa) VALUES ('José', 'Ramírez', 'jose.ramirez@instituto.com', '111222333', 'instituto', 1);
INSERT INTO Tutores (nombre, apellido, email, telefono, tipo, empresa) VALUES ('Lucía', 'Fernández', 'lucia.fernandez@instituto.com', '444555666', 'instituto', 1);
INSERT INTO Tutores (nombre, apellido, email, telefono, tipo, empresa) VALUES ('Miguel', 'Sánchez', 'miguel.sanchez@empresa.com', '777888999', 'Empresa',2);
INSERT INTO Tutores (nombre, apellido, email, telefono, tipo, empresa) VALUES ('Elena', 'Díaz', 'elena.diaz@empresa.com', '000111222', 'Empresa',3);

INSERT INTO Practicas (id_alumno, id_tutor_instituto, id_tutor_empresa, fecha_inicio, fecha_fin, horas_totales, evaluacion_empresa, evaluacion_instituto) VALUES (1, 2, 3, '2024-03-14', '2024-06-15', 370, 'APTO', 'APTO');
INSERT INTO Practicas (id_alumno, id_tutor_instituto, id_tutor_empresa, fecha_inicio, fecha_fin, horas_totales) VALUES (2, 1, 3, '2025-03-21', '2025-07-01', 370);
INSERT INTO Practicas (id_alumno, id_tutor_instituto, id_tutor_empresa, fecha_inicio, fecha_fin, horas_totales) VALUES (3, 2, 4, '2025-03-20', '2025-08-10', 370);
INSERT INTO Practicas (id_alumno, id_tutor_instituto, id_tutor_empresa, fecha_inicio, fecha_fin, horas_totales) VALUES (4, 1, 3, '2025-03-24', '2025-09-05', 370);

INSERT INTO Habilidades (nombre) VALUES ('Java');
INSERT INTO Habilidades (nombre) VALUES ('Python');
INSERT INTO Habilidades (nombre) VALUES ('SQL');
INSERT INTO Habilidades (nombre) VALUES ('Gestión de Proyectos');

INSERT INTO Alumnos_Habilidades (id_alumno, id_habilidad) VALUES (1, 1);
INSERT INTO Alumnos_Habilidades (id_alumno, id_habilidad) VALUES (1, 2);
INSERT INTO Alumnos_Habilidades (id_alumno, id_habilidad) VALUES (1, 4);
INSERT INTO Alumnos_Habilidades (id_alumno, id_habilidad) VALUES (2, 2);
INSERT INTO Alumnos_Habilidades (id_alumno, id_habilidad) VALUES (3, 3);
INSERT INTO Alumnos_Habilidades (id_alumno, id_habilidad) VALUES (4, 4);

CREATE USER IF NOT EXISTS 'examen'@'localhost' IDENTIFIED BY 'examen';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, EXECUTE ON FCTs.* TO 'examen'@'localhost';

select*from Alumnos ;
select*from alumnoscontactos;