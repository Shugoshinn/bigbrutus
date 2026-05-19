package com.bigbrutus.cliente.mapper;

import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setNombreCompleto(cliente.getNombre() + " " + cliente.getApellido());
        return dto;
    }

    public List<ClienteDTO> toDTOlist(List<Cliente> listado) {
        if (listado == null) return List.of(); // Buena práctica para evitar NullPointerException

        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}
