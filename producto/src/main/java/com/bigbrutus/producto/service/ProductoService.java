package com.bigbrutus.producto.service;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.mapper.ProuctoMapper;
import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProuctoMapper prouctoMapper;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Long id_producto){
        return productoRepository.findById(id_producto).orElse(null);
    }

    public Producto save(Producto p){
        return productoRepository.save(p);
    }

    public void delete(Long id_producto){
        productoRepository.deleteById(id_producto);
    }

    public Producto update(Long id_producto, Producto producto){
        Producto productoActualizar = productoRepository.findById(id_producto).orElse(null);
        if (productoActualizar == null) return null;
        productoActualizar.setNombre(producto.getNombre());
        productoActualizar.setDescripcion(productoActualizar.getDescripcion());
        productoActualizar.setCategoria(producto.getCategoria());
        productoActualizar.setPrecio(producto.getPrecio());
        productoActualizar.setStock(producto.getStock());

        return productoRepository.save(productoActualizar);
    }

    public ProductoDTO findDTO(Long id){
        return prouctoMapper.toDTO(findById(id));
    }

    public List<ProductoDTO> findDTOList(){
        return prouctoMapper.toDTOList(findAll());
    }
}
