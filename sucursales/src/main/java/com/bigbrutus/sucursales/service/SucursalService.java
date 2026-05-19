package com.bigbrutus.sucursales.service;

import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // ***OTROS**

    // Listar Sucursales Activos o Inactivos
    public List<Sucursal> findAllByActiva(Boolean activa){
        return sucursalRepository.findAllByActiva(activa);
    }

    // Listar Sucursales por Comuna
    public List<Sucursal> findAllByComuna(String comuna){
        return sucursalRepository.findAllByComuna(comuna);
    }

    // Listar Sucursales según tipo [PARA_SERVIR,PARA_LLEVAR]
    public List<Sucursal> findAllByTipo(TipoSucursal tipo){
        return sucursalRepository.findAllByTipo(tipo);
    }
}
