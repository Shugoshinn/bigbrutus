package com.bigbrutus.sucursales.mapper;

import com.bigbrutus.sucursales.dto.SucursalDTO;
import com.bigbrutus.sucursales.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public SucursalDTO toDTO(Sucursal sucursal){

        if (sucursal == null) return null;

        SucursalDTO sucursalDTO = new SucursalDTO();

        sucursalDTO.setNombre(sucursal.getNombre());
        sucursalDTO.setDireccionCompleta(sucursal.getDireccion()+", "+sucursal.getComuna());
        sucursalDTO.setHorario("De "+sucursal.getHorario_apertura()+" hasta "+sucursal.getHorario_cierre());
        if (sucursal.getActiva() == true){
            sucursalDTO.setActiva("Activa");
        } else {
            sucursalDTO.setActiva("Inactiva");
        }

        return sucursalDTO;
    }
}
