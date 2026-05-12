package com.bigbrutus.cliente.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "Cliente")

public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_cliente;

        @Column(nullable = false)
        private String nombre;

        @Column(nullable = false)
        private String apellido;

        @Column(nullable = false)
        private String telefono;

        @Column(unique = true, nullable = false)
        private String email;

        @Column(nullable = false)
        private String direccion;

        @Column(nullable = false)
        private LocalDate fechaRegistro;

        @Column(nullable = false)
        private boolean activo;
}
