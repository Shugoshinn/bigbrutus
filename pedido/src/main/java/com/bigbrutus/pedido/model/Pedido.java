package com.bigbrutus.pedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pedido {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_pedido;

        private LocalDate fecha_pedido;

        private String estado;
        private String tipo_entrega;

        @Column(columnDefinition = "TEXT")
        private String direccion_entrega;

        private Long total;
        private String metodo_pago;

        private Long id_cliente;
        private Long id_vendedor;
        private Long id_repartidor;
        private Long id_sucursal;
}
