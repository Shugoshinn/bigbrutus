package com.bigbrutus.vendedor.exception.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VendedorDTO {
    private Long idVendedor;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private LocalDate fechaContratacion;
    private BigDecimal salario;
    private String estado;
    private Long idSucursal;
}