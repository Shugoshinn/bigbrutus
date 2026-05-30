package com.bigbrutus.pedido.config;

import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.MetodoPago;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.model.TipoPedido;
import com.bigbrutus.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader {
    @Autowired
    public PedidoRepository pedidoRepository;

    public void run(String... args) throws Exception{

        if (pedidoRepository.count() == 0){

            List<Pedido> pedidosPrueba = List.of(
                    new Pedido(null, LocalDate.parse("2026-05-28"), EstadoPedido.EN_ESPERA, TipoPedido.PREPARANDO, "Avenida Recoleta 1234, Depto 4B", BigDecimal.valueOf(15990.00), MetodoPago.DEBITO, 1L, 1L, 1L, 1L),
                    new Pedido(null,LocalDate.parse("2026-05-29"), EstadoPedido.EN_CAMINO, TipoPedido.PREPARANDO, "Avenida Pedro Donoso 456", BigDecimal.valueOf(15990), MetodoPago.EFECTIVO, 1L, 2L, 2L, 2L),
                    new Pedido(null,LocalDate.parse("2026-05-25"), EstadoPedido.EN_CAMINO, TipoPedido.ENTREGADO, "Américo Vespucio 1011, Huechuraba", BigDecimal.valueOf(15990), MetodoPago.CREDITO, 1L, 2L, 3L, 3L),
                    new Pedido(null,LocalDate.parse("2026-05-30"), EstadoPedido.EN_ESPERA, TipoPedido.ENTREGADO, "Avenida España 1680", BigDecimal.valueOf(15990), MetodoPago.DEBITO, 1L, 2L, 4L, 4L),
                    new Pedido(null,LocalDate.parse("2026-05-27"), EstadoPedido.EN_CAMINO, TipoPedido.PREPARANDO, "Calle Einstein 789, Casa 2", BigDecimal.valueOf(15990), MetodoPago.EFECTIVO, 1L, 1L, 5L, 6L)
            );

            pedidoRepository.saveAll(pedidosPrueba);
            System.out.println(">>>>> Base de Datos Pedido Inicializada: 5 registros cargados. <<<<<");
        }

    }
}
