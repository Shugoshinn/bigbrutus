package com.bigbrutus.cocinero.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cocineros")
public class Cocinero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCocinero;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String apellido;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String especialidad;

    @Size(max = 15)
    private String telefono;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCocinero estado;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @NotNull
    @Column(name = "id_sucursal", nullable = false)
    private Long idSucursal;

    @PrePersist
    protected void onCreate() {
        this.fechaContratacion = LocalDate.now();

        if (this.estado == null) {
            this.estado = EstadoCocinero.ACTIVO;
        }
    }
}