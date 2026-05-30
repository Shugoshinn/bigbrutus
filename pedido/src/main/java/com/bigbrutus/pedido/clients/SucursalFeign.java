package com.bigbrutus.pedido.clients;

import com.bigbrutus.pedido.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "sucursales",path = "api/v1/sucursales")
public interface SucursalFeign {

    @GetMapping("/{id}")
    SucursalDTO buscarPorID(@PathVariable Long id);

    @GetMapping("/buscar-por-nombre/{nombre}")
    List<SucursalDTO> buscarPorNombre(@PathVariable String nombre);
}
