package com.bigbrutus.sucursales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @NotBlank(message = "El nombre no puede estar vacío!")
    private String nombre;

    @NotBlank(message = "La direccion no puede estar vacía!")
    private String direccion;

    @NotNull(message = "El telefono no puede estar vacío!")
    private Integer telefono;

    @NotBlank(message = "La comuna no puede estar vacía!")
    private String comuna;

    @NotNull(message = "El horario no puede estar vacío!")
    private Time horario_apertura;

    @NotNull(message = "El horario no puede estar vacío!")
    private Time horario_cierre;

    private Boolean activa;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Por favor, ingresar un tipo de sucursal! [PARA_SERVIR,PARA_LLEVAR]")
    private TipoSucursal tipo;
}
