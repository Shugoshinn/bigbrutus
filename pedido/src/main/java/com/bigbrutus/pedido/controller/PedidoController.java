package com.bigbrutus.pedido.controller;


import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.mapper.PedidoMapper;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<?> getAll(){
        return pedidoMapper.toDTOList(pedidoService.findAll());
    }

    @PostMapping
    public PedidoDTO create(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoGuardado = pedidoService.save(pedido);
        return pedidoMapper.toDTO(pedidoGuardado);
    }
}
