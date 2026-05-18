package com.bigbrutus.vehiculos.controller;

import com.bigbrutus.vehiculos.dto.VehiculoDTO;
import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import com.bigbrutus.vehiculos.model.Vehiculo;
import com.bigbrutus.vehiculos.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // *** ENDPOINTS CRUD BÁSICO ***

    // Lista de todos los vehículos FORMATO DTO
    @GetMapping
    public ResponseEntity<?> listarTodoDTO() {
        return ResponseEntity.ok(vehiculoService.findAllDTO());
    }

    // Lista de todos los vehículos
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(vehiculoService.findAll());
    }

    // Buscar vehículo específico por Id FORMATO DTO
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> buscarPorID(@PathVariable Long id){
        try {
            VehiculoDTO vehiculoDTO = vehiculoService.findByID(id);
            return ResponseEntity.ok(vehiculoDTO);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar un vehículo
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Vehiculo vehiculo){
        Vehiculo vehiculoNuevo = vehiculoService.save(vehiculo);
        return new ResponseEntity<>(vehiculoNuevo,HttpStatus.CREATED);
    }

    // Eliminar un vehículo por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        try {
            vehiculoService.deleteById(id);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar un vehículo por Completo, buscado por Id.
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Vehiculo vehiculo){
        try {
            Vehiculo vehiculoActualizado = vehiculoService.update(id,vehiculo);
            return ResponseEntity.ok(vehiculoActualizado);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // *** ENDPOINTS PERSONALIZADOS ***

    // Buscar por patente
    @GetMapping("/patente/{patente}")
    public ResponseEntity<?> buscarPorPatente(@PathVariable String patente){
        try {
            VehiculoDTO vehiculoDTO = vehiculoService.findByPatente(patente);
            return ResponseEntity.ok(vehiculoDTO);

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Listar por Estado
    @GetMapping("/listar-por-estado/{estado}")
    public ResponseEntity<?> listarPorEstadoDTO(@PathVariable EstadoVehiculo estado) {
        return ResponseEntity.ok(vehiculoService.findAllByEstado(estado));
    }

    // Listar por tipo
    @GetMapping("/listar-por-tipo/{tipo}")
    public ResponseEntity<?> listarPorTipoDTO(@PathVariable TipoVehiculo tipo) {
        return ResponseEntity.ok(vehiculoService.findAllByTipo(tipo));
    }

    // Actualizar Solo Estado
    @PatchMapping("/{id}/{estado}")
    public ResponseEntity<Vehiculo> actualizarEstado(@PathVariable Long id,
                                                     @PathVariable EstadoVehiculo estado){
        try {
            Vehiculo actualizado = vehiculoService.updateEstado(id,estado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


}
