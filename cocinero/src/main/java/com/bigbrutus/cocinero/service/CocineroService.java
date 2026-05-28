package com.bigbrutus.cocinero.service;

import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.exception.CocineroNotFoundException;
import com.bigbrutus.cocinero.mapper.CocineroMapper;
import com.bigbrutus.cocinero.model.Cocinero;
import com.bigbrutus.cocinero.model.EstadoCocinero;
import com.bigbrutus.cocinero.repository.CocineroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.bigbrutus.cocinero.model.EstadoCocinero;

@Service
public class CocineroService {

    @Autowired
    private CocineroRepository cocineroRepository;

    @Autowired
    private CocineroMapper cocineroMapper;

    // LISTAR TODOS
    public List<CocineroDTO> findDTOList() {

        return cocineroMapper.toDTOList(cocineroRepository.findAll());
    }

    // BUSCAR ENTIDAD POR ID
    public Cocinero findById(Long id) {

        return cocineroRepository.findById(id)
                .orElseThrow(() ->
                        new CocineroNotFoundException(
                                "Cocinero no encontrado con ID: " + id
                        ));
    }

    // BUSCAR DTO POR ID
    public CocineroDTO findDTO(Long id) {

        return cocineroMapper.toDTO(findById(id));
    }

    // GUARDAR
    public Cocinero save(Cocinero cocinero) {

        return cocineroRepository.save(cocinero);
    }

    // ELIMINAR
    public void delete(Long id) {

        if (!cocineroRepository.existsById(id)) {

            throw new CocineroNotFoundException(
                    "Cocinero no encontrado con ID: " + id
            );
        }

        cocineroRepository.deleteById(id);
    }

    // ACTUALIZAR
    public Cocinero update(Long id, Cocinero datos) {

        Cocinero existente = findById(id);

        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setEspecialidad(datos.getEspecialidad());
        existente.setTelefono(datos.getTelefono());
        existente.setEstado(datos.getEstado());
        existente.setIdSucursal(datos.getIdSucursal());

        return cocineroRepository.save(existente);
    }

    // =========================
    // MÉTODOS PERSONALIZADOS
    // =========================

    // BUSCAR POR ESPECIALIDAD
    public List<CocineroDTO> findBySpecialty(String specialty){

        return cocineroMapper.toDTOList(
                cocineroRepository.findByEspecialidad(specialty)
        );
    }

    // BUSCAR POR ESTADO
    public List<CocineroDTO> findByEstado(String estado){
        return cocineroMapper.toDTOList(
                cocineroRepository.findByEstado(EstadoCocinero.valueOf(estado))
        );
    }



    // BUSCAR POR SUCURSAL
    public List<CocineroDTO> findBySucursal(Long idSucursal){

        return cocineroMapper.toDTOList(
                cocineroRepository.findByIdSucursal(idSucursal)
        );
    }

    // ACTUALIZAR SOLO ESTADO
    public Cocinero updateEstado(Long id, EstadoCocinero estado){

        Cocinero cocinero = findById(id);

        cocinero.setEstado(estado);

        return cocineroRepository.save(cocinero);
    }
}