package com.bigbrutus.pedido.dto;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.MetodoPago;
import com.bigbrutus.pedido.model.TipoPedido;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PedidoDTO {

    private Long id_pedido;
    private LocalDate fecha_pedido;
    private String direccion_entrega;
    private EstadoPedido estado;
    private TipoPedido tipo_pedido;
    private BigDecimal total;
    private MetodoPago metodo_pago;

    private String nombre_cliente;
    private String nombre_vendedor;
    private String nombre_repartidor;
    private String nombre_sucursal;
}

