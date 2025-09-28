CREATE DATABASE prueba_java;
USE prueba_java;
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(50)
);
INSERT INTO usuarios (nombre, email) VALUES
('Ana', 'ana@mail.com'),
('Juan', 'juan@mail.com');

select*from usuarios;