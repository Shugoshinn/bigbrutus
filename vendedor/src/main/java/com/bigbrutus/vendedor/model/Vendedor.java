package com.bigbrutus.vendedor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    private String apellido;

    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String rut;

    private LocalDateTime fechaContratacion;
    private boolean activo;

    @PrePersist
    protected void onCreate() {
        this.fechaContratacion = LocalDateTime.now();
        this.activo = true;
    }
}