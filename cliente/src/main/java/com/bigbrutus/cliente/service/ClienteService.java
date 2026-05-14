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
    public Cliente save(Cliente c){
        return clienteRepository.save(c);
    }
    public void delete(Long id_cliente){
        clienteRepository.deleteById(id_cliente);
    }

    public Cliente update(Long id_cliente, Cliente cliente){
        Cliente clienteActualizar = clienteRepository.findById(id_cliente).orElse(null);
        if (clienteActualizar == null) return null;
        clienteActualizar.setNombre(cliente.getNombre());
        clienteActualizar.setApellido(cliente.getApellido());
        clienteActualizar.setTelefono(cliente.getTelefono());
        clienteActualizar.setEmail(cliente.getEmail());
        clienteActualizar.setFechaRegistro(cliente.getFechaRegistro());
        clienteActualizar.setActivo(cliente.isActivo());

        return clienteRepository.save(clienteActualizar);
    }



}
