package com.bigbrutus.producto;

import com.bigbrutus.producto.dto.ProductoDTO;
import com.bigbrutus.producto.exception.ProductoNotFoundException;
import com.bigbrutus.producto.mapper.ProductoMapper;
import com.bigbrutus.producto.model.CategoriaProducto;
import com.bigbrutus.producto.model.Producto;
import com.bigbrutus.producto.repository.ProductoRepository;

import com.bigbrutus.producto.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ProductoService (Pizzería)")
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    public void setUp() {
        // Configuramos una entidad Producto adaptada a una pizzería
        producto = new Producto(
                1L,
                "Pizza Pepperoni Familiar",
                "Pizza de 33cm con extra queso mozzarella y pepperoni",
                CategoriaProducto.COMIDA, // Reemplaza null por tu categoría real, ej: CategoriaProducto.PIZZAS
                12500L, // Precio en pesos
                50,     // Stock de ingredientes o unidades pre-armadas
                true
        );

        // Creamos un Mock del DTO para simular las respuestas del Mapper
        productoDTO = mock(ProductoDTO.class);
    }

    @Test
    @DisplayName("Debe listar todos los productos en formato Entidad")
    public void findAll_deberiaRetornarListaDeProductos() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pizza Pepperoni Familiar", resultado.get(0).getNombre());

        verify(productoRepository).findAll();
    }

    @Test
    @DisplayName("Debe listar todos los productos en formato DTO")
    public void findAllDTO_deberiaRetornarListaDeProductosDTO() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        List<ProductoDTO> resultado = productoService.findAllDTO();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(productoDTO, resultado.get(0));

        verify(productoRepository).findAll();
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debe buscar un producto por ID y retornar su DTO")
    public void findByID_cuandoExiste_deberiaRetornarProductoDTO() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        ProductoDTO resultado = productoService.findByID(1L);

        assertNotNull(resultado);
        assertEquals(productoDTO, resultado);

        verify(productoRepository).findById(1L);
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debe lanzar ProductoNotFoundException al buscar por ID inexistente")
    public void findByID_cuandoNoExiste_deberiaLanzarException() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        ProductoNotFoundException exception = assertThrows(
                ProductoNotFoundException.class,
                () -> productoService.findByID(99L)
        );

        assertEquals("Producto no encontrado con id: 99", exception.getMessage());
        verify(productoRepository).findById(99L);
        verify(productoMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Debe guardar un producto y retornar su DTO")
    public void save_deberiaGuardarYRetornarProductoDTO() {
        when(productoRepository.save(producto)).thenReturn(producto);
        when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        ProductoDTO resultado = productoService.save(producto);

        assertNotNull(resultado);
        assertEquals(productoDTO, resultado);

        verify(productoRepository).save(producto);
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debe eliminar un producto por ID si existe")
    public void deleteById_cuandoExiste_deberiaEliminar() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        productoService.deleteById(1L);

        verify(productoRepository).existsById(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar ProductoNotFoundException al eliminar un ID inexistente")
    public void deleteById_cuandoNoExiste_deberiaLanzarException() {
        when(productoRepository.existsById(99L)).thenReturn(false);

        ProductoNotFoundException exception = assertThrows(
                ProductoNotFoundException.class,
                () -> productoService.deleteById(99L)
        );

        assertEquals("Producto no encontrado con id: 99", exception.getMessage());
        verify(productoRepository).existsById(99L);
        verify(productoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe actualizar un producto existente y retornar su DTO")
    public void update_cuandoExiste_deberiaActualizarYRetornarDTO() {
        // Simulamos una actualización: cambia de tamaño familiar a mediana y baja el precio
        Producto productoActualizado = new Producto(1L, "Pizza Pepperoni Mediana", "Pizza de 25cm", null, 8500L, 20, true);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);
        when(productoMapper.toDTO(productoActualizado)).thenReturn(productoDTO);

        ProductoDTO resultado = productoService.update(1L, productoActualizado);

        assertNotNull(resultado);
        assertEquals(productoDTO, resultado);

        verify(productoRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
    }
    
    @Test
    @DisplayName("Debe retornar una lista de DTOs filtrados por categoría")
    public void findAllByCategoria_deberiaRetornarListaDeProductosDTO() {
        when(productoRepository.findAllByCategoria(any())).thenReturn(List.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        // Se envía null solo para la prueba (reemplazar por tu Enum real, ej: CategoriaProducto.BEBIDAS)
        List<ProductoDTO> resultado = productoService.findAllByCategoria(null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(productoDTO, resultado.get(0));

        verify(productoRepository).findAllByCategoria(any());
    }
}