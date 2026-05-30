package com.bigbrutus.pedido.clients;

import com.bigbrutus.pedido.dto.VendedorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vendedores",path = "/api/v1/vendedores")
public interface VendedorFeign {

    @GetMapping("/{id}")
    VendedorDTO buscarPorID(@PathVariable Long id);

    @GetMapping("/buscar-por-nombre-completo/{nombreCompleto}")
    List<VendedorDTO> buscarPorNombre(@PathVariable String nombreCompleto);
}
