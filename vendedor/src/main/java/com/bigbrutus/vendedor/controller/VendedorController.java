package com.bigbrutus.vendedor.controller;

import com.bigbrutus.vendedor.dto.VendedorDTO;
import com.bigbrutus.vendedor.model.Vendedor;
import com.bigbrutus.vendedor.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> listar() {
        return ResponseEntity.ok(vendedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendedorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Vendedor> registrar(@Valid @RequestBody Vendedor vendedor) {
        return new ResponseEntity<>(vendedorService.save(vendedor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizar(@PathVariable Long id, @Valid @RequestBody Vendedor vendedor) {
        return ResponseEntity.ok(vendedorService.update(id, vendedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        vendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}