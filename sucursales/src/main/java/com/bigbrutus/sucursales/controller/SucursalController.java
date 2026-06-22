package com.bigbrutus.sucursales.controller;

import com.bigbrutus.sucursales.dto.SucursalDTO;
import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Controlador de sucursales", description = "Endpoints para la gestión de sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // Lista de todo
    @Operation(
            summary = "Listar todas las sucursuales",
            description = "Obtiene una lista detallada de todas las sucursuales registradas en la base de datos sin ningún filtro."
    )
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(sucursalService.findAll());
    }

    // Buscar sucursal específica por Id
    @Operation(
            summary = "Buscar sucursual por ID",
            description = "Busca y retorna una sucursual específica en su formato DTO utilizando su número de ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> buscarPorID(@PathVariable Long id){
        SucursalDTO sucursalDTO = sucursalService.findByID(id);
        return ResponseEntity.ok(sucursalDTO);

    }

    // Registrar un sucursal
    @Operation(
            summary = "Registrar una nueva sucursual",
            description = "Registra una nueva sucursual en el sistema. Valida que los datos ingresados sean correctos."
    )
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Sucursal sucursal){
        Sucursal sucursalNuevo = sucursalService.save(sucursal);
        return new ResponseEntity<>(sucursalNuevo, HttpStatus.CREATED);
    }

    // Eliminar por Id
    @Operation(
            summary = "Eliminar una sucursual por ID",
            description = "Remueve una sucursual de la base de datos basándose en el identificador único proporcionado. Retorna 204 (No Content) si es exitoso."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        sucursalService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    // ***OTROS**

    // Listar Sucursales Activos o Inactivos
    @Operation(
            summary = "Listar las sucursuales según si están Activas o Inactivas",
            description = "Obtiene una lista detallada de todas las sucursuales registradas en la base de datos según si están Activas [true] o Inactivas [false]."
    )
    @GetMapping("/activo/{activa}")
    public List<Sucursal> findAllByActiva(@PathVariable Boolean activa){
        return sucursalService.findAllByActiva(activa);
    }

    // Listar Sucursales por Comuna
    @Operation(
            summary = "Listar las sucursuales según la Comuna",
            description = "Obtiene una lista detallada de todas las sucursuales registradas en la base de datos según su Comuna."
    )
    @GetMapping("/comuna/{comuna}")
    public List<Sucursal> findAllByComuna(@PathVariable String comuna){
        return sucursalService.findAllByComuna(comuna);
    }

    @Operation(
            summary = "Listar las sucursuales según su Tipo de Sucursal",
            description = "Obtiene una lista detallada de todas las sucursuales registradas en la base de datos según el tipo de sucursal [PARA_SERVIR,PARA_LLEVAR]."
    )
    @GetMapping("/tipo/{tipo}")
    // Listar Sucursales según tipo [PARA_SERVIR,PARA_LLEVAR]
    public List<Sucursal> findAllByTipo(@PathVariable TipoSucursal tipo){
        return sucursalService.findAllByTipo(tipo);
    }
}
