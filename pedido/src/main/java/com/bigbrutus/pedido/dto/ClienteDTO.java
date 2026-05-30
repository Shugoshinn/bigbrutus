package com.bigbrutus.pedido.dto;

import lombok.Data;

@Data
public class ClienteDTO {

    private Long idCliente;
    private String nombreCompleto;
    private String telefono;
    private String email;
}
