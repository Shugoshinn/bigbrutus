package com.bigbrutus.vendedor;

import com.bigbrutus.vendedor.controller.VendedorController;
import com.bigbrutus.vendedor.dto.VendedorDTO;
import com.bigbrutus.vendedor.model.EstadoVendedor;
import com.bigbrutus.vendedor.model.Vendedor;
import com.bigbrutus.vendedor.service.VendedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para VendedorController")
public class VendedorControllerTest {

    @Mock
    private VendedorService vendedorService;

    @InjectMocks
    private VendedorController vendedorController;

    private VendedorDTO vendedorDTO;
    private Vendedor vendedor;

    @BeforeEach
    void setUp() {
        vendedorDTO = new VendedorDTO();
        vendedorDTO.setIdVendedor(1L);
        vendedorDTO.setNombreCompleto("Pedro Gomez");
        vendedorDTO.setTelefono("123456789");
        vendedorDTO.setEmail("pedro@example.com");
        vendedorDTO.setFechaContratacion(LocalDate.now());
        vendedorDTO.setSalario(BigDecimal.valueOf(1200));
        vendedorDTO.setEstado("ACTIVO");
        vendedorDTO.setIdSucursal(1L);

        vendedor = new Vendedor(1L,
                "Pedro",
                "Gomez",
                "123456789",
                "pedro@example.com",
                LocalDate.now(),
                BigDecimal.valueOf(1200),
                EstadoVendedor.ACTIVO,
                1L);
    }

    @Test
    @DisplayName("Debe listar vendedores y retornar 200")
    void listar_deberiaRetornarLista() {
        when(vendedorService.findDTOList()).thenReturn(List.of(vendedorDTO));

        ResponseEntity<List<VendedorDTO>> response = vendedorController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(vendedorService).findDTOList();
    }

    @Test
    @DisplayName("Debe buscar vendedor por ID y retornar 200 cuando existe")
    void buscarPorId_deberiaRetornarOk() {
        when(vendedorService.findDTO(1L)).thenReturn(vendedorDTO);

        ResponseEntity<VendedorDTO> response = vendedorController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pedro Gomez", response.getBody().getNombreCompleto());
        verify(vendedorService).findDTO(1L);
    }

    @Test
    @DisplayName("Debe retornar 404 al buscar vendedor inexistente")
    void buscarPorId_cuandoNoExiste_deberiaRetornarNotFound() {
        when(vendedorService.findDTO(99L)).thenReturn(null);

        ResponseEntity<VendedorDTO> response = vendedorController.buscarPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(vendedorService).findDTO(99L);
    }

    @Test
    @DisplayName("Debe registrar vendedor y retornar 201")
    void registrar_deberiaRetornarCreated() {
        when(vendedorService.save(vendedor)).thenReturn(vendedor);

        ResponseEntity<Vendedor> response = vendedorController.registrar(vendedor);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(vendedor, response.getBody());
        verify(vendedorService).save(vendedor);
    }

    @Test
    @DisplayName("Debe actualizar vendedor y retornar 200")
    void actualizar_deberiaRetornarOk() {
        when(vendedorService.update(1L, vendedor)).thenReturn(vendedor);

        ResponseEntity<Vendedor> response = vendedorController.actualizar(1L, vendedor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendedor, response.getBody());
        verify(vendedorService).update(1L, vendedor);
    }

    @Test
    @DisplayName("Debe borrar vendedor y retornar 204")
    void borrar_deberiaRetornarNoContent() {
        doNothing().when(vendedorService).delete(1L);

        ResponseEntity<Void> response = vendedorController.borrar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(vendedorService).delete(1L);
    }

    @Test
    @DisplayName("Debe buscar vendedor por email y retornar 200 cuando existe")
    void buscarPorEmail_deberiaRetornarOk() {
        when(vendedorService.findByEmail("pedro@example.com")).thenReturn(vendedorDTO);

        ResponseEntity<VendedorDTO> response = vendedorController.buscarPorEmail("pedro@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pedro@example.com", response.getBody().getEmail());
        verify(vendedorService).findByEmail("pedro@example.com");
    }

    @Test
    @DisplayName("Debe actualizar estado del vendedor y retornar 200")
    void actualizarEstado_deberiaRetornarOk() {
        when(vendedorService.updateEstado(1L, EstadoVendedor.INACTIVO)).thenReturn(vendedor);

        ResponseEntity<Vendedor> response = vendedorController.actualizarEstado(1L, EstadoVendedor.INACTIVO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendedor, response.getBody());
        verify(vendedorService).updateEstado(1L, EstadoVendedor.INACTIVO);
    }
}
