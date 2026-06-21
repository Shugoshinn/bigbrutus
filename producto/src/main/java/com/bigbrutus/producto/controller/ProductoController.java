package com.bigbrutus.producto.controller;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Controlador de Productos", description = "Endpoints para la gestión del catálogo de productos de la pizzería")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(
            summary = "Listar todos los productos (Entidad)",
            description = "Retorna el catálogo completo de productos en su formato de entidad original, sin aplicar transformaciones DTO."
    )
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodoDTO(){
        return ResponseEntity.ok(productoService.findAll());
    }

    @Operation(
            summary = "Buscar producto por ID",
            description = "Obtiene los detalles de un producto específico (como una pizza o bebida) mediante su identificador único, retornándolo en formato DTO."
    )
    @GetMapping("/{id_producto}")
    public ResponseEntity<?> buscarPorID(@PathVariable Long id_producto){
        try {
            ProductoDTO productoDTO = productoService.findByID(id_producto);
            return ResponseEntity.ok(productoDTO);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Registrar un nuevo producto",
            description = "Añade un nuevo producto al catálogo del sistema. Valida estrictamente los datos ingresados como el precio positivo, stock no negativo y nombre válido."
    )
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Producto producto){
        ProductoDTO productoNuevo = productoService.save(producto);
        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar producto existente",
            description = "Sobrescribe la información de un producto registrado previamente (útil para cambiar precios, descripciones o actualizar el stock disponible)."
    )
    @PutMapping("/{id_producto}")
    public ResponseEntity<?> actualizar(@PathVariable Long id_producto, @Valid @RequestBody Producto producto){
        ProductoDTO productoActualizado = productoService.update(id_producto, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(
            summary = "Eliminar un producto",
            description = "Remueve permanentemente un producto del catálogo utilizando su ID. Retorna un código 204 (No Content) si se elimina correctamente."
    )
    @DeleteMapping("/{id_producto}")
    public ResponseEntity<?> borrar(@PathVariable Long id_producto){
        productoService.deleteById(id_producto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Buscar producto por ID (Ruta DTO)",
            description = "Endpoint alternativo bajo la ruta '/listado' para obtener un producto específico mapeado de forma segura utilizando el patrón DTO."
    )
    @GetMapping("/listado/{id_producto}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id_producto){
        ProductoDTO producto = productoService.findByID(id_producto);
        if (producto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    @Operation(
            summary = "Listar todos los productos (DTO)",
            description = "Consulta la base de datos y retorna el catálogo completo de productos, transformando cada registro a su respectivo DTO para ocultar información sensible."
    )
    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(productoService.findAllDTO());
    }
}
