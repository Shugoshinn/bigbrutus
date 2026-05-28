package com.bigbrutus.vendedor.repository;

import com.bigbrutus.vendedor.model.EstadoVendedor;
import com.bigbrutus.vendedor.model.Vendedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    // BUSCAR POR EMAIL
    Vendedor findByEmail(String email);

    // BUSCAR POR ESTADO
    List<Vendedor> findByEstado(EstadoVendedor estado);

    // BUSCAR POR SUCURSAL
    List<Vendedor> findByIdSucursal(Long idSucursal);
}