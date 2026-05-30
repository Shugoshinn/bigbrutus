package com.bigbrutus.pedido.mapper;

import com.bigbrutus.pedido.clients.ClienteFeign;
import com.bigbrutus.pedido.clients.RepartidorFeign;
import com.bigbrutus.pedido.clients.SucursalFeign;
import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PedidoMapper {

    @Autowired
    private ClienteFeign clienteFeign;

    @Autowired
    private SucursalFeign sucursalFeign;

    @Autowired
    private RepartidorFeign repartidorFeign;

    public PedidoDTO toDTO(Pedido p){

        if (p == null) return null;

        PedidoDTO dto =new PedidoDTO();

        dto.setId_pedido(p.getId_pedido());
        dto.setFecha_pedido(p.getFecha_pedido());
        dto.setDireccion_entrega(p.getDireccion_entrega());
        dto.setEstado(p.getEstado());
        dto.setTipo_pedido(p.getTipo_pedido());
        dto.setTotal(p.getTotal());
        dto.setMetodo_pago(p.getMetodo_pago());

        // Validar que no esté vacío
        if (p.getCliente() != null) {
            //Buscar al cliente usando el ID que viene del pedido
            var cliente = clienteFeign.buscarPorID(p.getCliente());

            if (cliente != null){
                dto.setCliente(Long.valueOf(cliente.getNombreCompleto()));
            }
        }

        // Validar que no esté vacío
        if (p.getSucursal() != null) {
            //Buscar la sucursal usando el ID que viene del pedido
            var sucursal = sucursalFeign.buscarPorID(p.getSucursal());

            if (sucursal != null){
                dto.setSucursal(p.getSucursal());
            }
        }

        // Validar que no esté vacío
        if (p.getRepartidor() != null) {
            //Buscar al cliente usando el ID que viene del pedido
            var repartidor = repartidorFeign.buscarPorID(p.getRepartidor());

            if (repartidor != null){
                dto.setRepartidor(p.getRepartidor());
            }
        }

        return dto;
    }
}
