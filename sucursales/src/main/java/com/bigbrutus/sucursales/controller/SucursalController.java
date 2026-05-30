package com.bigbrutus.sucursales.controller;

import com.bigbrutus.sucursales.dto.SucursalDTO;
import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // Lista de todo
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(sucursalService.findAll());
    }

    // Buscar sucursal específica por Id
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> buscarPorID(@PathVariable Long id){
        SucursalDTO sucursalDTO = sucursalService.findByID(id);
        return ResponseEntity.ok(sucursalDTO);

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
        sucursalService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    // ***OTROS**

    // Listar Sucursales Activos o Inactivos

    @GetMapping("/activo/{activa}")
    public List<Sucursal> findAllByActiva(@PathVariable Boolean activa){
        return sucursalService.findAllByActiva(activa);
    }

    // Listar Sucursales por Comuna
    @GetMapping("/comuna/{comuna}")
    public List<Sucursal> findAllByComuna(@PathVariable String comuna){
        return sucursalService.findAllByComuna(comuna);
    }

    @GetMapping("/tipo/{tipo}")
    // Listar Sucursales según tipo [PARA_SERVIR,PARA_LLEVAR]
    public List<Sucursal> findAllByTipo(@PathVariable TipoSucursal tipo){
        return sucursalService.findAllByTipo(tipo);
    }
}
