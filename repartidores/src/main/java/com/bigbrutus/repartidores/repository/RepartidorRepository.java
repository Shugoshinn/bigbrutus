package com.bigbrutus.repartidores.repository;

import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor,Long> {

    // Este método buscará todos los repartidores cuyo ID de vehículo esté en la lista que le enviemos
    List<Repartidor> findByVehiculoIn(List<Long> vehiculoIds);


    List<Repartidor> findAllByEstado(EstadoRepartidor estado);
}
