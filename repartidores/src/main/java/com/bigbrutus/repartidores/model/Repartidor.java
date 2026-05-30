package com.bigbrutus.repartidores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Repartidores")
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repartidor")
    private Long idRepartidor;

    @NotBlank(message = "El nombre no puede estar vacío!")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacía!")
    private String apellido;

    @NotNull(message = "El telefono no puede estar vacío!")
    private Integer telefono;

    @NotBlank(message = "La licencia no puede estar vacía!")
    private String licencia;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Por favor, ingresar un estado del repartidor! [DISPONIBLE, EN_REPARTO, CON_LICENCIA, EN_VACACIONES]")
    private EstadoRepartidor estado;

    @NotNull(message = "Id de vehiculo no puede estar vacio")
    @Column(name = "id_vehiculo")
    private Long vehiculo;


}
