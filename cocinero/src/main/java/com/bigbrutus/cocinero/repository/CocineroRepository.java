package com.bigbrutus.cocinero.repository;

import com.bigbrutus.cocinero.model.Cocinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocineroRepository extends JpaRepository<Cocinero, Long> {
}