package com.bigbrutus.producto.service;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.exception.ProductoNotFoundException;
import com.bigbrutus.producto.mapper.ProductoMapper;
import com.bigbrutus.producto.model.CategoriaProducto;
import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    // *** MÉTODOS CRUD BÁSICO ***

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    // Lista de todos los productos FORMATO DTO
    public List<ProductoDTO> findAllDTO(){
        List<Producto> listaProductos = productoRepository.findAll();
        List<ProductoDTO> listaProductoDTO = new ArrayList<>();
        for (Producto p : listaProductos) {
            listaProductoDTO.add(productoMapper.toDTO(p));
        }
        return listaProductoDTO;
    }

    public ProductoDTO findByID(Long id_producto){
        Producto producto = productoRepository.findById(id_producto).orElseThrow(()-> new ProductoNotFoundException("Producto no encontrado con id: "+ id_producto));
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO save(Producto p){
        Producto productoGuardado = productoRepository.save(p);
        return productoMapper.toDTO(productoGuardado);
    }

    public void deleteById(Long id_producto){
        if (!productoRepository.existsById(id_producto)){
            throw new ProductoNotFoundException("Producto no encontrado con id: "+ id_producto);
        }
        productoRepository.deleteById(id_producto);
    }

    public ProductoDTO update(Long id_producto, Producto productoActualizado){
        return productoRepository.findById(id_producto)
                .map(producto ->{
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setCategoria(productoActualizado.getCategoria());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    producto.setDisponible(productoActualizado.isDisponible());

                    Producto productoGuardado = productoRepository.save(producto);
                    return productoMapper.toDTO(productoGuardado);
                }).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: "+ id_producto));
    }

    // *** MÉTODOS PERSONALIZADOS ***

    // Buscar por nombre
    public ProductoDTO findByNombre(String nombre){
        Producto producto = productoRepository.findByNombre(nombre).orElseThrow(()-> new ProductoNotFoundException("Producto no encontrado por su nombre: "+ nombre));
        return productoMapper.toDTO(producto);
    }

    public List<ProductoDTO> findAllByCategoria(CategoriaProducto categoriaProducto){
        List<Producto> listaProductos = productoRepository.findAllByCategoria(categoriaProducto);
        List<ProductoDTO> listaProductosDTO = new ArrayList<>();
        for (Producto p : listaProductos) {
            listaProductosDTO.add(productoMapper.toDTO(p));
        }
        return listaProductosDTO;
    }
}
