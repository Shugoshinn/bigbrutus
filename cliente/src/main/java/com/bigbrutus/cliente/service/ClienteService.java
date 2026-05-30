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

    // LISTAR TODOS
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    // BUSCAR POR ID
    public Cliente findById(Long id_cliente){
        return clienteRepository.findById(id_cliente).orElse(null);
    }

    // BUSCAR DTO POR ID
    public ClienteDTO findDTO(Long id){

        Cliente cliente = findById(id);

        return clienteMapper.toDTO(cliente);
    }

    // LISTA DTO
    public List<ClienteDTO> findDTOList(){
        return clienteMapper.toDTOlist(findAll());
    }

    // GUARDAR
    public Cliente save(Cliente c){
        return clienteRepository.save(c);
    }

    // ELIMINAR
    public void delete(Long id_cliente){
        clienteRepository.deleteById(id_cliente);
    }

    // ACTUALIZAR
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

    // =========================
    // MÉTODOS PERSONALIZADOS
    // =========================

    // BUSCAR POR EMAIL
    public ClienteDTO findByEmail(String email){

        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente == null){
            return null;
        }

        return clienteMapper.toDTO(cliente);
    }

    // BUSCAR POR NOMBRE
    public List<ClienteDTO> findByNombre(String nombre){

        List<Cliente> clientes = clienteRepository.findByNombre(nombre);

        return clienteMapper.toDTOlist(clientes);
    }

    // LISTAR CLIENTES ACTIVOS
    public List<ClienteDTO> findActivos(){

        List<Cliente> clientes = clienteRepository.findByActivo(true);

        return clienteMapper.toDTOlist(clientes);
    }

    // ACTUALIZAR SOLO ACTIVO
    public Cliente updateActivo(Long id_cliente, boolean activo){

        Cliente cliente = findById(id_cliente);

        if(cliente == null){
            return null;
        }

        cliente.setActivo(activo);

        return clienteRepository.save(cliente);
    }
}