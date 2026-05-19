package com.bigbrutus.cocinero.service;

import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.exception.CocineroNotFoundException;
import com.bigbrutus.cocinero.mapper.CocineroMapper;
import com.bigbrutus.cocinero.model.Cocinero;
import com.bigbrutus.cocinero.repository.CocineroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CocineroService {

    @Autowired
    private CocineroRepository cocineroRepository;

    @Autowired
    private CocineroMapper cocineroMapper;

    public List<CocineroDTO> findAll() {
        return cocineroMapper.toDTOList(cocineroRepository.findAll());
    }

    public CocineroDTO findById(Long id) {
        Cocinero cocinero = cocineroRepository.findById(id)
                .orElseThrow(() -> new CocineroNotFoundException("Cocinero no encontrado con ID: " + id));
        return cocineroMapper.toDTO(cocinero);
    }

    public Cocinero save(Cocinero cocinero) {
        return cocineroRepository.save(cocinero);
    }

    public void delete(Long id) {
        if (!cocineroRepository.existsById(id)) {
            throw new CocineroNotFoundException("Cocinero no encontrado con ID: " + id);
        }
        cocineroRepository.deleteById(id);
    }

    public Cocinero update(Long id, Cocinero datos) {
        Cocinero existente = cocineroRepository.findById(id)
                .orElseThrow(() -> new CocineroNotFoundException("Cocinero no encontrado con ID: " + id));

        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setSpecialty(datos.getSpecialty());
        existente.setTelefono(datos.getTelefono());
        existente.setEstado(datos.getEstado());
        existente.setIdSucursal(datos.getIdSucursal());

        return cocineroRepository.save(existente);
    }
}