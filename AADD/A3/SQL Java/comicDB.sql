-- 1. CREACIÓN DE LA BASE DE DATOS Y USO
DROP DATABASE ComicStore_BD;
CREATE DATABASE IF NOT EXISTS ComicStore_BD;
USE ComicStore_BD;

-- 2. CREACIÓN DE TABLAS

-- Tabla de Editoriales
CREATE TABLE Editorial (
    cod_editorial INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de Cómics
CREATE TABLE Comic (
    cod_comic INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    numero INT NOT NULL,
    precio DECIMAL(5, 2) NOT NULL, -- Precio en €
    cod_editorial INT,
    fecha_publicacion DATE,
    FOREIGN KEY (cod_editorial) REFERENCES Editorial(cod_editorial)
);

-- Tabla de Autores/Creadores
CREATE TABLE Creador (
    cod_creador INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla de Relación N:M entre Comic y Creador (quién lo escribió/dibujó)
CREATE TABLE Comic_Creador (
    cod_comic INT,
    cod_creador INT,
    rol VARCHAR(50) NOT NULL, -- Ej: 'Escritor', 'Dibujante', 'Colorista'
    PRIMARY KEY (cod_comic, cod_creador, rol),
    FOREIGN KEY (cod_comic) REFERENCES Comic(cod_comic) ON DELETE CASCADE,
    FOREIGN KEY (cod_creador) REFERENCES Creador(cod_creador) ON DELETE RESTRICT
);

-- 3. INSERCIÓN DE DATOS INICIALES

-- Editoriales
INSERT INTO Editorial (nombre) VALUES
('DC Comics'),
('Marvel'),
('Image Comics');

-- Creadores
INSERT INTO Creador (nombre) VALUES
('Alan Moore'),
('Frank Miller'),
('Jim Lee'),
('Stan Lee');

-- Cómics
INSERT INTO Comic (titulo, numero, precio, cod_editorial, fecha_publicacion) VALUES
('Watchmen', 1, 4.99, 1, '1986-09-01'), -- DC
('The Dark Knight Returns', 1, 5.99, 1, '1986-02-01'), -- DC
('Amazing Fantasy', 15, 12.50, 2, '1962-08-01'); -- Marvel

-- Relaciones Comic - Creador
-- Watchmen #1 (cod_comic=1)
INSERT INTO Comic_Creador (cod_comic, cod_creador, rol) VALUES
(1, 1, 'Escritor'); -- Alan Moore

-- The Dark Knight Returns #1 (cod_comic=2)
INSERT INTO Comic_Creador (cod_comic, cod_creador, rol) VALUES
(2, 2, 'Escritor'); -- Frank Miller

-- Función para obtener el precio de un cómic
DELIMITER //
CREATE FUNCTION getPrecioComic(comic_id INT)
RETURNS DECIMAL(5, 2)
DETERMINISTIC
BEGIN
    DECLARE comic_price DECIMAL(5, 2);
    
    SELECT precio INTO comic_price
    FROM Comic
    WHERE cod_comic = comic_id;
    
    RETURN comic_price;
END //
DELIMITER ;

-- Ejemplo de uso:
-- SELECT titulo, getPrecioComic(cod_comic) AS precio FROM Comic WHERE titulo LIKE 'Watchmen%';

-- Procedimiento para asignar un creador (autor/dibujante, etc.) a un cómic
DELIMITER //
CREATE PROCEDURE asignarCreadorComic(
    IN nombre_creador VARCHAR(100),
    IN titulo_comic VARCHAR(100),
    IN rol_creador VARCHAR(50)
)
BEGIN
    DECLARE creador_id INT;
    DECLARE comic_id INT;
    
    -- Obtener el código del creador
    SELECT cod_creador INTO creador_id
    FROM Creador
    WHERE nombre = nombre_creador;
    
    -- Obtener el código del cómic
    SELECT cod_comic INTO comic_id
    FROM Comic
    WHERE titulo = titulo_comic;
    
    -- Insertar la relación si ambos existen
    IF comic_id IS NOT NULL AND creador_id IS NOT NULL THEN
        -- Insertar la nueva relación (no se actualiza, ya que un cómic puede tener múltiples roles para un mismo creador)
        INSERT INTO Comic_Creador (cod_comic, cod_creador, rol)
        VALUES (comic_id, creador_id, rol_creador);
    ELSE
        SELECT 'Error: El cómic o el creador no existen.' AS Mensaje_Error;
    END IF;
END //
DELIMITER ;

-- Ejemplo de uso:
-- CALL asignarCreadorComic('Jim Lee','Watchmen','Dibujante');
SELECT
    C.nombre -- Nombre del creador
FROM
    Creador C, Comic_Creador CC, Comic CO -- Listado de las tres tablas
WHERE
    C.cod_creador = CC.cod_creador -- Condición de unión 1: Creador a la tabla de relación
    AND CC.cod_comic = CO.cod_comic   -- Condición de unión 2: Tabla de relación al Comic
    AND CO.titulo = 'Watchmen';
    
SELECT
    CO.titulo,
    C.nombre AS Creador,
    CC.rol AS Rol_Asignado
FROM
    Comic_Creador CC
JOIN
    Comic CO ON CC.cod_comic = CO.cod_comic
JOIN
    Creador C ON CC.cod_creador = C.cod_creador
WHERE
    CO.titulo = 'Spawn'; -- Sustituir por el título del cómic usado en la prueba
    
SELECT nombre FROM Creador;