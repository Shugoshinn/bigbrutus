package com.bigbrutus.cocinero.repository;

import com.bigbrutus.cocinero.model.Cocinero;

import com.bigbrutus.cocinero.model.EstadoCocinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocineroRepository extends JpaRepository<Cocinero, Long> {

    // BUSCAR POR ESPECIALIDAD
    List<Cocinero> findByEspecialidad(String especialidad);

    // BUSCAR POR ESTADO
    List<Cocinero> findByEstado(EstadoCocinero estadoCocinero);

    // BUSCAR POR SUCURSAL
    List<Cocinero> findByIdSucursal(Long idSucursal);


}