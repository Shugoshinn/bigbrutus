package com.bigbrutus.pedido.controller;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> listarTodo(){
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorID(@PathVariable Long id){
        try {
            Pedido pedidoDTO = pedidoService.findByID(id);
            return ResponseEntity.ok(pedidoDTO);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> registra(@Valid @RequestBody Pedido pedido){
        Pedido pedidoNuevo = pedidoService.save(pedido);
        return new ResponseEntity<>(pedidoNuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorID(@PathVariable Long id){
        try {
            pedidoService.deleteByID(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedido){
        try {
            return ResponseEntity.ok(pedidoService.update(id, pedido));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista-detallada")
    public ResponseEntity<?> listaDetallada(){
        return ResponseEntity.ok(pedidoService.listaDetallada());
    }

    @GetMapping("/nombre-repartidor/{nombreRep}")
    public ResponseEntity<?> buscarPorNomRepartidor(@PathVariable String nombreRep){
        return ResponseEntity.ok(pedidoService.listPedPorRepartidor(nombreRep));
    }

    @GetMapping("/nombre-cliente/{nombreCli}")
    public ResponseEntity<?> buscarPorNomCliente(@PathVariable String nombreCli){
        return ResponseEntity.ok(pedidoService.listPedPorCliente(nombreCli));
    }

    @GetMapping("/nombre-vendedor/{nombreVend}")
    public ResponseEntity<?> buscarPorNomVendedor(@PathVariable String nombreVend){
        return ResponseEntity.ok(pedidoService.listPedPorVendedor(nombreVend));
    }

    @GetMapping("/listar-por-estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable EstadoPedido estado){
        return ResponseEntity.ok(pedidoService.findAllByEstado(estado));
    }


}
