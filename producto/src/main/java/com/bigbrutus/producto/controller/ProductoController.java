package com.bigbrutus.producto.controller;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id_producto){
        Producto producto = productoService.findById(id_producto);
        if (producto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    @GetMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Producto producto){
        Producto productoNuevo = productoService.save(producto);
        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id_producto, @Valid @RequestBody Producto producto){
        Producto productoActualizado = productoService.update(id_producto, producto);
        if (productoActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{listado/{id}}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id){
        ProductoDTO producto = productoService.findDTO(id);
        if (producto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(productoService.findDTOList());
    }
}
