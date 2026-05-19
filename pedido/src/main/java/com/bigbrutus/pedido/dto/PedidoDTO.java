package com.bigbrutus.pedido.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonPropertyOrder({"id","fecha","estado","entrega","direccion","total","pago"})
public class PedidoDTO {

    private Long id_pedido;
    private LocalDate fecha_pedido;
    private String estado;
    private String tipo_entrega;
    private String direccion_entrega;
    private Long total;
    private String metodo_pago;
}
