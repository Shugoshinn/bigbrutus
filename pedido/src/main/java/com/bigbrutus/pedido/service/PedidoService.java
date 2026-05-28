package com.bigbrutus.pedido.service;

import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.mapper.PedidoMapper;
import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.model.TipoPedido;
import com.bigbrutus.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    // *** MÉTODOS CRUD BÁSICO ***

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    // Lista de todos los pedidos FORMATO DTO
    public List<PedidoDTO> findAllDTO(){
        List<Pedido> listaPedidos = pedidoRepository.findAll();
        List<PedidoDTO> listaPedidoDTO = new ArrayList<>();
        for (Pedido p : listaPedidos) {
            listaPedidoDTO.add(pedidoMapper.toDTO(p));
        }
        return listaPedidoDTO;
    }

    // Buscar  por Id pedido específico FORMATO DTO
    public PedidoDTO findByID(Long id){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException("Pedido no encontrado con id: "+ id));

        return pedidoMapper.toDTO(pedido);
    }

    // Registrar un pedido
    public Pedido save(Pedido pedidoNuevo){
        return pedidoRepository.save(pedidoNuevo);
    }

    // Eliminar por Id un pedido
    public void deleteById(Long id){
        if (!pedidoRepository.existsById(id)){
            throw new RuntimeException("Pedido no encontrado con id: "+ id);
        }
        pedidoRepository.deleteById(id);
    }

    // Actualizar un pedido, buscado por Id.
    public Pedido update(Long id,Pedido pedidoActualizado){
        return pedidoRepository.findById(id)
                .map(pedido ->{
                    pedido.setFecha_pedido(pedidoActualizado.getFecha_pedido());
                    pedido.setEstado(pedidoActualizado.getEstado());
                    pedido.setTipo_pedido(pedidoActualizado.getTipo_pedido());
                    pedido.setDireccion_entrega(pedidoActualizado.getDireccion_entrega());
                    pedido.setTotal(pedidoActualizado.getTotal());
                    pedido.setMetodo_pago(pedidoActualizado.getMetodo_pago());
                    pedido.setId_cliente(pedidoActualizado.getId_cliente());
                    pedido.setId_vendedor(pedidoActualizado.getId_vendedor());
                    pedido.setId_repartidor(pedidoActualizado.getId_repartidor());
                    pedido.setId_sucursal(pedidoActualizado.getId_sucursal());

                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: "+ id));
    }

    // *** MÉTODOS PERSONALIZADOS ***

    // Buscar por patente
    public PedidoDTO findByDireccionEntrega(String direccion){
        Pedido pedido = pedidoRepository.findByDireccionEntrega(direccion).orElseThrow(()-> new RuntimeException("Direccion no encontrada con pedido: "+ direccion));
        return pedidoMapper.toDTO(pedido);
    }

    // Actualizar Solo Estado
    public Pedido updateEstado(Long id, EstadoPedido estado){
        return pedidoRepository.findById(id)
                .map(pedido ->{
                    pedido.setEstado(estado);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: "+ id));
    }
    // Listar por Estado FORMATO DTO
    public List<PedidoDTO> findAllByEstado(EstadoPedido estado){
        List<Pedido> listaPedidos = pedidoRepository.findAllByEstado(estado);
        List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
        for (Pedido p : listaPedidos) {
            listaPedidosDTO.add(pedidoMapper.toDTO(p));
        }
        return listaPedidosDTO;
    }


    // Listar por tipo FORMATO DTO
    public List<PedidoDTO> findAllByTipo(TipoPedido tipo){
        List<Pedido> listaPedidos = pedidoRepository.findAllByTipo(tipo);
        List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
        for (Pedido p : listaPedidos) {
            listaPedidosDTO.add(pedidoMapper.toDTO(p));
        }
        return listaPedidosDTO;
    }
}
