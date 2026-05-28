package com.bigbrutus.repartidores.service;

import com.bigbrutus.repartidores.clients.VehiculoFeign;
import com.bigbrutus.repartidores.dto.RepartidorDTO;
import com.bigbrutus.repartidores.dto.VehiculoDTO;
import com.bigbrutus.repartidores.exception.NotFoundException;
import com.bigbrutus.repartidores.mapper.RepartidorMapper;
import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.model.TipoVehiculo;
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

    @Autowired
    private VehiculoFeign vehiculoFeign;

    public List<Repartidor> findAll() {
        return repartidorRepository.findAll();
    }

    // Buscar repartidor por Id
    public Repartidor findByID(Long id) {
        return repartidorRepository.findById(id).orElseThrow(() -> new NotFoundException("Repartidor no encontrado con id: " + id));
    }

    // Registrar
    public Repartidor save(Repartidor repartidorNuevo) {
        return repartidorRepository.save(repartidorNuevo);
    }

    // Eliminar por Id
    public void deleteById(Long id) {
        if (!repartidorRepository.existsById(id)) {
            throw new NotFoundException("Repartidor no encontrado con id: " + id);
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
                .orElseThrow(() -> new NotFoundException("Repartidor no encontrado con id: " + id));
    }

    // Listar todo con vehiculos incluidos
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

    // Buscar repartidores por Tipo de Vehiculo
    public List<RepartidorDTO> listRepPorTipoVehiculo(TipoVehiculo tipo){

        List<RepartidorDTO> listadoFinal = new ArrayList<>();
        List<VehiculoDTO> listadoVehiculoDTO = vehiculoFeign.findAllByTipo(tipo);

        if (listadoVehiculoDTO.isEmpty()){
            return listadoFinal;
        }

        // Extraer los IDs de los vehículos mientras se guardan en una lista
        List<Long> idsVehiculos = listadoVehiculoDTO.stream().map(VehiculoDTO::getIdVehiculo).toList();

        // Buscar los repartidores asociados a los IDS de vehiculos
        List<Repartidor> repartidoresEncontrados = repartidorRepository.findByVehiculoIn(idsVehiculos);

        // Transformar los Repartidores a DTOs
        for (Repartidor r : repartidoresEncontrados){
            RepartidorDTO dto = repartidorMapper.toDTO(r);

            // Buscar si vehiculos de la lista vehiculos que coincidan con la del repartidor
            for (VehiculoDTO v : listadoVehiculoDTO){
                if (v.getIdVehiculo().equals(r.getVehiculo())){
                    dto.setVehiculo(v.getInfoVehiculo());
                    break;
                }
            }

            listadoFinal.add(dto);
        }

        return listadoFinal;
    }

    // Buscar repartidores por Estado
    public List<RepartidorDTO> findAllByEstado(EstadoRepartidor estado) {
        List<Repartidor> listaRepartidores = repartidorRepository.findAllByEstado(estado);
        List<RepartidorDTO> listaRepartidoresDTO = new ArrayList<>();
        for (Repartidor r : listaRepartidores) {
            listaRepartidoresDTO.add(repartidorMapper.toDTO(r));
        }
        return listaRepartidoresDTO;
    }
}
