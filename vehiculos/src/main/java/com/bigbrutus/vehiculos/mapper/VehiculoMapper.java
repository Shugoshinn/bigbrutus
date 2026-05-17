package com.bigbrutus.vehiculos.mapper;

import com.bigbrutus.vehiculos.dto.VehiculoDTO;
import com.bigbrutus.vehiculos.model.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoDTO toDTO(Vehiculo vehiculo){

        if (vehiculo == null) return null;

        VehiculoDTO vehiculoDTO = new VehiculoDTO();

        vehiculoDTO.setIdVehiculo(vehiculo.getId_vehiculo());
        vehiculoDTO.setPatente(vehiculo.getPatente());
        vehiculoDTO.setInfoVehiculo(vehiculo.getTipo()+" marca "+vehiculo.getMarca()+ ", modelo "+vehiculo.getModelo());
        vehiculoDTO.setEstado(vehiculo.getEstado());

        return vehiculoDTO;
    }
}
