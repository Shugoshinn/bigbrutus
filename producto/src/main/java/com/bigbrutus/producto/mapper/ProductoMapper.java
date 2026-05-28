package com.bigbrutus.producto.mapper;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto){
        if (producto == null) return null;

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setId_producto(producto.getId_producto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCategoria(producto.getCategoria().name());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setDisponible(producto.isDisponible());

        return productoDTO;
    }
}
