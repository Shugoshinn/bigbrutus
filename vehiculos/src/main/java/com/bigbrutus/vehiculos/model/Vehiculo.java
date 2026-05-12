package com.bigbrutus.vehiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vehiculo;

    @NotBlank
    private String patente;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    private int anio;

    @NotBlank
    private String tipo;

    @NotBlank
    private String estado;
}
