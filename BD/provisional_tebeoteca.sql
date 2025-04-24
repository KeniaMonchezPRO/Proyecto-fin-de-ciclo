-- Creacion y selección de la base de datos
CREATE DATABASE IF NOT EXISTS tebeoteca CHARACTER SET utf8;
SET default_storage_engine = InnoDB;
use tebeoteca;

-- Establecimiento del motor
set default_storage_engine = InnoDB;

-- Drops:
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS clientes_usuarios;
SET FOREIGN_KEY_CHECKS = 1;

-- Creación de tablas:
-- Tabla: usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    foto_perfil VARCHAR(255),
        
    -- campos del 'sistema'
    fecha_creacion DATE NOT NULL,
    fecha_modificacion DATE NOT NULL
);

-- Tabla: clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) UNIQUE NOT NULL,
    email VARCHAR(150) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    logo VARCHAR(255),
    banner VARCHAR(255),
    num_seguidores INT NOT NULL,
    
    -- campos del 'sistema'
    tipo ENUM('editorial', 'distribuidora', 'creador') DEFAULT 'creador',
    fecha_creacion DATE NOT NULL,
    fecha_modificacion DATE NOT NULL
);

-- Tabla: usuarios_clientes
CREATE TABLE clientes_usuarios (
    id_cliente INT NOT NULL,
    id_usuario INT NOT NULL,
    
    PRIMARY KEY (id_cliente, id_usuario),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Datos de ejemplo
-- Para clientes
INSERT INTO clientes (nombre, tipo, email, contrasena, descripcion, logo, banner, num_seguidores, fecha_creacion, fecha_modificacion) VALUES
('Editorial Panini', 'editorial', 'panini@editorial.com', 'editor123', 'Nos encargamos de distribuir los cómics más famosos en español', 'planeta.png', 'banner.jpg', 3, '2024-03-15', '2024-03-15'),
('Distribuciones López', 'distribuidora', 'lopez@distrib.com', 'lopez456', 'La mejor distribuidora en toda España!', 'lopez.jpg', 'banner.jpg', 2, '2024-03-20', '2024-03-20'),
('Atlas Cómics', 'creador', 'atlas@comics.com', 'manu789', 'Creador de cómics independiente, centrado en el género de magia, medieval y un poco de sci-fi!', 'atlas-logo.png', 'atlas-banner.jpg', 1, '2024-04-24', '2024-04-24');

-- Para usuarios
INSERT INTO usuarios (nombre_usuario, nombre, apellidos, email, contrasena, foto_perfil, fecha_creacion, fecha_modificacion) VALUES
('juanX', 'Juan', 'Pérez', 'juanperez@gmail.com', '123456', 'juan.png', '2024-04-01', '2024-04-01'),
('laurita_63', 'Laura', 'Gómez', 'laura@oulook.com', 'contraseña123', 'laura.jpg', '2024-04-05', '2024-04-05'),
('charles.xavier.ruiz', 'Carlos', 'Ruiz', 'carlosr@gmail.com', 'abc123.', NULL, '2024-04-07', '2024-04-07');

-- Para clientes-usuarios
INSERT INTO clientes_usuarios (id_cliente, id_usuario) VALUES
(1,1),
(1,2),
(1,3),
(2,1),
(2,3),
(3,3);
