CREATE TABLE cocineros (
   id_cocinero BIGINT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(50) NOT NULL,
   apellido VARCHAR(50) NOT NULL,
   especialidad VARCHAR(50) NOT NULL,
   telefono VARCHAR(15),
   estado VARCHAR(20) NOT NULL,
   fecha_contratacion DATE NOT NULL,
   id_sucursal BIGINT NOT NULL
);