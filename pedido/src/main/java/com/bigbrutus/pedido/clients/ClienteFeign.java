package com.bigbrutus.pedido.clients;

import com.bigbrutus.pedido.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cliente",path = "/api/v1/clientes")
public interface ClienteFeign {

    @GetMapping("/{id}")
    ClienteDTO buscarPorID(@PathVariable Long id);

    @GetMapping("/buscar-por-nombre-completo/{nombreCompleto}")
    List<ClienteDTO> buscarPorNombre(@PathVariable String nombreCompleto);
}
