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

    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodoDTO(){
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id_producto}")
    public ResponseEntity<?> buscarPorID(@PathVariable Long id_producto){
        try {
            ProductoDTO productoDTO = productoService.findByID(id_producto);
            return ResponseEntity.ok(productoDTO);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Producto producto){
        ProductoDTO productoNuevo = productoService.save(producto);
        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id_producto}")
    public ResponseEntity<?> actualizar(@PathVariable Long id_producto, @Valid @RequestBody Producto producto){
        ProductoDTO productoActualizado = productoService.update(id_producto, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id_producto}")
    public ResponseEntity<?> borrar(@PathVariable Long id_producto){
        productoService.deleteById(id_producto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listado/{id_producto}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id_producto){
        ProductoDTO producto = productoService.findByID(id_producto);
        if (producto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(productoService.findAllDTO());
    }
}
