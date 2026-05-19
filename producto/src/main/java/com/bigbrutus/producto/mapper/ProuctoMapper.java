package com.bigbrutus.producto.mapper;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<ProductoDTO> toDTOList(List<Producto> listado){
        return listado.stream().map(this::toDTO).toList();
    }
}
