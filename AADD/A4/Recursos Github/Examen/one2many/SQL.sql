drop database if exists elcorteingles;
CREATE DATABASE IF NOT EXISTS elcorteingles CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE elcorteingles;

CREATE TABLE cliente (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE pedido (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    importe DECIMAL(10,2) NOT NULL,
    cliente_id SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

select*from cliente;
select*from pedido;