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
        dto.setIdVendedor(vendedor.getIdVendedor());
        dto.setNombreCompleto(vendedor.getNombre() + " " + vendedor.getApellido());
        dto.setTelefono(vendedor.getTelefono());
        dto.setEmail(vendedor.getEmail());
        dto.setFechaContratacion(vendedor.getFechaContratacion());
        dto.setSalario(vendedor.getSalario());
        dto.setEstado(vendedor.getEstado());
        dto.setIdSucursal(vendedor.getIdSucursal());
        return dto;
    }

    public List<VendedorDTO> toDTOList(List<Vendedor> listado) {
        if (listado == null) return List.of();
        return listado.stream().map(this::toDTO).toList();
    }
}