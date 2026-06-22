package com.bigbrutus.vehiculos.controller;

import com.bigbrutus.vehiculos.dto.VehiculoDTO;
import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import com.bigbrutus.vehiculos.model.Vehiculo;
import com.bigbrutus.vehiculos.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehiculos")
@Tag(name = "Controlador de Vehículos", description = "Endpoints para la gestión de vehículos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // *** ENDPOINTS CRUD BÁSICO ***

    // Lista de todos los vehículos FORMATO DTO
    @Operation(
            summary = "Listar todos los vehículos en formato DTO",
            description = "Obtiene una lista en formato DTO de todos los vehiculos registrados en la base de datos sin ningún filtro."
    )
    @GetMapping
    public ResponseEntity<?> listarTodoDTO() {
        return ResponseEntity.ok(vehiculoService.findAllDTO());
    }

    // Lista de todos los vehículos
    @Operation(
            summary = "Listar detalladamente todos los vehículos",
            description = "Obtiene una lista detallada de todos los vehiculos registrados en la base de datos sin ningún filtro."
    )
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(vehiculoService.findAll());
    }

    // Buscar vehículo específico por Id FORMATO DTO
    @Operation(
            summary = "Buscar vehículo por ID",
            description = "Busca y retorna un vehículo específico en su formato DTO utilizando su número de ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> buscarPorID(@PathVariable Long id){
        VehiculoDTO vehiculoDTO = vehiculoService.findByID(id);
        return ResponseEntity.ok(vehiculoDTO);
    }

    // Registrar un vehículo
    @Operation(
            summary = "Registrar un nuevo vehículo",
            description = "Registra un nuevo vehículo en el sistema. Valida que los datos ingresados sean correctos."
    )
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Vehiculo vehiculo){
        Vehiculo vehiculoNuevo = vehiculoService.save(vehiculo);
        return new ResponseEntity<>(vehiculoNuevo,HttpStatus.CREATED);
    }

    // Eliminar un vehículo por Id
    @Operation(
            summary = "Eliminar un vehículo por ID",
            description = "Remueve un vehículo de la base de datos basándose en el identificador único proporcionado. Retorna 204 (No Content) si es exitoso."
    )
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
    @Operation(
            summary = "Actualizar un vehículo existente",
            description = "Permite modificar todos los datos de un vehículo buscandolo por su identificador."
    )
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
    @Operation(
            summary = "Buscar vehículo por patente",
            description = "Busca y retorna un vehículo específico en su formato DTO utilizando su patente."
    )
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
    @Operation(
            summary = "Listar todos los vehículos según su estado",
            description = "Obtiene una lista detallada de todos los vehiculos registrados en la base de datos según el estado indicado. [DISPONIBLE, EN_REPARTO, MANTENCION, FUERA_DE_SERVICIO]."
    )
    @GetMapping("/listar-por-estado/{estado}")
    public ResponseEntity<?> listarPorEstadoDTO(@PathVariable EstadoVehiculo estado) {
        return ResponseEntity.ok(vehiculoService.findAllByEstado(estado));
    }

    // Listar por tipo
    @Operation(
            summary = "Listar todos los vehículos según su tipo",
            description = "Obtiene una lista detallada de todos los vehiculos registrados en la base de datos según el tipo indicado. [MOTO, SCOOTER, AUTO]."
    )
    @GetMapping("/listar-por-tipo/{tipo}")
    public ResponseEntity<?> listarPorTipoDTO(@PathVariable TipoVehiculo tipo) {
        return ResponseEntity.ok(vehiculoService.findAllByTipoDTO(tipo));
    }

    // Actualizar Solo Estado
    @Operation(
            summary = "Actualizar el estado de un vehículo existente",
            description = "Permite modificar solo el 'estado' de un vehículo buscandolo por su identificador."
    )
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
