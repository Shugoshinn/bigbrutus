CREATE TABLE pedido (
                        id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
                        fecha_pedido DATE NOT NULL,
                        estado VARCHAR(20) NOT NULL,
                        tipo_pedido VARCHAR(20) NOT NULL,
                        direccion_entrega TEXT NOT NULL,
                        total DECIMAL(10, 2) NOT NULL,
                        metodo_pago VARCHAR(20) NOT NULL,
                        id_cliente BIGINT NOT NULL,
                        id_vendedor BIGINT NOT NULL,
                        id_repartidor BIGINT NOT NULL,
                        id_sucursal BIGINT NOT NULL
);