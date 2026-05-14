package com.bigbrutus.cliente.mapper;

import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteDTO toDTO(Cliente cliente){
        if(cliente== null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setNombreCompleto(cliente.getNombre() + " "+ cliente.getApellido());
        return dto;
    }
}
