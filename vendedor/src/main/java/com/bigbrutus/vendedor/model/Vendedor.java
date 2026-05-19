package com.bigbrutus.vendedor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedor")
    private Long idVendedor;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    private String apellido;

    @Size(max = 15)
    private String telefono;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @NotNull
    private BigDecimal salario;

    @NotBlank
    @Size(max = 20)
    private String estado;

    @NotNull
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @PrePersist
    protected void onCreate() {
        this.fechaContratacion = LocalDate.now();
        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
    }
}