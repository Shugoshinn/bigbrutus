package com.bigbrutus.vendedor.service;

import com.bigbrutus.vendedor.dto.VendedorDTO;
import com.bigbrutus.vendedor.exception.VendedorNotFoundException;
import com.bigbrutus.vendedor.mapper.VendedorMapper;
import com.bigbrutus.vendedor.model.Vendedor;
import com.bigbrutus.vendedor.model.EstadoVendedor;
import com.bigbrutus.vendedor.repository.VendedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorMapper vendedorMapper;

    // LISTAR TODOS
    public List<VendedorDTO> findDTOList() {

        return vendedorMapper.toDTOList(
                vendedorRepository.findAll()
        );
    }

    // BUSCAR ENTIDAD POR ID
    public Vendedor findById(Long id) {

        return vendedorRepository.findById(id)
                .orElseThrow(() ->
                        new VendedorNotFoundException(
                                "Vendedor no encontrado con ID: " + id
                        ));
    }

    // BUSCAR DTO POR ID
    public VendedorDTO findDTO(Long id) {

        return vendedorMapper.toDTO(findById(id));
    }

    // GUARDAR
    public Vendedor save(Vendedor vendedor) {

        return vendedorRepository.save(vendedor);
    }

    // ELIMINAR
    public void delete(Long id) {

        if (!vendedorRepository.existsById(id)) {

            throw new VendedorNotFoundException(
                    "Vendedor no encontrado con ID: " + id
            );
        }

        vendedorRepository.deleteById(id);
    }

    // ACTUALIZAR
    public Vendedor update(Long id, Vendedor datos) {

        Vendedor existente = findById(id);

        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        existente.setSalario(datos.getSalario());
        existente.setEstado(datos.getEstado());
        existente.setIdSucursal(datos.getIdSucursal());

        return vendedorRepository.save(existente);
    }

    // =========================
    // MÉTODOS PERSONALIZADOS
    // =========================

    // BUSCAR POR EMAIL
    public VendedorDTO findByEmail(String email){

        Vendedor vendedor = vendedorRepository.findByEmail(email);

        if(vendedor == null){
            return null;
        }

        return vendedorMapper.toDTO(vendedor);
    }

    // BUSCAR POR ESTADO
    public List<VendedorDTO> findByEstado(EstadoVendedor estado){

        return vendedorMapper.toDTOList(
                vendedorRepository.findByEstado(estado)
        );
    }

    // BUSCAR POR SUCURSAL
    public List<VendedorDTO> findBySucursal(Long idSucursal){

        return vendedorMapper.toDTOList(
                vendedorRepository.findByIdSucursal(idSucursal)
        );
    }

    // ACTUALIZAR SOLO ESTADO
    public Vendedor updateEstado(Long id, EstadoVendedor estado){

        Vendedor vendedor = findById(id);

        vendedor.setEstado(estado);

        return vendedorRepository.save(vendedor);
    }
}