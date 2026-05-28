package com.bigbrutus.cocinero.controller;

import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.model.Cocinero;
import com.bigbrutus.cocinero.model.EstadoCocinero;
import com.bigbrutus.cocinero.service.CocineroService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cocineros")
public class CocineroController {

    @Autowired
    private CocineroService cocineroService;

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<CocineroDTO>> listar() {

        return ResponseEntity.ok(cocineroService.findDTOList());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<CocineroDTO> buscarPorId(@PathVariable Long id) {

        CocineroDTO cocinero = cocineroService.findDTO(id);

        if(cocinero == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cocinero);
    }

    // REGISTRAR
    @PostMapping
    public ResponseEntity<Cocinero> registrar(@Valid @RequestBody Cocinero cocinero) {

        Cocinero nuevoCocinero = cocineroService.save(cocinero);

        return new ResponseEntity<>(nuevoCocinero, HttpStatus.CREATED);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Cocinero> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody Cocinero cocinero) {

        Cocinero cocineroActualizado = cocineroService.update(id, cocinero);

        if(cocineroActualizado == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cocineroActualizado);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        cocineroService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // ENDPOINTS PERSONALIZADOS
    // =========================

    // BUSCAR POR ESPECIALIDAD
    @GetMapping("/especialidad/{specialty}")
    public ResponseEntity<List<CocineroDTO>> buscarPorEspecialidad(@PathVariable String specialty){

        return ResponseEntity.ok(cocineroService.findBySpecialty(specialty));
    }

    // BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CocineroDTO>> buscarPorEstado(@PathVariable String estado){

        return ResponseEntity.ok(cocineroService.findByEstado(estado));
    }

    // BUSCAR POR SUCURSAL
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<CocineroDTO>> buscarPorSucursal(@PathVariable Long idSucursal){

        return ResponseEntity.ok(cocineroService.findBySucursal(idSucursal));
    }

    // ACTUALIZAR SOLO ESTADO
    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<Cocinero> actualizarEstado(@PathVariable Long id,
                                                     @PathVariable String estado){

        Cocinero cocineroActualizado = cocineroService.updateEstado(id, EstadoCocinero.valueOf(estado));

        if(cocineroActualizado == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cocineroActualizado);
    }
}