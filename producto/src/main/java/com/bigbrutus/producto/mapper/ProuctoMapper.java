package com.bigbrutus.producto.mapper;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProuctoMapper {

    public ProductoDTO toDTO(Producto producto){
        if (producto == null) return null;
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId_producto(producto.getId_producto());
        productoDTO.setNombre(productoDTO.getNombre());
        productoDTO.setDescripcion(productoDTO.getDescripcion());
        productoDTO.setCategoria(productoDTO.getCategoria());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(productoDTO.getStock());
        productoDTO.setDisponible(productoDTO.isDisponible());

        return productoDTO;
    }
}
