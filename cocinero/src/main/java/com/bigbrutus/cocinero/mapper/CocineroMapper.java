package com.bigbrutus.cocinero.mapper;

import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.model.Cocinero;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CocineroMapper {

    public CocineroDTO toDTO(Cocinero cocinero){

        if(cocinero == null) return null;

        CocineroDTO dto = new CocineroDTO();

        dto.setIdCocinero(cocinero.getIdCocinero());
        dto.setNombreCompleto(cocinero.getNombre() + " " + cocinero.getApellido());
        dto.setSpecialty(cocinero.getSpecialty());
        dto.setTelefono(cocinero.getTelefono());
        dto.setEstado(cocinero.getEstado());
        dto.setFechaContratacion(cocinero.getFechaContratacion());
        dto.setIdSucursal(cocinero.getIdSucursal());

        return dto;
    }

    public List<CocineroDTO> toDTOList(List<Cocinero> listado){

        if(listado == null) return List.of();

        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}