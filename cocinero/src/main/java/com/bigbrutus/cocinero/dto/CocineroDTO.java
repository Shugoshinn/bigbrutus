package com.bigbrutus.cocinero.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CocineroDTO {
    private Long idCocinero;
    private String nombreCompleto;
    private String specialty;
    private String telefono;
    private String estado;
    private LocalDate fechaContratacion;
    private Long idSucursal;
}