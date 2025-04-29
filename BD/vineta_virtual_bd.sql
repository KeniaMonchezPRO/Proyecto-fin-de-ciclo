-- Creacion y seleccion de la base de datos
CREATE DATABASE IF NOT EXISTS vineta_virtual CHARACTER SET utf8;
SET default_storage_engine = InnoDB;
use vineta_virtual;

-- Establecimiento del motor
set default_storage_engine = InnoDB;

-- Drops:
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS lectores;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS seguidores;
SET FOREIGN_KEY_CHECKS = 1;

-- Creación de tablas:
CREATE TABLE usuarios (
	# Atributos comunes:
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    foto_perfil VARCHAR(255),
    tipo_usuario ENUM('LECTOR', 'CLIENTE') NOT NULL,
     
     # Atributos del 'sistema'
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE lectores (
	id INT PRIMARY KEY,
    nombre_lector VARCHAR(255) NOT NULL,
    apellidos_lector VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
	FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE clientes (
	id INT PRIMARY KEY,
    nombre_cliente VARCHAR(255) NOT NULL,
    fecha_creacion_empresa DATE NOT NULL,
    descripcion VARCHAR(255),
    banner VARCHAR(255),
    tipo_cliente ENUM('EDITORIAL', 'DISTRIBUIDORA', 'CREADOR') DEFAULT 'creador',
    FOREIGN KEY (id) REFERENCES usuarios(id)
);

-- Tabla intermedia
CREATE TABLE seguidores (
	id_lector INT NOT NULL,
    id_cliente INT NOT NULL,
    fecha_seguimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_lector, id_cliente),
    FOREIGN KEY (id_lector) REFERENCES lectores(id),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

/*-- Datos de ejemplo
-- Para usuarios
INSERT INTO usuarios (id, nombre_usuario, email, contrasena, foto_perfil, tipo_usuario) VALUES
(1, 'Panini', 'hello@panini.com', 'hashed123', 'panini_logo.png', 'cliente'),
(2, 'ana_matrix', 'ana@matrix.com', 'hashed456', 'img_12345.jpg', 'lector'),
(3, 'charlesX', 'charles@indie.com', 'hashed789', 'DCIM_50021.jpg', 'lector'),
(4, 'Distribuidores Grace', 'graceDist@gd.es', 'hashed251', 'gracedist.png', 'cliente');

-- Para clientes
INSERT INTO clientes (id, nombre_cliente, fecha_creacion_empresa, descripcion, banner, tipo_cliente) VALUES
(1, 'Editorial Paninin', '1987-10-10', 'La editorial más grande de toda España!', 'banner_panini.jpg', 'editorial'),
(4, 'Distribuidores Grace', '1990-08-20', 'Distribuidores de cómics confiables desde 1990', 'banner10.jpg', 'distribuidora');

-- Para lectores
INSERT INTO lectores (id, nombre_lector, apellidos_lector, fecha_nacimiento) VALUES
(2, 'Ana', 'Lee X', '1997-05-30'),
(3, 'Carlos Javier', 'Pérez Castelo', '1980-01-01');

-- Para seguidores
INSERT INTO seguidores (id_lector, id_cliente) VALUES
(2, 1),  -- Ana sigue a Panini
(3, 1),  -- Carlos también sigue a Panini
(3, 4);  -- Carlos sigue a Distribuidores Grace*/
