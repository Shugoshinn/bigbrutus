package com.bigbrutus.sucursales.service;

import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Sucursal findByID(Long id) {
        return sucursalRepository.findById(id).orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id: " + id));
    }

    // Registrar un vehículo
    public Sucursal save(Sucursal sucursalNuevo) {
        return sucursalRepository.save(sucursalNuevo);
    }

    // Eliminar por Id un vehículo
    public void deleteById(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no encontrado con id: " + id);
        }
    }
}
