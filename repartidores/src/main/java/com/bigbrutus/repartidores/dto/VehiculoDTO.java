package com.bigbrutus.repartidores.dto;

import com.bigbrutus.repartidores.model.EstadoVehiculo;
import lombok.Data;

@Data
public class VehiculoDTO {

    private Long idVehiculo;
    private String patente;
    private String infoVehiculo;
    private EstadoVehiculo estado;
}
