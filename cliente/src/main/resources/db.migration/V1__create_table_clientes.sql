CREATE TABLE clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100) NOT NULL UNIQUE,
    fecha_registro DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);