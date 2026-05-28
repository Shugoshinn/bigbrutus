package com.bigbrutus.repartidores.controller;

import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.model.TipoVehiculo;
import com.bigbrutus.repartidores.service.RepartidorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    // Lista de todos
    @GetMapping
    public ResponseEntity<?> listarTodo() {
        return ResponseEntity.ok(repartidorService.findAll());
    }


    // Buscar vehículo específico por Id FORMATO DTO
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
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Repartidor repartidor){
        Repartidor repartidorNuevo = repartidorService.save(repartidor);
        return new ResponseEntity<>(repartidorNuevo, HttpStatus.CREATED);
    }

    // Eliminar por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        try {
            repartidorService.deleteById(id);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Repartidor repartidor){
        try {
            return ResponseEntity.ok(repartidorService.update(id,repartidor));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todo con vehiculos incluidos
    @GetMapping("/lista-detallada")
    public ResponseEntity<?> listaDetallada(){
        return ResponseEntity.ok(repartidorService.listaDetallada());
    }

    // Buscar repartidores por Tipo de Vehiculo
    @GetMapping("/tipo-vehiculo/{tipo}")
    public ResponseEntity<?> buscarPorTipoVehiculo(@PathVariable TipoVehiculo tipo){
        return ResponseEntity.ok(repartidorService.listRepPorTipoVehiculo(tipo));
    }

    // Listar repartidores por Estado
    @GetMapping("/listar-por-estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable EstadoRepartidor estado){
        return ResponseEntity.ok(repartidorService.findAllByEstado(estado));
    }
}
