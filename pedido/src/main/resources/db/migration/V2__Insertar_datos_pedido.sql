INSERT INTO pedido (fecha_pedido, estado, tipo_pedido, direccion_entrega, total, metodo_pago, id_cliente, id_vendedor, id_repartidor, id_sucursal)
VALUES
    ('2026-05-28', 'EN_ESPERA', 'PREPARANDO', 'Avenida Recoleta 1234, Depto 4B', 15990.00, 'DEBITO', 1, 1, 1, 1),
    ('2026-05-29', 'EN_CAMINO', 'PREPARANDO', 'Avenida Pedro Donoso 456', 8500.50, 'EFECTIVO', 1, 2, 2, 2),
    ('2026-05-25', 'EN_CAMINO', 'ENTREGADO', 'Américo Vespucio 1011, Huechuraba', 24990.00, 'CREDITO', 1, 2, 3, 3),
    ('2026-05-30', 'EN_ESPERA', 'ENTREGADO', 'Avenida España 1680', 12400.00, 'DEBITO', 1, 2, 4, 4),
    ('2026-05-27', 'EN_CAMINO', 'PREPARANDO', 'Calle Einstein 789, Casa 2', 5990.00, 'EFECTIVO', 1, 1, 5, 6);