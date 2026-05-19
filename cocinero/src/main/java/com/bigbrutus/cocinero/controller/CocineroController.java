package com.bigbrutus.cocinero.controller;

import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.model.Cocinero;
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

    @GetMapping
    public ResponseEntity<List<CocineroDTO>> listar() {
        return ResponseEntity.ok(cocineroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocineroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cocineroService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Cocinero> registrar(@Valid @RequestBody Cocinero cocinero) {
        return new ResponseEntity<>(cocineroService.save(cocinero), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cocinero> actualizar(@PathVariable Long id, @Valid @RequestBody Cocinero cocinero) {
        return ResponseEntity.ok(cocineroService.update(id, cocinero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        cocineroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}