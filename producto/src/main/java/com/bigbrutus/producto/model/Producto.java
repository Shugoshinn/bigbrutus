package com.bigbrutus.producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id_producto;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 4, message = "El nombre del prducto no puedes ser menor a los 4 caracteres")
    @Size(max = 35, message= "El nombre del producto no puede superar los 35 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(min = 10, message = "La descripcion no puede ser menor a los 10 caracteres")
    @Size(max = 80, message= "La descripcion no puede superar los 80 caracteres")
    private String descripcion;

    @NotNull(message = "La categoría es obligatoria")
    @Enumerated(EnumType.STRING)
    private CategoriaProducto categoria;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor a cero")
    private Long precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private boolean disponible;
}
