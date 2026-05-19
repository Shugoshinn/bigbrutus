package com.bigbrutus.sucursales.repository;

import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,Long> {

    List<Sucursal> findAllByActiva(Boolean activa);

    List<Sucursal> findAllByComuna(String comuna);

    List<Sucursal> findAllByTipo(TipoSucursal tipo);
}
