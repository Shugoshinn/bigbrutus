CREATE TABLE Sucursales (
                            id_sucursal BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(50) NOT NULL,
                            direccion VARCHAR(255) NOT NULL,
                            telefono INT NOT NULL,
                            comuna VARCHAR(100) NOT NULL,
                            horario_apertura TIME NOT NULL,
                            horario_cierre TIME NOT NULL,
                            activa TINYINT(1) DEFAULT 1,         -- En MySQL, BOOLEAN es un alias de TINYINT(1)
                            tipo VARCHAR(20) NOT NULL            -- Guarda el String del Enum (PARA_SERVIR, PARA_LLEVAR)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;