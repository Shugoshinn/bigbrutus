package com.bigbrutus.sucursales.dto;

import lombok.Data;

@Data
public class SucursalDTO {
    private String nombre;
    private String direccionCompleta;
    private String horario;
    private String activa;
}
