package com.bigbrutus.sucursales.service;

import com.bigbrutus.sucursales.dto.SucursalDTO;
import com.bigbrutus.sucursales.exception.BadNameException;
import com.bigbrutus.sucursales.mapper.SucursalMapper;
import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.exception.NotFoundException;
import com.bigbrutus.sucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private SucursalMapper sucursalMapper;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public SucursalDTO findByID(Long id) {
        Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(() -> new NotFoundException("Sucursal no encontrada con id: " + id));
        return sucursalMapper.toDTO(sucursal);
    }

    // Registrar un vehículo
    public Sucursal save(Sucursal sucursalNuevo) {
        if (sucursalNuevo.getNombre().length() < 3 || sucursalNuevo.getNombre().length() > 50){
            throw new BadNameException("El nombre ingresado no es válido");
        }
        return sucursalRepository.save(sucursalNuevo);
    }

    // Eliminar por Id un vehículo
    public void deleteById(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new NotFoundException("Sucursal no encontrada con id: " + id);
        } sucursalRepository.deleteById(id);
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
