package com.bigbrutus.repartidores.repository;

import com.bigbrutus.repartidores.model.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor,Long> {
}
