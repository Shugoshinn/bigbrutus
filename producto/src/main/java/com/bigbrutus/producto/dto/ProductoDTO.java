package com.bigbrutus.producto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "nombre", "descripcion", "categoria", "precio", "stock", "disponible"})
public class ProductoDTO {
    private Long id_producto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Long precio;
    private int stock;
    private boolean disponible;
}
