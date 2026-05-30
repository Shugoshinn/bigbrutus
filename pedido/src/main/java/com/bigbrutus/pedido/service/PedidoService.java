package com.bigbrutus.pedido.service;

import com.bigbrutus.pedido.clients.ClienteFeign;
import com.bigbrutus.pedido.clients.RepartidorFeign;
import com.bigbrutus.pedido.clients.SucursalFeign;
import com.bigbrutus.pedido.clients.VendedorFeign;
import com.bigbrutus.pedido.dto.*;
import com.bigbrutus.pedido.exception.PedidoNotFoundException;
import com.bigbrutus.pedido.mapper.PedidoMapper;
import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.Pedido;
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

    @Autowired
    private ClienteFeign clienteFeign;

    @Autowired
    private RepartidorFeign repartidorFeign;

    @Autowired
    private SucursalFeign sucursalFeign;

    @Autowired
    private VendedorFeign vendedorFeign;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Pedido findByID(Long id){
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con id" + id));
    }

    public Pedido save(Pedido pedidoNuevo){
            return pedidoRepository.save(pedidoNuevo);
    }

    public void deleteByID(Long id){
        if (!pedidoRepository.existsById(id)){
            throw new PedidoNotFoundException("Pedido no econtrado con id" + id);
        }
    }

    public Pedido update(Long id, Pedido pedidoActualizado){
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setDireccion_entrega(pedidoActualizado.getDireccion_entrega());
                    pedido.setMetodo_pago(pedidoActualizado.getMetodo_pago());

                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con id: " + id));
    }

    public List<PedidoDTO> listaDetallada(){
        List<Pedido> listado = pedidoRepository.findAll();
        List<PedidoDTO> listadoDTO = new ArrayList<>();

        for (Pedido pedido : listado){
            PedidoDTO dto = pedidoMapper.toDTO(pedido);
            ClienteDTO cliente = clienteFeign.buscarPorID(pedido.getCliente());
            SucursalDTO sucursal  = sucursalFeign.buscarPorID(pedido.getSucursal());
            RepartidorDTO repartidor = repartidorFeign.buscarPorID(pedido.getRepartidor());
            VendedorDTO vendedor = vendedorFeign.buscarPorID(pedido.getVendedor());

            dto.setNombre_repartidor(String.valueOf(pedido.getRepartidor()));
            dto.setNombre_cliente(String.valueOf(pedido.getCliente()));
            dto.setNombre_sucursal(String.valueOf(pedido.getSucursal()));
            dto.setNombre_vendedor(String.valueOf(pedido.getVendedor()));

            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<PedidoDTO> listPedPorRepartidor(String  nombreCompleto){

        List<PedidoDTO> listadoFinal = new ArrayList<>();
        List<RepartidorDTO> listadoRepartidorDTO =repartidorFeign.buscarPorNombre(nombreCompleto);

        if (listadoRepartidorDTO.isEmpty()){
            return listadoFinal;
        }

        List<Long> idsRepartidores = listadoRepartidorDTO.stream().map(RepartidorDTO::getId).toList();

        List<Pedido> pedidosEncontrados = pedidoRepository.findByRepartidorIn(idsRepartidores);

        for (Pedido p : pedidosEncontrados){
            PedidoDTO dto= pedidoMapper.toDTO(p);

            for (RepartidorDTO r : listadoRepartidorDTO){
                if (p.getRepartidor().equals(r.getId())){
                    dto.setNombre_repartidor(r.getNombreCompleto());
                    break;
                }
            }
            listadoFinal.add(dto);
        }

        return listadoFinal;
    }

    public List<PedidoDTO> listPedPorCliente(String nombreCompleto){

        List<PedidoDTO> listadoFinal = new ArrayList<>();
        List<ClienteDTO> listadoClienteDTO = clienteFeign.buscarPorNombre(nombreCompleto);

        if (listadoClienteDTO.isEmpty()){
            return listadoFinal;
        }

        List<Long> idsClientes = listadoClienteDTO.stream().map(ClienteDTO::getIdCliente).toList();

        List<Pedido> pedidosEncontrados = pedidoRepository.findByClienteIn(idsClientes);

        for (Pedido p : pedidosEncontrados){
            PedidoDTO dto = pedidoMapper.toDTO(p);

            for (ClienteDTO c : listadoClienteDTO){
                if (p.getCliente().equals(c.getIdCliente())){
                    dto.setNombre_cliente(c.getNombreCompleto());
                    break;
                }
            }
            listadoFinal.add(dto);
        }

        return listadoFinal;
    }

    public List<PedidoDTO> listPedPorVendedor(String nombreCompleto){

        List<PedidoDTO> listadoFinal = new ArrayList<>();
        List<VendedorDTO> listadoVendedorDTO = vendedorFeign.buscarPorNombre(nombreCompleto);

        if (listadoVendedorDTO.isEmpty()){
            return listadoFinal;
        }

        List<Long> idsVendedor = listadoVendedorDTO.stream().map(VendedorDTO::getIdVendedor).toList();

        List<Pedido> pedidosEncontrados = pedidoRepository.findByVendedorIn(idsVendedor);

        for (Pedido p : pedidosEncontrados){
            PedidoDTO dto = pedidoMapper.toDTO(p);

            for (VendedorDTO v : listadoVendedorDTO){
                if (p.getVendedor().equals(v.getIdVendedor())){
                    dto.setNombre_cliente(v.getNombreCompleto());
                    break;
                }
            }
            listadoFinal.add(dto);
        }

        return listadoFinal;
    }

    public List<PedidoDTO> findAllByEstado(EstadoPedido estado){
        List<Pedido> listaPedidos = pedidoRepository.findAllByEstado(estado);
        List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
        for (Pedido p : listaPedidos){
            listaPedidosDTO.add(pedidoMapper.toDTO(p));
        }
        return listaPedidosDTO;
    }
}
