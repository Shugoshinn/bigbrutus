package com.bigbrutus.producto.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id_producto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Long precio;
    private int stock;
    private boolean disponible;
}
