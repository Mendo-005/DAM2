CREATE TABLE cliente (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE pedido (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    importe DECIMAL(10,2) NOT NULL,
    cliente_id SMALLINT NOT NULL,
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
);


INSERT INTO cliente (nombre, email)
VALUES ('Jacobo Sanz','jsanz@empresa.es');

INSERT INTO pedido (fecha, importe, cliente_id)
VALUES ('2026-01-29', 100.5, 1);

INSERT INTO pedido (fecha, importe, cliente_id)
VALUES ('2026-01-30', 40.5, 1);

INSERT INTO pedido (fecha, importe, cliente_id)
VALUES ('2026-01-19', 10.0, 1);

/*
La aplicación debe implementar la siguiente lógica:
	a) Crear un nuevo cliente
	b) Añadir pedido a cliente
	c) Eliminar cliente junto con todos sus pedidos
	d) Eliminar un pedido de un cliente
	e) Generar un reporte personalizado de todos los clientes junto con el importe total gastado por cada uno:
			nombre 		| email 			| total_gastado
			Jacobo Sanz |jsanz@empresa.es	| 151.0
*/