package com.bigbrutus.vendedor.mapper;

import com.bigbrutus.vendedor.dto.VendedorDTO;
import com.bigbrutus.vendedor.model.Vendedor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VendedorMapper {

    public VendedorDTO toDTO(Vendedor vendedor) {
        if (vendedor == null) return null;
        VendedorDTO dto = new VendedorDTO();
        dto.setId(vendedor.getId());
        dto.setNombreCompleto(vendedor.getNombre() + " " + vendedor.getApellido());
        dto.setRut(vendedor.getRut());
        dto.setActivo(vendedor.isActivo());
        return dto;
    }

    public List<VendedorDTO> toDTOList(List<Vendedor> listado) {
        if (listado == null) return List.of();
        return listado.stream().map(this::toDTO).toList();
    }
}