package com.bigbrutus.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
        @Column(name = "id_pedido")
        private Long id_pedido;

        @NotNull(message = "La fecha no puede estar vacía!")
        @Min(value = 1800, message = "El año debe ser válido! (Mayor a 1800)")
        @Max(value = 2100, message = "El año debe ser válido! (Menor a 2100)")
        private LocalDate fecha_pedido;

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Por favor, ingresar un tipo de estadp! [EN_CAMINO, EN_ESPERA]")
        private EstadoPedido estado;

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Por favor, ingresar un tipo de pedido! [ENTREGADO, PREPARANDO]")
        private TipoPedido tipo_pedido;

        @Column(columnDefinition = "TEXT")
        private String direccion_entrega;

        private Long total;
        @Enumerated(EnumType.STRING)
        @NotNull(message = "Por favor, ingresar un metodo de pago! [EFECTIVO, CREDITO, DEBITO]")
        private MetodoPago metodo_pago;

        private Long id_cliente;
        private Long id_vendedor;
        private Long id_repartidor;
        private Long id_sucursal;
}
