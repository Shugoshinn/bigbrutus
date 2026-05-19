package com.bigbrutus.repartidores.clients;

import com.bigbrutus.repartidores.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vehiculos",url = "localhost:8080/api/v1/vehiculos")
public interface VehiculoFeign {

    @GetMapping("/{id}")
    VehiculoDTO buscarPorID(@PathVariable Long id);

    @GetMapping
    List<VehiculoDTO> listarVehiculos();

}
