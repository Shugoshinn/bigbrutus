package com.bigbrutus.vehiculos.repository;

import com.bigbrutus.vehiculos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {

    boolean existsByPatente(String patente);

    // Metodo Buscar un vehículo por su patente
    Optional<Vehiculo> findByPatente(String patente);

    // Listar todos los vehículos según su estado
    List<Vehiculo> findAllByEstado(String estado);
}
