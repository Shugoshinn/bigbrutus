CREATE TABLE pedido
(
    id_pedido                  bigint auto_increment primary key,
    fecha_pedidio              date not null,
    estado                     varchar(20) not null,
    tipo_entrega               varchar(20) not null,
    direccion_entrega          varchar(80) not null,
    total                      numeric not null,
    metodo_pago                varchar(20) not null,
    id_cliente                 bigint auto_increment not null,
    id_vendedor                bigint auto_increment not null,
    id_repartidor              bigint auto_increment not null,
    id_sucursal                bigint auto_increment not null
)