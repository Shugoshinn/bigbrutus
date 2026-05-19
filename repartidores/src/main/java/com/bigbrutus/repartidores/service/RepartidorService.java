package com.bigbrutus.repartidores.service;

import com.bigbrutus.repartidores.clients.VehiculoFeign;
import com.bigbrutus.repartidores.dto.RepartidorDTO;
import com.bigbrutus.repartidores.dto.VehiculoDTO;
import com.bigbrutus.repartidores.mapper.RepartidorMapper;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.repository.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Autowired
    private RepartidorMapper repartidorMapper;

    private VehiculoFeign vehiculoFeign;

    public List<Repartidor> findAll() {
        return repartidorRepository.findAll();
    }

    // Buscar repartidor por Id
    public Repartidor findByID(Long id) {
        return repartidorRepository.findById(id).orElseThrow(() -> new RuntimeException("Repartidor no encontrado con id: " + id));
    }

    // Registrar
    public Repartidor save(Repartidor repartidorNuevo) {
        return repartidorRepository.save(repartidorNuevo);
    }

    // Eliminar por Id
    public void deleteById(Long id) {
        if (!repartidorRepository.existsById(id)) {
            throw new RuntimeException("Repartidor no encontrado con id: " + id);
        }
    }

    // Actualizar repartidor existente
    public Repartidor update(Long id, Repartidor repartidorActualizado) {
        return repartidorRepository.findById(id)
                .map(repartidor -> {
                    repartidor.setNombre(repartidorActualizado.getNombre());
                    repartidor.setApellido(repartidorActualizado.getApellido());
                    repartidor.setTelefono(repartidorActualizado.getTelefono());
                    repartidor.setLicencia(repartidorActualizado.getLicencia());
                    repartidor.setEstado(repartidorActualizado.getEstado());

                    return repartidorRepository.save(repartidor);
                })
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con id: " + id));
    }

    public List<RepartidorDTO> listaDetallada(){
        List<Repartidor> listado = repartidorRepository.findAll();
        List<RepartidorDTO> listadoDTO = new ArrayList<>();

        for (Repartidor repartidor : listado) {
            RepartidorDTO dto = repartidorMapper.toDTO(repartidor);
            VehiculoDTO vehiculo = vehiculoFeign.buscarPorID(repartidor.getVehiculo());
            dto.setVehiculo(vehiculo.getInfoVehiculo());

            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<VehiculoDTO> listadoVehiculos(){
        return vehiculoFeign.listarVehiculos();
    }
    
}
