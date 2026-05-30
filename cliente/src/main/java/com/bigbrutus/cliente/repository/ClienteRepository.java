package com.bigbrutus.cliente.repository;

import com.bigbrutus.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // BUSCAR POR EMAIL
    Cliente findByEmail(String email);

    // BUSCAR POR NOMBRE
    List<Cliente> findByNombre(String nombre);

    // BUSCAR CLIENTES ACTIVOS
    List<Cliente> findByActivo(boolean activo);
}