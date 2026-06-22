package com.bigbrutus.vehiculos;

import com.bigbrutus.vehiculos.controller.VehiculoController;
import com.bigbrutus.vehiculos.dto.VehiculoDTO;
import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import com.bigbrutus.vehiculos.model.Vehiculo;
import com.bigbrutus.vehiculos.service.VehiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehiculoController.class)
@DisplayName("Pruebas en la capa Controller de Vehículos")
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehiculoService vehiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehiculo vehiculo;
    private VehiculoDTO vehiculoDTO;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo(
                1L,
                "ABCD12",
                "Yamaha",
                "NMAX",
                2024,
                TipoVehiculo.MOTO,
                EstadoVehiculo.DISPONIBLE
        );

        vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setIdVehiculo(1L);
        vehiculoDTO.setPatente("ABCD12");
        vehiculoDTO.setInfoVehiculo("MOTO Yamaha NMAX");
        vehiculoDTO.setEstado(EstadoVehiculo.DISPONIBLE);
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos - Retorna 200 OK y lista de DTOs")
    public void testListarTodoDTO() throws Exception {
        when(vehiculoService.findAllDTO()).thenReturn(List.of(vehiculoDTO));

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idVehiculo").value(1))
                .andExpect(jsonPath("$[0].patente").value("ABCD12"))
                .andExpect(jsonPath("$[0].infoVehiculo").value("MOTO Yamaha NMAX"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/listar-todo - Retorna 200 OK y lista de entidades")
    public void testListarTodo() throws Exception {
        when(vehiculoService.findAll()).thenReturn(List.of(vehiculo));

        mockMvc.perform(get("/api/v1/vehiculos/listar-todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idVehiculo").value(1))
                .andExpect(jsonPath("$[0].patente").value("ABCD12"))
                .andExpect(jsonPath("$[0].marca").value("Yamaha"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/{id} - Retorna 200 OK y DTO")
    void testBuscarPorID() throws Exception {
        when(vehiculoService.findByID(1L)).thenReturn(vehiculoDTO);

        mockMvc.perform(get("/api/v1/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVehiculo").value(1))
                .andExpect(jsonPath("$.patente").value("ABCD12"));
    }

    @Test
    @DisplayName("POST /api/v1/vehiculos - Retorna 201 CREATED y entidad guardada")
    void testRegistrar() throws Exception {
        when(vehiculoService.save(any(Vehiculo.class))).thenReturn(vehiculo);

        mockMvc.perform(post("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idVehiculo").value(1))
                .andExpect(jsonPath("$.patente").value("ABCD12"));
    }

    @Test
    @DisplayName("DELETE /api/v1/vehiculos/{id} - Retorna 204 NO CONTENT")
    void testEliminarPorID() throws Exception {
        mockMvc.perform(delete("/api/v1/vehiculos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /api/v1/vehiculos/{id} - Retorna 200 OK y entidad actualizada")
    void testActualizar() throws Exception {
        when(vehiculoService.update(eq(1L), any(Vehiculo.class))).thenReturn(vehiculo);

        mockMvc.perform(put("/api/v1/vehiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVehiculo").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/patente/{patente} - Retorna 200 OK y DTO")
    void testBuscarPorPatente() throws Exception {
        when(vehiculoService.findByPatente("ABCD12")).thenReturn(vehiculoDTO);

        mockMvc.perform(get("/api/v1/vehiculos/patente/ABCD12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVehiculo").value(1))
                .andExpect(jsonPath("$.patente").value("ABCD12"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/listar-por-estado/{estado} - Retorna 200 OK y lista DTO")
    void testListarPorEstadoDTO() throws Exception {
        when(vehiculoService.findAllByEstado(EstadoVehiculo.DISPONIBLE)).thenReturn(List.of(vehiculoDTO));

        mockMvc.perform(get("/api/v1/vehiculos/listar-por-estado/DISPONIBLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idVehiculo").value(1))
                .andExpect(jsonPath("$[0].estado").value("DISPONIBLE"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/listar-por-tipo/{tipo} - Retorna 200 OK y lista DTO")
    void testListarPorTipoDTO() throws Exception {
        when(vehiculoService.findAllByTipoDTO(TipoVehiculo.MOTO)).thenReturn(List.of(vehiculoDTO));

        mockMvc.perform(get("/api/v1/vehiculos/listar-por-tipo/MOTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idVehiculo").value(1))
                .andExpect(jsonPath("$[0].infoVehiculo").value("MOTO Yamaha NMAX"));
    }

    @Test
    @DisplayName("PATCH /api/v1/vehiculos/{id}/{estado} - Retorna 200 OK y entidad actualizada")
    void testActualizarEstado() throws Exception {
        vehiculo.setEstado(EstadoVehiculo.EN_REPARTO);
        when(vehiculoService.updateEstado(1L, EstadoVehiculo.EN_REPARTO)).thenReturn(vehiculo);

        mockMvc.perform(patch("/api/v1/vehiculos/1/EN_REPARTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVehiculo").value(1))
                .andExpect(jsonPath("$.estado").value("EN_REPARTO"));
    }
}
