CREATE TABLE repartidores (
                              id_repartidor BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nombre VARCHAR(50) NOT NULL,
                              apellido VARCHAR(100) NOT NULL,
                              telefono INT NOT NULL,
                              licencia VARCHAR(50) NOT NULL,
                              estado VARCHAR(30) NOT NULL,
                              id_vehiculo BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;