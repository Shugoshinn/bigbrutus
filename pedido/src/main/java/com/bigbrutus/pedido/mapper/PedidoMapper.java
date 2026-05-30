package com.bigbrutus.pedido.mapper;

import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) return null;
        PedidoDTO dto = new PedidoDTO();
        dto.setId_pedido(pedido.getId_pedido());
        dto.setFecha_pedido(pedido.getFecha_pedido());
        dto.setEstado(pedido.getEstado());
        dto.setTipo_entrega(pedido.getTipo_entrega());
        dto.setDireccion_entrega(pedido.getDireccion_entrega());
        dto.setTotal(pedido.getTotal());
        dto.setMetodo_pago(pedido.getMetodo_pago());
        
        dto.setId_cliente(pedido.getId_cliente());
        dto.setId_vendedor(pedido.getId_vendedor());
        dto.setId_repartidor(pedido.getId_repartidor());
        dto.setId_sucursal(pedido.getId_sucursal());

        return dto;
    }

    public Pedido toEntity(PedidoDTO dto) {
        if (dto == null) return null;
        Pedido pedido = new Pedido();
        pedido.setId_pedido(dto.getId_pedido());
        pedido.setFecha_pedido(dto.getFecha_pedido());
        pedido.setEstado(dto.getEstado());
        pedido.setTipo_entrega(dto.getTipo_entrega());
        pedido.setDireccion_entrega(dto.getDireccion_entrega());
        pedido.setTotal(dto.getTotal());
        pedido.setMetodo_pago(dto.getMetodo_pago());

        pedido.setId_cliente(dto.getId_cliente());
        pedido.setId_vendedor(dto.getId_vendedor());
        pedido.setId_repartidor(dto.getId_repartidor());
        pedido.setId_sucursal(dto.getId_sucursal());

        return pedido;
    }

    public List<PedidoDTO> toDTOList(List<Pedido> listado) {
        return listado.stream().map(this::toDTO).toList();
    }
}
