package com.bigbrutus.cocinero.dto;

import com.bigbrutus.cocinero.model.EstadoCocinero;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CocineroDTO {
    private Long idCocinero;
    private String nombreCompleto;
    private String especialidad;
    private String telefono;
    private EstadoCocinero estado;
    private LocalDate fechaContratacion;
    private Long idSucursal;
}