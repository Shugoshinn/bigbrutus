package com.bigbrutus.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @NotNull(message = "La fecha no puede estar vacia.")
    @PastOrPresent(message = "La fecha del pedido no puede ser futura.")
    private LocalDate fecha_pedido;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Ingresar un tipo de estado! [EN_CAMINO, EN_ESPERA]")
    private EstadoPedido estado;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Ingresar un tipo de pedido! [ENTREGADO, PREPARANDO]")
    private TipoPedido tipo_pedido;

    @NotBlank(message = "La dirección de entrega no puede estar vacía.")
    @Size(min = 5, max = 100, message = "La dirección debe ser descriptiva (entre 5 y 500 caracteres).")
    @Column(columnDefinition = "TEXT")
    private String direccion_entrega;

    @NotNull(message = "El total no puede estar vacío.")
    @Positive(message = "El total debe ser mayor a cero.")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Ingresar un metodo de pago valido! [EFECTIVO, CREDITO, DEBITO]")
    private MetodoPago metodo_pago;

    @NotNull(message = "Id de cliente no puede estar vacio")
    @Column(name = "id_cliente")
    private Long cliente;

    @NotNull(message = "Id de vendedor no puede estar vacio")
    @Column(name = "id_vendedor")
    private Long vendedor;

    @NotNull(message = "Id de repartidor no puede estar vacio")
    @Column(name = "id_repartidor")
    private Long repartidor;

    @NotNull(message = "Id de sucursal no puede estar vacio")
    @Column(name = "id_sucursal")
    private Long sucursal;
}
