package com.bigbrutus.vendedor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedor")
    private Long idVendedor;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre del vendedor no puedes ser menor a los 2 caracteres")
    @Size(max = 50, message= "El nombre del vendedor no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, message = "El apellido del vendedor no puedes ser menor a los 2 caracteres")
    @Size(max = 50, message= "El apellido del vendedor no puede superar los 50 caracteres")
    private String apellido;

    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Correo electronico no válido")
    @Size(max = 100)
    private String email;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @NotNull(message = "Error! debe existir un salario")
    @PositiveOrZero(message = "El salario debe ser positivo")
    private BigDecimal salario;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    @Column(nullable = false)
    private EstadoVendedor estado;

    @NotNull(message = "La sucursal es obligatoria")
    @Column(name = "id_sucursal")
    private Long idSucursal;


    @PrePersist
    protected void onCreate() {
        this.fechaContratacion = LocalDate.now();
        if (this.estado == null) {
            this.estado = EstadoVendedor.ACTIVO;
        }
    }

}