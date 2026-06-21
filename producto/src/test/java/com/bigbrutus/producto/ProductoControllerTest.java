package com.bigbrutus.producto;

import com.bigbrutus.producto.controller.ProductoController;
import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.model.CategoriaProducto;
import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.service.ProductoService;

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

@WebMvcTest(ProductoController.class)
@DisplayName("Pruebas en la capa Controller de Productos (Pizzería)")
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoDTO productoDTO;
    private Producto productoSinId;

    @BeforeEach
    void setUp() {
        // Objeto simulado para el cuerpo de la petición
        // Reemplaza "CategoriaProducto.PIZZAS" por el nombre real que tengas en tu Enum
        productoSinId = new Producto(null, "Pizza Pepperoni Familiar", "Pizza de 33cm con extra queso", CategoriaProducto.COMIDA, 12500L, 50, true);

        // DTO simulado para la respuesta del servicio (creado con Setters porque el DTO no tiene @AllArgsConstructor)
        productoDTO = new ProductoDTO();
        productoDTO.setId_producto(1L);
        productoDTO.setNombre("Pizza Pepperoni Familiar");
        productoDTO.setDescripcion("Pizza de 33cm con extra queso");
        productoDTO.setCategoria("PIZZAS");
        productoDTO.setPrecio(12500L);
        productoDTO.setStock(50);
        productoDTO.setDisponible(true);
    }

    @Test
    @DisplayName("GET /api/v1/productos/listado - Debería retornar 200 OK y la lista de productos DTO")
    public void testEndpointListarTodos() throws Exception {
        // Arrange
        when(productoService.findAllDTO()).thenReturn(List.of(productoDTO));

        // Act & Assert
        // Ojo aquí: Actualizado para usar la ruta exacta de tu controlador
        mockMvc.perform(get("/api/v1/productos/listado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_producto").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Pizza Pepperoni Familiar"))
                .andExpect(jsonPath("$[0].descripcion").value("Pizza de 33cm con extra queso"))
                .andExpect(jsonPath("$[0].precio").value(12500))
                .andExpect(jsonPath("$[0].stock").value(50))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    @DisplayName("POST /api/v1/productos - Debería retornar 201 CREATED y el producto creado")
    void testEndpointCrear() throws Exception {
        // Arrange
        when(productoService.save(any(Producto.class))).thenReturn(productoDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_producto").value(1))
                .andExpect(jsonPath("$.nombre").value("Pizza Pepperoni Familiar"))
                .andExpect(jsonPath("$.precio").value(12500))
                .andExpect(jsonPath("$.stock").value(50));
    }

    @Test
    @DisplayName("GET /api/v1/productos/{id} - Debería retornar 200 OK y el producto encontrado")
    void testEndpointBuscarPorId() throws Exception {
        // Arrange
        when(productoService.findByID(1L)).thenReturn(productoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_producto").value(1))
                .andExpect(jsonPath("$.nombre").value("Pizza Pepperoni Familiar"))
                .andExpect(jsonPath("$.precio").value(12500));
    }

    @Test
    @DisplayName("PUT /api/v1/productos/{id} - Debería retornar 200 OK y el producto actualizado")
    void testEndpointActualizar() throws Exception {
        // Arrange
        when(productoService.update(eq(1L), any(Producto.class))).thenReturn(productoDTO);

        // Act & Assert
        mockMvc.perform(put("/api/v1/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoSinId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_producto").value(1))
                .andExpect(jsonPath("$.nombre").value("Pizza Pepperoni Familiar"));
    }

    @Test
    @DisplayName("DELETE /api/v1/productos/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        // Arrange (No es necesario simular retorno para void, solo ejecutar)

        // Act & Assert
        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());
    }
}