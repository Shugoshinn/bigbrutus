package com.bigbrutus.repartidores.controller;

import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.model.TipoVehiculo;
import com.bigbrutus.repartidores.service.RepartidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/repartidores")
@Tag(name = "Controlador de repartidores", description = "Endpoints para la gestión de repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    // Lista de todos
    @GetMapping
    @Operation(
            summary = "Listar detalladamente todos los repartidores",
            description = "Obtiene una lista detallada de todos los repartidores registrados en la base de datos sin ningún filtro."
    )
    public ResponseEntity<?> listarTodo() {
        return ResponseEntity.ok(repartidorService.findAll());
    }


    // Buscar repartidor específico por Id FORMATO DTO
    @Operation(
            summary = "Buscar repartidor por ID",
            description = "Busca y retorna un repartidor específico en su formato DTO utilizando su número de ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Repartidor> buscarPorID(@PathVariable Long id){
        try {
            Repartidor repartidorDTO = repartidorService.findByID(id);
            return ResponseEntity.ok(repartidorDTO);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar
    @Operation(
            summary = "Registrar un nuevo repartidor",
            description = "Registra un nuevo repartidor en el sistema. Valida que los datos ingresados sean correctos."
    )
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Repartidor repartidor){
        Repartidor repartidorNuevo = repartidorService.save(repartidor);
        return new ResponseEntity<>(repartidorNuevo, HttpStatus.CREATED);
    }

    // Eliminar por Id
    @Operation(
            summary = "Eliminar un repartidor por ID",
            description = "Remueve un repartidor de la base de datos basándose en el identificador único proporcionado. Retorna 204 (No Content) si es exitoso."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        try {
            repartidorService.deleteById(id);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Actualizar un repartidor existente",
            description = "Permite modificar todos los datos de un repartidor buscandolo por su identificador."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Repartidor repartidor){
        try {
            return ResponseEntity.ok(repartidorService.update(id,repartidor));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todo con vehiculos incluidos
    @Operation(
            summary = "Listar todos los repartidores con su vehículo",
            description = "Obtiene una lista de todos los repartidores registrados con sus vehículos en la base de datos sin ningún filtro."
    )
    @GetMapping("/lista-detallada")
    public ResponseEntity<?> listaDetallada(){
        return ResponseEntity.ok(repartidorService.listaDetallada());
    }

    // Buscar repartidores por Tipo de Vehiculo
    @Operation(
            summary = "Listar todos los repartidores según su tipo de vehículo",
            description = "Obtiene una lista detallada de todos los repartidores registrados en la base de datos según el tipo de vehículo indicado. [MOTO, SCOOTER, AUTO]."
    )
    @GetMapping("/tipo-vehiculo/{tipo}")
    public ResponseEntity<?> buscarPorTipoVehiculo(@PathVariable TipoVehiculo tipo){
        return ResponseEntity.ok(repartidorService.listRepPorTipoVehiculo(tipo));
    }

    // Listar repartidores por Estado
    @Operation(
            summary = "Buscar todos los repartidores según su estado",
            description = "Obtiene una lista detallada de todos los repartidores registrados en la base de datos según el estado indicado. [DISPONIBLE, EN_REPARTO, CON_LICENCIA, EN_VACACIONES]"
    )
    @GetMapping("/listar-por-estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable EstadoRepartidor estado){
        return ResponseEntity.ok(repartidorService.findAllByEstado(estado));
    }
}
