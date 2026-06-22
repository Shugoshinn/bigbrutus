package com.bigbrutus.sucursales;

import com.bigbrutus.sucursales.controller.SucursalController;
import com.bigbrutus.sucursales.dto.SucursalDTO;
import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SucursalController.class)
@DisplayName("Pruebas en la capa Controller de Sucursales")
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sucursal sucursal;
    private SucursalDTO sucursalDTO;

    @BeforeEach
    void setUp() {
        sucursal = new Sucursal(
                1L,
                "Sucursal Centro",
                "Av. Siempre Viva 742",
                22334455,
                "Santiago",
                Time.valueOf("09:00:00"),
                Time.valueOf("18:00:00"),
                true,
                TipoSucursal.PARA_SERVIR
        );

        sucursalDTO = new SucursalDTO();
        sucursalDTO.setNombre("Sucursal Centro");
        sucursalDTO.setDireccionCompleta("Av. Siempre Viva 742, Santiago");
        sucursalDTO.setHorario("De 09:00:00 hasta 18:00:00");
        sucursalDTO.setActiva("Activa");
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/listar-todo - Retorna 200 OK y lista de entidades")
    public void testListarTodo() throws Exception {
        when(sucursalService.findAll()).thenReturn(List.of(sucursal));

        mockMvc.perform(get("/api/v1/sucursales/listar-todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSucursal").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sucursal Centro"))
                .andExpect(jsonPath("$[0].comuna").value("Santiago"));
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/{id} - Retorna 200 OK y DTO")
    void testBuscarPorID() throws Exception {
        when(sucursalService.findByID(1L)).thenReturn(sucursalDTO);

        mockMvc.perform(get("/api/v1/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Centro"))
                .andExpect(jsonPath("$.direccionCompleta").value("Av. Siempre Viva 742, Santiago"));
    }

    @Test
    @DisplayName("POST /api/v1/sucursales - Retorna 201 CREATED y entidad guardada")
    void testRegistrar() throws Exception {
        when(sucursalService.save(any(Sucursal.class))).thenReturn(sucursal);

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSucursal").value(1))
                .andExpect(jsonPath("$.nombre").value("Sucursal Centro"));
    }

    @Test
    @DisplayName("DELETE /api/v1/sucursales/{id} - Retorna 204 NO CONTENT")
    void testEliminarPorID() throws Exception {
        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/activo/{activa} - Retorna 200 OK y lista de entidades")
    void testFindAllByActiva() throws Exception {
        when(sucursalService.findAllByActiva(true)).thenReturn(List.of(sucursal));

        mockMvc.perform(get("/api/v1/sucursales/activo/true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSucursal").value(1))
                .andExpect(jsonPath("$[0].activa").value(true));
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/comuna/{comuna} - Retorna 200 OK y lista de entidades")
    void testFindAllByComuna() throws Exception {
        when(sucursalService.findAllByComuna("Santiago")).thenReturn(List.of(sucursal));

        mockMvc.perform(get("/api/v1/sucursales/comuna/Santiago"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSucursal").value(1))
                .andExpect(jsonPath("$[0].comuna").value("Santiago"));
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/tipo/{tipo} - Retorna 200 OK y lista de entidades")
    void testFindAllByTipo() throws Exception {
        when(sucursalService.findAllByTipo(TipoSucursal.PARA_SERVIR)).thenReturn(List.of(sucursal));

        mockMvc.perform(get("/api/v1/sucursales/tipo/PARA_SERVIR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSucursal").value(1))
                .andExpect(jsonPath("$[0].tipo").value("PARA_SERVIR"));
    }
}
