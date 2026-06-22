package com.bigbrutus.cliente;

import com.bigbrutus.cliente.dto.ClienteDTO;
import com.bigbrutus.cliente.mapper.ClienteMapper;
import com.bigbrutus.cliente.model.Cliente;
import com.bigbrutus.cliente.repository.ClienteRepository;
import com.bigbrutus.cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClienteService")
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L,
                "Juan",
                "Perez",
                "123456789",
                "juan@example.com",
                "Av. Siempre Viva 123",
                LocalDate.now(),
                true);

        clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(1L);
        clienteDTO.setNombreCompleto("Juan Perez");
        clienteDTO.setTelefono("123456789");
        clienteDTO.setEmail("juan@example.com");
        clienteDTO.setFechaRegistro(cliente.getFechaRegistro());
        clienteDTO.setActivo(true);
    }

    @Test
    @DisplayName("Debe listar todos los clientes como DTO")
    void findDTOList_deberiaRetornarListaDTO() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toDTOlist(List.of(cliente))).thenReturn(List.of(clienteDTO));

        List<ClienteDTO> resultado = clienteService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombreCompleto());
        verify(clienteRepository).findAll();
        verify(clienteMapper).toDTOlist(List.of(cliente));
    }

    @Test
    @DisplayName("Debe buscar un cliente por ID y retornar DTO")
    void findDTO_deberiaRetornarDTO() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.findDTO(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCliente());
        verify(clienteRepository).findById(1L);
        verify(clienteMapper).toDTO(cliente);
    }

    @Test
    @DisplayName("Debe guardar un cliente")
    void save_deberiaGuardarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.save(cliente);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Debe actualizar un cliente existente")
    void update_deberiaActualizarCliente() {
        Cliente datos = new Cliente();
        datos.setNombre("Ana");
        datos.setApellido("Gomez");
        datos.setTelefono("987654321");
        datos.setEmail("ana@example.com");
        datos.setFechaRegistro(LocalDate.now());
        datos.setActivo(false);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.update(1L, datos);

        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
        assertEquals("ana@example.com", resultado.getEmail());
        assertFalse(resultado.isActivo());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Debe buscar cliente por email y retornar DTO cuando existe")
    void findByEmail_deberiaRetornarDTO() {
        when(clienteRepository.findByEmail("juan@example.com")).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.findByEmail("juan@example.com");

        assertNotNull(resultado);
        assertEquals("juan@example.com", resultado.getEmail());
        verify(clienteRepository).findByEmail("juan@example.com");
        verify(clienteMapper).toDTO(cliente);
    }

    @Test
    @DisplayName("Debe retornar lista de clientes activos")
    void findActivos_deberiaRetornarActivos() {
        when(clienteRepository.findByActivo(true)).thenReturn(List.of(cliente));
        when(clienteMapper.toDTOlist(List.of(cliente))).thenReturn(List.of(clienteDTO));

        List<ClienteDTO> resultado = clienteService.findActivos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository).findByActivo(true);
        verify(clienteMapper).toDTOlist(List.of(cliente));
    }

    @Test
    @DisplayName("Debe actualizar solo el estado activo del cliente")
    void updateActivo_deberiaActualizarActivo() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.updateActivo(1L, false);

        assertNotNull(resultado);
        assertFalse(resultado.isActivo());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(cliente);
    }
}