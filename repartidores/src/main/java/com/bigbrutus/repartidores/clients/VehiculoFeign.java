package com.bigbrutus.repartidores.clients;

import com.bigbrutus.repartidores.dto.VehiculoDTO;
import com.bigbrutus.repartidores.model.TipoVehiculo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vehiculos",path = "api/v1/vehiculos")
public interface VehiculoFeign {

    @GetMapping("/{id}")
    VehiculoDTO buscarPorID(@PathVariable Long id);

    @GetMapping
    List<VehiculoDTO> listarVehiculos();

    @GetMapping("/buscar-por-tipo/{tipo}")
    List<VehiculoDTO> findAllByTipo(@PathVariable TipoVehiculo tipo);

}
