package com.bigbrutus.pedido.repository;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.model.TipoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Metodo Buscar una direccion por su pedido
    Optional<Pedido> findByDireccionEntrega(String direccion);

    // Metodo Buscar un pedido según su estado
    List<Pedido> findAllByEstado(EstadoPedido estado);

    // Metodo Buscar un pedido según su tipo
    List<Pedido> findAllByTipo(TipoPedido tipo);
}
