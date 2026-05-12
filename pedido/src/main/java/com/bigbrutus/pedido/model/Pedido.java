package com.bigbrutus.pedido.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pedido {
    private int id_pedido;
    private LocalDate fecha_pedido;
    private String estado;
    private String tipo_entrega;
    private String direccion_entrega;
    private Long total;
    private String metodo_pago;
}
