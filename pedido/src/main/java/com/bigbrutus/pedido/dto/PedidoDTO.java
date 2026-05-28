package com.bigbrutus.pedido.dto;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonPropertyOrder({"id","entrega","direccion","total","pago"})
public class PedidoDTO {

    private Long id_pedido;
    private String direccion_entrega;
    private EstadoPedido estado;
    private Long total;
    private String metodo_pago;

    private Long id_cliente;
    private Long id_vendedor;
    private Long id_repartidor;
    private Long id_sucursal;
}
