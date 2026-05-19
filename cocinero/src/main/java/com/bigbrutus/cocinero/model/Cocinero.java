package com.bigbrutus.cocinero.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cocineros")
public class Cocinero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cocinero")
    private Long idCocinero;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    private String apellido;

    @NotBlank
    @Size(max = 50)
    private String specialty;

    @Size(max = 15)
    private String telefono;

    @NotBlank
    @Size(max = 20)
    private String estado;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

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