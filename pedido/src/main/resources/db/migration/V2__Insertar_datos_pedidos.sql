INSERT INTO pedido (fecha_pedido, estado, tipo_pedido, direccion_entrega, total, metodo_pago, id_cliente, id_vendedor, id_repartidor, id_sucursal)
VALUES
    ('2026-05-28', 'EN_ESPERA', 'PREPARANDO', 'Avenida Recoleta 1234, Depto 4B', 15990.00, 'DEBITO', 1L, 1L, 1L, 1L),
    ('2026-05-29', 'EN_CAMINO', 'PREPARANDO', 'Avenida Pedro Donoso 456', 8500.50, 'EFECTIVO', 1L, 2L, 2L, 2L),
    ('2026-05-25', 'EN_CAMINO', 'ENTREGADO', 'Américo Vespucio 1011, Huechuraba', 24990.00, 'CREDITO', 1L, 2L, 3L, 3L),
    ('2026-05-30', 'EN_ESPERA', 'ENTREGADO', 'Avenida España 1680', 12400.00, 'DEBITO', 1L, 2L, 4L, 4L),
    ('2026-05-27', 'EN_CAMINO', 'PREPARANDO', 'Calle Einstein 789, Casa 2', 5990.00, 'EFECTIVO', 1L, 1L, 5L, 6L);