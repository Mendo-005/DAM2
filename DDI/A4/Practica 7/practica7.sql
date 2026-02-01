-- ---------------------------------------------------------
-- 1. CREACIÓN DE LA BASE DE DATOS Y TABLAS (Ejercicio 2)
-- ---------------------------------------------------------
DROP DATABASE IF EXISTS JasperReportsDB;
CREATE DATABASE JasperReportsDB;
USE JasperReportsDB;

-- Tabla Productos
CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    -- Nota: Añadiremos las columnas de categoría e imagen más abajo
    -- para seguir el orden lógico de los ejercicios, aunque
    -- en la vida real se crearían aquí directamente.
);

-- Tabla Ventas
CREATE TABLE Sales (
    sale_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    sale_date DATE NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- ---------------------------------------------------------
-- 2. INSERCIÓN DE DATOS INICIALES (Ejercicio 2)
-- ---------------------------------------------------------
INSERT INTO Products (product_name, price) VALUES
('Producto A', 10.00),
('Producto B', 15.50),
('Producto C', 7.25);

INSERT INTO Sales (product_id, quantity, sale_date) VALUES
(1, 2, '2025-01-01'),
(2, 1, '2025-01-02'),
(3, 5, '2025-01-03'),
(1, 3, '2025-01-04');

-- ---------------------------------------------------------
-- 3. ACTUALIZACIÓN PARA AGRUPAMIENTO (Ejercicio 4)
-- Requisito: Añadir columna categoría y asignar 'blanca' o 'negra'
-- ---------------------------------------------------------
ALTER TABLE Products ADD COLUMN category VARCHAR(50);

-- Asignamos categorías
UPDATE Products SET category = 'blanca' WHERE product_name = 'Producto A';
UPDATE Products SET category = 'negra'  WHERE product_name = 'Producto B';
UPDATE Products SET category = 'blanca' WHERE product_name = 'Producto C';

-- Insertamos un producto extra para que la categoría 'negra' no esté sola
INSERT INTO Products (product_name, price, category) VALUES ('Producto D', 22.00, 'negra');

-- ---------------------------------------------------------
-- 4. ACTUALIZACIÓN PARA IMÁGENES (Ejercicio 5)
-- Requisito: Campo con URL o ruta de imagen
-- ---------------------------------------------------------
ALTER TABLE Products ADD COLUMN image_path VARCHAR(255);

-- Aquí pongo rutas de ejemplo. 
-- IMPORTANTE: En el ejercicio tendrás que cambiar esto por rutas reales de tu PC 
-- o URLs de internet si usas imágenes web.
UPDATE Products SET image_path = 'C:/imagenes/producto_a.jpg' WHERE product_name = 'Producto A';
UPDATE Products SET image_path = 'C:/imagenes/producto_b.jpg' WHERE product_name = 'Producto B';
UPDATE Products SET image_path = 'C:/imagenes/producto_c.jpg' WHERE product_name = 'Producto C';
UPDATE Products SET image_path = 'C:/imagenes/producto_d.jpg' WHERE product_name = 'Producto D';

-- ---------------------------------------------------------
-- 5. DATOS PARA GRÁFICOS (Ejercicio 7)
-- Requisito: Ventas con fechas de 2024 para el gráfico mensual
-- ---------------------------------------------------------
INSERT INTO Sales (product_id, quantity, sale_date) VALUES
(1, 10, '2024-01-15'), -- Enero 2024
(2, 5,  '2024-01-20'), -- Enero 2024
(1, 8,  '2024-02-10'), -- Febrero 2024
(3, 12, '2024-02-25'), -- Febrero 2024
(4, 3,  '2024-03-05'), -- Marzo 2024
(2, 7,  '2024-03-22'), -- Marzo 2024
(1, 15, '2024-04-12'), -- Abril 2024
(3, 20, '2024-05-30'); -- Mayo 2024

-- ---------------------------------------------------------
-- VERIFICACIÓN FINAL
-- ---------------------------------------------------------
SELECT * FROM Products;
SELECT * FROM Sales;

SELECT 
    MONTHNAME(sale_date) as Mes, 
    SUM(quantity) as TotalVendidos
FROM Sales
WHERE YEAR(sale_date) = 2024
GROUP BY MONTH(sale_date), MONTHNAME(sale_date)
ORDER BY MONTH(sale_date)