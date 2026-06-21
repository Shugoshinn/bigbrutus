package com.bigbrutus.pedido.controller;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Controlador de Pedidos", description = "Endpoints para la gestión de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(
            summary = "Listar todos los pedidos",
            description = "Obtiene una lista completa de todos los pedidos registrados en la base de datos sin ningún filtro."
    )
    @GetMapping("/listar-todo")
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Busca y retorna los detalles de un pedido específico utilizando su número de ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorID(@PathVariable Long id){
        try {
            Pedido pedidoDTO = pedidoService.findByID(id);
            return ResponseEntity.ok(pedidoDTO);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Crear un nuevo pedido",
            description = "Registra un nuevo pedido en el sistema. Valida que los datos ingresados sean correctos."
    )
    @PostMapping
    public ResponseEntity<?> registra(@Valid @RequestBody Pedido pedido){
        Pedido pedidoNuevo = pedidoService.save(pedido);
        return new ResponseEntity<>(pedidoNuevo, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar un pedido por ID",
            description = "Remueve físicamente un pedido de la base de datos basándose en el identificador único proporcionado. Retorna 204 (No Content) si es exitoso."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorID(@PathVariable Long id){
        try {
            pedidoService.deleteByID(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Actualizar un pedido existente",
            description = "Permite modificar datos dinámicos de un pedido (como la dirección de entrega y el método de pago) buscando por su identificador."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedido){
        try {
            return ResponseEntity.ok(pedidoService.update(id, pedido));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtener lista detallada (DTO)",
            description = "Retorna el listado de pedidos consolidando asíncronamente los nombres reales del cliente, sucursal, repartidor y vendedor mediante comunicaciones remotas con Feign Clients."
    )
    @GetMapping("/lista-detallada")
    public ResponseEntity<?> listaDetallada(){
        return ResponseEntity.ok(pedidoService.listaDetallada());
    }

    @Operation(
            summary = "Buscar pedidos por nombre de repartidor",
            description = "Consulta al microservicio de repartidores externos para obtener los IDs asociados al nombre y retorna todos los pedidos que tengan asignados a dicho personal."
    )
    @GetMapping("/nombre-repartidor/{nombreRep}")
    public ResponseEntity<?> buscarPorNomRepartidor(@PathVariable String nombreRep){
        return ResponseEntity.ok(pedidoService.listPedPorRepartidor(nombreRep));
    }

    @Operation(
            summary = "Buscar pedidos por nombre de cliente",
            description = "Filtra y extrae los pedidos mapeados en formato DTO vinculando los registros de identificación recuperados desde el microservicio de Clientes de forma remota."
    )
    @GetMapping("/nombre-cliente/{nombreCli}")
    public ResponseEntity<?> buscarPorNomCliente(@PathVariable String nombreCli){
        return ResponseEntity.ok(pedidoService.listPedPorCliente(nombreCli));
    }

    @Operation(
            summary = "Buscar pedidos por nombre de vendedor",
            description = "Se conecta al microservicio de origen para identificar al vendedor por su criterio de nombre completo y lista las órdenes generadas por este."
    )
    @GetMapping("/nombre-vendedor/{nombreVend}")
    public ResponseEntity<?> buscarPorNomVendedor(@PathVariable String nombreVend){
        return ResponseEntity.ok(pedidoService.listPedPorVendedor(nombreVend));
    }

    @Operation(
            summary = "Listar pedidos filtrados por estado",
            description = "Retorna una colección en formato DTO que agrupa únicamente las órdenes que se encuentren bajo el estado especificado (por ejemplo: EN_ESPERA o EN_CAMINO)."
    )
    @GetMapping("/listar-por-estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable EstadoPedido estado){
        return ResponseEntity.ok(pedidoService.findAllByEstado(estado));
    }


}
