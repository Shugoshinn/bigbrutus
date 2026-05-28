package com.bigbrutus.vendedor.controller;

import com.bigbrutus.vendedor.exception.dto.VendedorDTO;
import com.bigbrutus.vendedor.model.EstadoVendedor;
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

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<VendedorDTO>> listar() {

        return ResponseEntity.ok(
                vendedorService.findDTOList()
        );
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> buscarPorId(@PathVariable Long id) {

        VendedorDTO vendedor = vendedorService.findDTO(id);

        if(vendedor == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vendedor);
    }

    // REGISTRAR
    @PostMapping
    public ResponseEntity<Vendedor> registrar(
            @Valid @RequestBody Vendedor vendedor
    ) {

        Vendedor nuevoVendedor = vendedorService.save(vendedor);

        return new ResponseEntity<>(
                nuevoVendedor,
                HttpStatus.CREATED
        );
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Vendedor vendedor
    ) {

        Vendedor vendedorActualizado =
                vendedorService.update(id, vendedor);

        if(vendedorActualizado == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vendedorActualizado);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        vendedorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // ENDPOINTS PERSONALIZADOS
    // =========================

    // BUSCAR POR EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<VendedorDTO> buscarPorEmail(
            @PathVariable String email
    ){

        VendedorDTO vendedor =
                vendedorService.findByEmail(email);

        if(vendedor == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vendedor);
    }

    // BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<VendedorDTO>> buscarPorEstado(
            @PathVariable EstadoVendedor estado
    ){

        return ResponseEntity.ok(
                vendedorService.findByEstado(estado)
        );
    }

    // BUSCAR POR SUCURSAL
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<VendedorDTO>> buscarPorSucursal(
            @PathVariable Long idSucursal
    ){

        return ResponseEntity.ok(
                vendedorService.findBySucursal(idSucursal)
        );
    }

    // ACTUALIZAR SOLO ESTADO
    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<Vendedor> actualizarEstado(
            @PathVariable Long id,
            @PathVariable EstadoVendedor estado
    ){

        Vendedor vendedorActualizado =
                vendedorService.updateEstado(id, estado);

        if(vendedorActualizado == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vendedorActualizado);
    }
}