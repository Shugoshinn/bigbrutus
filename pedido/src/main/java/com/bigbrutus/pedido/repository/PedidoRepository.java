package com.bigbrutus.pedido.repository;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.model.TipoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByRepartidorIn(List<Long> RepartidorIds);

    List<Pedido> findByClienteIn(List<Long> ClienteIds);

    List<Pedido> findByVendedorIn(List<Long> VendedorIds);

    List<Pedido> findAllByEstado(EstadoPedido estado);
}
