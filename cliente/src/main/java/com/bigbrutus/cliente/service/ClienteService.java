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
    private ClienteMapper clienteMapper; // Instancia inyectada correctamente

    // 1. Devuelve la lista de entidades puras
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    // 2. Devuelve la entidad Cliente (Útil para consumo interno del Service)
    public Cliente findById(Long id_cliente){
        return clienteRepository.findById(id_cliente).orElse(null);
    }

    // 3. Busca la entidad y la transforma a DTO usando la instancia 'clienteMapper'
    public ClienteDTO findDTO(Long id){
        Cliente cliente = findById(id);
        return clienteMapper.toDTO(cliente);
    }

    // 4. Transforma la lista completa a DTO usando la instancia 'clienteMapper'
    public List<ClienteDTO> findDTOList(){
        return clienteMapper.toDTOlist(findAll());
    }

    public Cliente save(Cliente c){
        return clienteRepository.save(c);
    }

    public void delete(Long id_cliente){
        clienteRepository.deleteById(id_cliente);
    }

    public Cliente update(Long id_cliente, Cliente cliente){
        Cliente clienteActualizar = findById(id_cliente);
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