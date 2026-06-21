package com.bigbrutus.pedido;

import com.bigbrutus.pedido.clients.ClienteFeign;
import com.bigbrutus.pedido.clients.RepartidorFeign;
import com.bigbrutus.pedido.clients.SucursalFeign;
import com.bigbrutus.pedido.clients.VendedorFeign;
import com.bigbrutus.pedido.dto.*;
import com.bigbrutus.pedido.exception.PedidoNotFoundException;
import com.bigbrutus.pedido.mapper.PedidoMapper;
import com.bigbrutus.pedido.model.EstadoPedido;
import com.bigbrutus.pedido.model.MetodoPago;
import com.bigbrutus.pedido.model.Pedido;
import com.bigbrutus.pedido.model.TipoPedido;
import com.bigbrutus.pedido.repository.PedidoRepository;

import com.bigbrutus.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para PedidoService")
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    // Mocks de los clientes Feign
    @Mock private ClienteFeign clienteFeign;
    @Mock private RepartidorFeign repartidorFeign;
    @Mock private SucursalFeign sucursalFeign;
    @Mock private VendedorFeign vendedorFeign;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    public void setUp() {
        // Asumimos los enums según los mensajes de validación de tu modelo
        pedido = new Pedido(
                1L,
                LocalDate.now(),
                EstadoPedido.EN_ESPERA, // Ajustar si el enum real se llama distinto
                TipoPedido.PREPARANDO,  // Ajustar si el enum real se llama distinto
                "Av. Siempre Viva 742",
                BigDecimal.valueOf(15000),
                MetodoPago.EFECTIVO,    // Ajustar si el enum real se llama distinto
                100L, // id_cliente
                200L, // id_vendedor
                300L, // id_repartidor
                400L  // id_sucursal
        );

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId_pedido(1L);
        pedidoDTO.setDireccion_entrega("Av. Siempre Viva 742");
        pedidoDTO.setTotal(BigDecimal.valueOf(15000));
    }

    @Test
    @DisplayName("Debe listar todos los pedidos (Entidad)")
    public void findAll_deberiaRetornarListaDePedidos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Av. Siempre Viva 742", resultado.get(0).getDireccion_entrega());
        verify(pedidoRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un pedido por ID (Entidad)")
    public void findByID_cuandoExiste_deberiaRetornarPedido() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.findByID(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId_pedido());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar un pedido")
    public void save_deberiaGuardarYRetornarPedido() {
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoService.save(pedido);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId_pedido());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    @DisplayName("Debe actualizar dirección y método de pago de un pedido")
    public void update_cuandoExiste_deberiaActualizarYRetornarPedido() {
        Pedido pedidoActualizado = new Pedido();
        pedidoActualizado.setDireccion_entrega("Nueva Direccion 123");
        pedidoActualizado.setMetodo_pago(MetodoPago.DEBITO);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.update(1L, pedidoActualizado);

        assertNotNull(resultado);
        assertEquals("Nueva Direccion 123", resultado.getDireccion_entrega());
        assertEquals(MetodoPago.DEBITO, resultado.getMetodo_pago());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    @DisplayName("Debe lanzar PedidoNotFoundException al eliminar un ID inexistente")
    public void deleteByID_cuandoNoExiste_deberiaLanzarException() {
        when(pedidoRepository.existsById(99L)).thenReturn(false);

        assertThrows(PedidoNotFoundException.class, () -> pedidoService.deleteByID(99L));
        verify(pedidoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe retornar la lista detallada consultando Feign Clients")
    public void listaDetallada_deberiaRetornarListaDTO() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));
        when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        // Mocks para los Feign Clients (retornando nulos o mocks ya que tu código convierte los IDs a String de todos modos)
        when(clienteFeign.buscarPorID(100L)).thenReturn(mock(ClienteDTO.class));
        when(sucursalFeign.buscarPorID(400L)).thenReturn(mock(SucursalDTO.class));
        when(repartidorFeign.buscarPorID(300L)).thenReturn(mock(RepartidorDTO.class));
        when(vendedorFeign.buscarPorID(200L)).thenReturn(mock(VendedorDTO.class));

        List<PedidoDTO> resultado = pedidoService.listaDetallada();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        // Verifica la lógica particular de tu servicio que castea el ID a String
        assertEquals("100", resultado.get(0).getNombre_cliente());
        assertEquals("300", resultado.get(0).getNombre_repartidor());
    }
}
