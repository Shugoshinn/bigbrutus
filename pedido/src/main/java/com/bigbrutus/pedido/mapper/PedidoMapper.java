package com.bigbrutus.pedido.mapper;

import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {
    public PedidoDTO toDTO(Pedido pedido){
        if (pedido == null) return null;
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setFecha_pedido(pedido.getFecha_pedido());
        pedidoDTO.setEstado(pedido.getEstado());
        pedidoDTO.setTipo_entrega(pedido.getTipo_entrega());
        pedidoDTO.setDireccion_entrega(pedido.getDireccion_entrega());
        pedidoDTO.setTotal(pedido.getTotal());
        pedidoDTO.setMetodo_pago(pedido.getMetodo_pago());

        return pedidoDTO;
    }

    public List<PedidoDTO> toDTOList(List<Pedido> listado){
        return listado.stream().map(this::toDTO).toList();
    }
}
