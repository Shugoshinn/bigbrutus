package com.bigbrutus.pedido.service;

import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.mapper.PedidoMapper;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Pedido findById(Long id_pedido){
        return pedidoRepository.findById(id_pedido).orElse(null);
    }

    public Pedido save(Pedido p){
        return pedidoRepository.save(p);
    }

    public void delete(Long id_pedido){
        pedidoRepository.deleteById(id_pedido);
    }

    public PedidoDTO findByIdDTO(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return pedidoMapper.toDTO(pedido);
    }
}
