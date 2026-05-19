package com.bigbrutus.repartidores.dto;

import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.EstadoVehiculo;
import lombok.Data;

@Data
public class RepartidorDTO {

    private Long id;

    private String nombreCompleto;

    private Integer telefono;

    private EstadoRepartidor estado;

    private String vehiculo;

    private String sucursal;


}
