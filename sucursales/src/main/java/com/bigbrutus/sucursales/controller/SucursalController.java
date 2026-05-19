package com.bigbrutus.sucursales.controller;

import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {
    
    private SucursalService sucursalService;

    // Lista de todo
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(sucursalService.findAll());
    }

    // Buscar sucursal específica por Id
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscarPorID(@PathVariable Long id){
        try {
            Sucursal sucursalDTO = sucursalService.findByID(id);
            return ResponseEntity.ok(sucursalDTO);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar un sucursal
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Sucursal sucursal){
        Sucursal sucursalNuevo = sucursalService.save(sucursal);
        return new ResponseEntity<>(sucursalNuevo, HttpStatus.CREATED);
    }

    // Eliminar por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        try {
            sucursalService.deleteById(id);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
