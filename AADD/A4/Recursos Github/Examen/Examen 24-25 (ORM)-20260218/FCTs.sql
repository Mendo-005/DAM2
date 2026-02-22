drop database if exists credenciales;
CREATE DATABASE IF NOT EXISTS credenciales CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE credenciales;
CREATE TABLE usuario (
    id_usuario SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE,
    email VARCHAR(150) NOT NULL UNIQUE );

CREATE TABLE perfil (
    id_perfil  SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(60) NOT NULL,
	usuario SMALLINT UNSIGNED,
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (usuario) REFERENCES usuario(id_usuario) ) ;

Select *from usuario;
Select *from perfil;
