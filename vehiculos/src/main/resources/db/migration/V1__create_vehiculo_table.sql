CREATE TABLE vehiculo (
                          id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
                          patente VARCHAR(10) NOT NULL UNIQUE,
                          marca VARCHAR(50) NOT NULL,
                          modelo VARCHAR(50) NOT NULL,
                          anio INT NOT NULL,
                          tipo VARCHAR(20) NOT NULL,
                          estado VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;