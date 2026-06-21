package com.bigbrutus.pedido;

import com.bigbrutus.pedido.controller.PedidoController;
import com.bigbrutus.pedido.dto.PedidoDTO;
import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.MetodoPago;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.model.TipoPedido;
import com.bigbrutus.pedido.service.PedidoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
@DisplayName("Pruebas en la capa Controller de Pedidos")
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(
                1L,
                LocalDate.now(),
                EstadoPedido.EN_ESPERA,
                TipoPedido.PREPARANDO,
                "Av. Siempre Viva 742",
                BigDecimal.valueOf(15000),
                MetodoPago.EFECTIVO,
                100L, 200L, 300L, 400L
        );

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId_pedido(1L);
        pedidoDTO.setDireccion_entrega("Av. Siempre Viva 742");
        pedidoDTO.setTotal(BigDecimal.valueOf(15000));
        pedidoDTO.setNombre_cliente("Juan Perez");
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/listar-todo - Retorna 200 OK y lista de entidades")
    public void testListarTodo() throws Exception {
        when(pedidoService.findAll()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/v1/pedidos/listar-todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_pedido").value(1))
                .andExpect(jsonPath("$[0].direccion_entrega").value("Av. Siempre Viva 742"))
                .andExpect(jsonPath("$[0].total").value(15000));
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/{id} - Retorna 200 OK y la entidad")
    void testBuscarPorID() throws Exception {
        when(pedidoService.findByID(1L)).thenReturn(pedido);

        mockMvc.perform(get("/api/v1/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_pedido").value(1))
                .andExpect(jsonPath("$.direccion_entrega").value("Av. Siempre Viva 742"));
    }

    @Test
    @DisplayName("POST /api/v1/pedidos - Retorna 201 CREATED y entidad guardada")
    void testRegistrar() throws Exception {
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_pedido").value(1));
    }

    @Test
    @DisplayName("PUT /api/v1/pedidos/{id} - Retorna 200 OK y entidad actualizada")
    void testActualizar() throws Exception {
        when(pedidoService.update(eq(1L), any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(put("/api/v1/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_pedido").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/pedidos/{id} - Retorna 204 NO CONTENT")
    void testEliminarPorID() throws Exception {
        mockMvc.perform(delete("/api/v1/pedidos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/lista-detallada - Retorna 200 OK y lista de DTOs")
    void testListaDetallada() throws Exception {
        when(pedidoService.listaDetallada()).thenReturn(List.of(pedidoDTO));

        mockMvc.perform(get("/api/v1/pedidos/lista-detallada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_pedido").value(1))
                .andExpect(jsonPath("$[0].nombre_cliente").value("Juan Perez"));
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/listar-por-estado/{estado} - Retorna 200 OK y lista DTO")
    void testListarPorEstado() throws Exception {
        when(pedidoService.findAllByEstado(EstadoPedido.EN_ESPERA)).thenReturn(List.of(pedidoDTO));

        mockMvc.perform(get("/api/v1/pedidos/listar-por-estado/EN_ESPERA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_pedido").value(1));
    }
}