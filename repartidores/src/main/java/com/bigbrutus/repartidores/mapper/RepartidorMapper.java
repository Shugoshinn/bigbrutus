package com.bigbrutus.repartidores.mapper;

import com.bigbrutus.repartidores.clients.VehiculoFeign;
import com.bigbrutus.repartidores.dto.RepartidorDTO;
import com.bigbrutus.repartidores.model.Repartidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepartidorMapper {

    @Autowired
    private VehiculoFeign vehiculoFeign;

    public RepartidorDTO toDTO(Repartidor r){
        if (r==null) return null;

        RepartidorDTO dto = new RepartidorDTO();

        dto.setId(r.getIdRepartidor());
        dto.setNombreCompleto(r.getNombre() + " " + r.getApellido());
        dto.setTelefono(r.getTelefono());
        dto.setEstado(r.getEstado());

        // Validar que no esté vacío
        if (r.getVehiculo() != null) {
            //Buscar el vehículo usando el ID que viene del repartidor
            var vehiculo = vehiculoFeign.buscarPorID(r.getVehiculo());

            if (vehiculo != null) {
                // 3. Asignar la patente
                dto.setVehiculo(vehiculo.getPatente());
                dto.setPatente(vehiculo.getPatente());
            }
        }

        return dto;

    }
}
