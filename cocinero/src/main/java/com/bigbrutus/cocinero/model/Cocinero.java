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

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "La especialidad no puede estar vacía")
    @Size(max = 50, message = "La especialidad no puede superar los 50 caracteres")
    @Column(nullable = false)
    private String specialty;

    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    private String telefono;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    @Column(nullable = false)
    private EstadoCocinero estado;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @NotNull(message = "La sucursal es obligatoria")
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