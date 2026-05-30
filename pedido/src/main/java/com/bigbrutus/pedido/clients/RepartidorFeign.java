package com.bigbrutus.pedido.clients;

import com.bigbrutus.pedido.dto.RepartidorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "repartidores",path = "api/v1/repartidores")
public interface RepartidorFeign {

    @GetMapping("/{id}")
    RepartidorDTO buscarPorID(@PathVariable Long id);

    @GetMapping("/buscar-por-nombre-completo/{nombreCompleto}")
    List<RepartidorDTO> buscarPorNombre(@PathVariable String nombreCompleto);
}
