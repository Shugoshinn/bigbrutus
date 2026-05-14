package com.bigbrutus.cliente.service;

import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.mapper.ClienteMapper;
import com.bigbrutus.cliente.model.Cliente;
import com.bigbrutus.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public ClienteDTO findbyId(Long id_cliente){
        Cliente cliente =clienteRepository.findById(id_cliente).orElse(null);
        return clienteMapper.toDTO(cliente);
    }



}
