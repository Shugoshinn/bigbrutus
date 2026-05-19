package com.bigbrutus.vendedor.service;

import com.bigbrutus.vendedor.dto.VendedorDTO;
import com.bigbrutus.vendedor.exception.VendedorNotFoundException;
import com.bigbrutus.vendedor.mapper.VendedorMapper;
import com.bigbrutus.vendedor.model.Vendedor;
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

    public List<VendedorDTO> findAll() {
        return vendedorMapper.toDTOList(vendedorRepository.findAll());
    }

    public VendedorDTO findById(Long id) {
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new VendedorNotFoundException("Vendedor no encontrado con ID: " + id));
        return vendedorMapper.toDTO(vendedor);
    }

    public Vendedor save(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public void delete(Long id) {
        if (!vendedorRepository.existsById(id)) {
            throw new VendedorNotFoundException("Vendedor no encontrado con ID: " + id);
        }
        vendedorRepository.deleteById(id);
    }

    public Vendedor update(Long id, Vendedor datos) {
        Vendedor existente = vendedorRepository.findById(id)
                .orElseThrow(() -> new VendedorNotFoundException("Vendedor no encontrado con ID: " + id));

        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        existente.setSalario(datos.getSalario());
        existente.setEstado(datos.getEstado());
        existente.setIdSucursal(datos.getIdSucursal());

        return vendedorRepository.save(existente);
    }
}