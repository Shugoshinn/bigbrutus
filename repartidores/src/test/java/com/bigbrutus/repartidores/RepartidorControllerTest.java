package com.bigbrutus.repartidores;

import com.bigbrutus.repartidores.controller.RepartidorController;
import com.bigbrutus.repartidores.dto.RepartidorDTO;
import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.model.TipoVehiculo;
import com.bigbrutus.repartidores.service.RepartidorService;
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

@WebMvcTest(RepartidorController.class)
@DisplayName("Pruebas en la capa Controller de Repartidores")
class RepartidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepartidorService repartidorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Repartidor repartidor;
    private RepartidorDTO repartidorDTO;

    @BeforeEach
    void setUp() {
        repartidor = new Repartidor(
                1L,
                "Micha",
                "Fuentes",
                912345678,
                "Clase B",
                EstadoRepartidor.DISPONIBLE,
                1L
        );

        repartidorDTO = new RepartidorDTO();
        repartidorDTO.setId(1L);
        repartidorDTO.setNombreCompleto("Micha Fuentes");
        repartidorDTO.setTelefono(912345678);
        repartidorDTO.setEstado(EstadoRepartidor.DISPONIBLE);
        repartidorDTO.setVehiculo("MOTO Yamaha NMAX");
        repartidorDTO.setPatente("ABCD12");
    }

    @Test
    @DisplayName("GET /api/v1/repartidores - Retorna 200 OK y lista de entidades")
    public void testListarTodo() throws Exception {
        when(repartidorService.findAll()).thenReturn(List.of(repartidor));

        mockMvc.perform(get("/api/v1/repartidores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRepartidor").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Micha"))
                .andExpect(jsonPath("$[0].vehiculo").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/repartidores/{id} - Retorna 200 OK y la entidad")
    void testBuscarPorID() throws Exception {
        when(repartidorService.findByID(1L)).thenReturn(repartidor);

        mockMvc.perform(get("/api/v1/repartidores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRepartidor").value(1))
                .andExpect(jsonPath("$.nombre").value("Micha"));
    }

    @Test
    @DisplayName("POST /api/v1/repartidores - Retorna 201 CREATED y entidad guardada")
    void testRegistrar() throws Exception {
        when(repartidorService.save(any(Repartidor.class))).thenReturn(repartidor);

        mockMvc.perform(post("/api/v1/repartidores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repartidor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRepartidor").value(1))
                .andExpect(jsonPath("$.nombre").value("Micha"));
    }

    @Test
    @DisplayName("PUT /api/v1/repartidores/{id} - Retorna 200 OK y entidad actualizada")
    void testActualizar() throws Exception {
        when(repartidorService.update(eq(1L), any(Repartidor.class))).thenReturn(repartidor);

        mockMvc.perform(put("/api/v1/repartidores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repartidor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRepartidor").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/repartidores/{id} - Retorna 204 NO CONTENT")
    void testEliminarPorID() throws Exception {
        mockMvc.perform(delete("/api/v1/repartidores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/repartidores/lista-detallada - Retorna 200 OK y lista de DTOs")
    void testListaDetallada() throws Exception {
        when(repartidorService.listaDetallada()).thenReturn(List.of(repartidorDTO));

        mockMvc.perform(get("/api/v1/repartidores/lista-detallada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Micha Fuentes"))
                .andExpect(jsonPath("$[0].vehiculo").value("MOTO Yamaha NMAX"));
    }

    @Test
    @DisplayName("GET /api/v1/repartidores/tipo-vehiculo/{tipo} - Retorna 200 OK y lista DTO")
    void testBuscarPorTipoVehiculo() throws Exception {
        when(repartidorService.listRepPorTipoVehiculo(TipoVehiculo.MOTO)).thenReturn(List.of(repartidorDTO));

        mockMvc.perform(get("/api/v1/repartidores/tipo-vehiculo/MOTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].vehiculo").value("MOTO Yamaha NMAX"));
    }

    @Test
    @DisplayName("GET /api/v1/repartidores/listar-por-estado/{estado} - Retorna 200 OK y lista DTO")
    void testListarPorEstado() throws Exception {
        when(repartidorService.findAllByEstado(EstadoRepartidor.DISPONIBLE)).thenReturn(List.of(repartidorDTO));

        mockMvc.perform(get("/api/v1/repartidores/listar-por-estado/DISPONIBLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("DISPONIBLE"));
    }
}
