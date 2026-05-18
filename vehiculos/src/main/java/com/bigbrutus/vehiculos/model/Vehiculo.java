package com.bigbrutus.vehiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @NotBlank(message = "La patente no puede estar vacía!")
    @Column(nullable = false, unique = true, length = 8)
    @Size(min = 5, max = 10)
    @Pattern(
            regexp = "^[A-Z0-9-]+$",
            message = "La patente solo puede contener letras mayúsculas, números y guiones"
    )
    private String patente;

    @NotBlank(message = "La marca no puede estar vacía!")
    @Size(max = 50)
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío!")
    @Size(max = 50)
    private String modelo;

    @NotNull
    @Min(value = 1800, message = "El año debe ser válido! (Mayor a 1800)")
    @Max(value = 2100, message = "El año debe ser válido! (Menor a 2100)")
    private Integer anio;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Por favor, ingresar un tipo de vehículo! [MOTO, SCOOTER, AUTO]")
    private TipoVehiculo tipo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Por favor, ingresar un estado del vehículo! [DISPONIBLE, EN_REPARTO, MANTENCION, FUERA_DE_SERVICIO]")
    private EstadoVehiculo estado;

}