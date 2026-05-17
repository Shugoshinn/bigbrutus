package com.bigbrutus.vehiculos.dto;

import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import lombok.Data;

@Data
public class VehiculoDTO {

    private Long idVehiculo;
    private String patente;
    private String infoVehiculo;
    private EstadoVehiculo estado;
}
