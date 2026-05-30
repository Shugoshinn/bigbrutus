package com.bigbrutus.producto.repository;

import com.bigbrutus.producto.model.CategoriaProducto;
import com.bigbrutus.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Metodo Buscar un producto por su nombre
    Optional<Producto> findByNombre(String nombre);

    // Metodo Buscar un producto según su categoria
    List<Producto> findAllByCategoria(CategoriaProducto categoriaProducto);
}
