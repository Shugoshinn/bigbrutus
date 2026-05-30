package com.bigbrutus.pedido.controller;


import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.mapper.PedidoMapper;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        List<?> pedidos = pedidoMapper.toDTOList(pedidoService.findAll());
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoGuardado = pedidoService.save(pedido);

        return new ResponseEntity<PedidoDTO>(pedidoMapper.toDTO(pedidoGuardado), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pedidoService.delete(id);
    }
}

