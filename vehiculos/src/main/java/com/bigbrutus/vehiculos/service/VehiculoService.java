package com.bigbrutus.vehiculos.service;

import com.bigbrutus.vehiculos.dto.VehiculoDTO;
import com.bigbrutus.vehiculos.exception.AnioNoValidoException;
import com.bigbrutus.vehiculos.exception.NotFoundException;
import com.bigbrutus.vehiculos.exception.RequestException;
import com.bigbrutus.vehiculos.mapper.VehiculoMapper;
import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import com.bigbrutus.vehiculos.model.Vehiculo;
import com.bigbrutus.vehiculos.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private VehiculoMapper vehiculoMapper;

    // *** MÉTODOS CRUD BÁSICO ***

    public List<Vehiculo> findAll(){
        return vehiculoRepository.findAll();
    }

    // Lista de todos los vehículos FORMATO DTO
    public List<VehiculoDTO> findAllDTO(){
        List<Vehiculo> listaVehiculos = vehiculoRepository.findAll();
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo v : listaVehiculos) {
            listaVehiculosDTO.add(vehiculoMapper.toDTO(v));
        }
        return listaVehiculosDTO;
    }

    // Buscar  por Id vehículo específico FORMATO DTO
    public VehiculoDTO findByID(Long id){
         Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow(()-> new NotFoundException("Vehículo no encontrado con id: "+ id));
         return vehiculoMapper.toDTO(vehiculo);
    }

    // Registrar un vehículo
    public Vehiculo save(Vehiculo vehiculoNuevo){
        if (vehiculoNuevo.getAnio() < 1800 || vehiculoNuevo.getAnio() > Year.now().getValue()){
            throw new AnioNoValidoException("El año ingresado no es válido");
        }
        return vehiculoRepository.save(vehiculoNuevo);
    }

    // Eliminar por Id un vehículo
    public void deleteById(Long id){
        if (!vehiculoRepository.existsById(id)){
            throw new NotFoundException("Vehículo no encontrado con id: "+ id);
        }
        vehiculoRepository.deleteById(id);
    }

    // Actualizar un vehículo por Completo, buscado por Id.
    public Vehiculo update(Long id,Vehiculo vehiculoActualizado){
        return vehiculoRepository.findById(id)
                .map(vehiculo ->{
                    vehiculo.setPatente(vehiculoActualizado.getPatente());
                    vehiculo.setMarca(vehiculoActualizado.getMarca());
                    vehiculo.setModelo(vehiculoActualizado.getModelo());
                    vehiculo.setAnio(vehiculoActualizado.getAnio());
                    vehiculo.setTipo(vehiculoActualizado.getTipo());
                    vehiculo.setEstado(vehiculoActualizado.getEstado());

                    return vehiculoRepository.save(vehiculo);
                }).orElseThrow(() -> new NotFoundException("Vehículo no encontrado con id: "+ id));
    }

    // *** MÉTODOS PERSONALIZADOS ***

    // Buscar por patente
    public VehiculoDTO findByPatente(String patente){
        Vehiculo vehiculo = vehiculoRepository.findByPatente(patente).orElseThrow(()-> new NotFoundException("Vehículo no encontrado con patente: "+ patente));
        return vehiculoMapper.toDTO(vehiculo);
    }

    // Listar por Tipo
    public List<Vehiculo> findAllByTipo(TipoVehiculo tipo){
        return vehiculoRepository.findAllByTipo(tipo);
    }

    // Actualizar Solo Estado
    public Vehiculo updateEstado(Long id, EstadoVehiculo estado){
        return vehiculoRepository.findById(id)
                .map(vehiculo ->{
                    vehiculo.setEstado(estado);
                    return vehiculoRepository.save(vehiculo);
                }).orElseThrow(() -> new NotFoundException("Vehículo no encontrado con id: "+ id));
    }
    // Listar por Estado FORMATO DTO
    public List<VehiculoDTO> findAllByEstado(EstadoVehiculo estado){
        List<Vehiculo> listaVehiculos = vehiculoRepository.findAllByEstado(estado);
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo v : listaVehiculos) {
            listaVehiculosDTO.add(vehiculoMapper.toDTO(v));
        }
        return listaVehiculosDTO;
    }


    // Listar por tipo FORMATO DTO
    public List<VehiculoDTO> findAllByTipoDTO(TipoVehiculo tipo){
        List<Vehiculo> listaVehiculos = vehiculoRepository.findAllByTipo(tipo);
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo v : listaVehiculos) {
            listaVehiculosDTO.add(vehiculoMapper.toDTO(v));
        }
        return listaVehiculosDTO;
    }
}
