package com.bigbrutus.repartidores.controller;

import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.service.RepartidorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/repartidores")
public class RepartidorController {

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
}
