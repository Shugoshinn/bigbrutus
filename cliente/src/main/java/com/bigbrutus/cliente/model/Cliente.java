package com.bigbrutus.cliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cliente")

public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_cliente;

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        private String nombre;

        @NotBlank(message = "El apellido no puede estar vacío")
        @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
        private String apellido;

        @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
        private String telefono;

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El formato del email no es válido")
        @Column(unique = true)
        private String email;

        @NotBlank(message = "La direccion no puede estar vacia")
        @Column(nullable = false)
        private String direccion;

        @NotNull(message = "Este campo no puede estar vacío")
        private LocalDate fechaRegistro;

        @Column(nullable = false)
        private boolean activo;
}
