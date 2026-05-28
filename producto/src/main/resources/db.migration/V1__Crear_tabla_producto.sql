CREATE TABLE producto (
                          id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(35) NOT NULL,
                          descripcion VARCHAR(80) NOT NULL,
                          categoria VARCHAR(50) NOT NULL,
                          precio BIGINT NOT NULL,
                          stock INT NOT NULL,
                          disponible BOOLEAN NOT NULL
);