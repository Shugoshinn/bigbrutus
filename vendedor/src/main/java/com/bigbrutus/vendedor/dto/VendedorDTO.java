package com.bigbrutus.vendedor.dto;

import lombok.Data;

@Data
public class VendedorDTO {
    private Long id;
    private String nombreCompleto;
    private String rut;
    private boolean activo;
}