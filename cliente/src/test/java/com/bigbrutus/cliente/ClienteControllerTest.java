package com.bigbrutus.cliente;

import com.bigbrutus.cliente.controller.ClienteController;
import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.model.Cliente;
import com.bigbrutus.cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClienteController")
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(1L);
        clienteDTO.setNombreCompleto("Juan Perez");
        clienteDTO.setTelefono("123456789");
        clienteDTO.setEmail("juan@example.com");
        clienteDTO.setFechaRegistro(LocalDate.now());
        clienteDTO.setActivo(true);

        cliente = new Cliente(1L,
                "Juan",
                "Perez",
                "123456789",
                "juan@example.com",
                "Av. Siempre Viva 123",
                LocalDate.now(),
                true);
    }

    @Test
    @DisplayName("Debe listar clientes y retornar 200")
    void listar_deberiaRetornarListaDeClientes() {
        when(clienteService.findDTOList()).thenReturn(List.of(clienteDTO));

        ResponseEntity<List<ClienteDTO>> response = clienteController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Juan Perez", response.getBody().get(0).getNombreCompleto());
        verify(clienteService).findDTOList();
    }

    @Test
    @DisplayName("Debe buscar cliente por ID y retornar 200 cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarOk() {
        when(clienteService.findDTO(1L)).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Juan Perez", response.getBody().getNombreCompleto());
        verify(clienteService).findDTO(1L);
    }

    @Test
    @DisplayName("Debe buscar cliente por ID y retornar 404 cuando no existe")
    void buscarPorId_cuandoNoExiste_deberiaRetornarNotFound() {
        when(clienteService.findDTO(99L)).thenReturn(null);

        ResponseEntity<ClienteDTO> response = clienteController.buscarPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clienteService).findDTO(99L);
    }

    @Test
    @DisplayName("Debe registrar cliente y retornar 201")
    void registrar_deberiaRetornarCreated() {
        when(clienteService.save(cliente)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.registrar(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(clienteService).save(cliente);
    }

    @Test
    @DisplayName("Debe actualizar cliente existente y retornar 200")
    void actualizar_deberiaRetornarOk() {
        when(clienteService.update(1L, cliente)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.actualizar(1L, cliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(clienteService).update(1L, cliente);
    }

    @Test
    @DisplayName("Debe retornar 404 al actualizar cliente inexistente")
    void actualizar_cuandoNoExiste_deberiaRetornarNotFound() {
        when(clienteService.update(99L, cliente)).thenReturn(null);

        ResponseEntity<Cliente> response = clienteController.actualizar(99L, cliente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clienteService).update(99L, cliente);
    }

    @Test
    @DisplayName("Debe borrar cliente y retornar 204")
    void borrar_deberiaRetornarNoContent() {
        doNothing().when(clienteService).delete(1L);

        ResponseEntity<Void> response = clienteController.borrar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService).delete(1L);
    }

    @Test
    @DisplayName("Debe buscar cliente por email y retornar 200 cuando existe")
    void buscarPorEmail_deberiaRetornarOk() {
        when(clienteService.findByEmail("juan@example.com")).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.buscarPorEmail("juan@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("juan@example.com", response.getBody().getEmail());
        verify(clienteService).findByEmail("juan@example.com");
    }

    @Test
    @DisplayName("Debe retornar 404 al buscar cliente por email inexistente")
    void buscarPorEmail_cuandoNoExiste_deberiaRetornarNotFound() {
        when(clienteService.findByEmail("noexiste@example.com")).thenReturn(null);

        ResponseEntity<ClienteDTO> response = clienteController.buscarPorEmail("noexiste@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clienteService).findByEmail("noexiste@example.com");
    }

    @Test
    @DisplayName("Debe buscar clientes por nombre y retornar 200")
    void buscarPorNombre_deberiaRetornarLista() {
        when(clienteService.findByNombre("Juan")).thenReturn(List.of(clienteDTO));

        ResponseEntity<List<ClienteDTO>> response = clienteController.buscarPorNombre("Juan");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(clienteService).findByNombre("Juan");
    }

    @Test
    @DisplayName("Debe listar clientes activos y retornar 200")
    void listarActivos_deberiaRetornarListaActivos() {
        when(clienteService.findActivos()).thenReturn(List.of(clienteDTO));

        ResponseEntity<List<ClienteDTO>> response = clienteController.listarActivos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(clienteService).findActivos();
    }

    @Test
    @DisplayName("Debe actualizar sólo el estado activo y retornar 200 cuando existe")
    void actualizarActivo_deberiaRetornarOk() {
        when(clienteService.updateActivo(1L, false)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.actualizarActivo(1L, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(clienteService).updateActivo(1L, false);
    }

    @Test
    @DisplayName("Debe retornar 404 al actualizar el estado activo de un cliente inexistente")
    void actualizarActivo_cuandoNoExiste_deberiaRetornarNotFound() {
        when(clienteService.updateActivo(99L, false)).thenReturn(null);

        ResponseEntity<Cliente> response = clienteController.actualizarActivo(99L, false);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clienteService).updateActivo(99L, false);
    }
}
