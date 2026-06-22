package com.bigbrutus.cocinero;

import com.bigbrutus.cocinero.controller.CocineroController;
import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.model.Cocinero;
import com.bigbrutus.cocinero.model.EstadoCocinero;
import com.bigbrutus.cocinero.service.CocineroService;
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
@DisplayName("Pruebas unitarias para CocineroController")
public class CocineroControllerTest {

    @Mock
    private CocineroService cocineroService;

    @InjectMocks
    private CocineroController cocineroController;

    private CocineroDTO cocineroDTO;
    private Cocinero cocinero;

    @BeforeEach
    void setUp() {
        cocineroDTO = new CocineroDTO();
        cocineroDTO.setIdCocinero(1L);
        cocineroDTO.setNombreCompleto("Ana Martinez");
        cocineroDTO.setEspecialidad("Pizzas");
        cocineroDTO.setTelefono("987654321");
        cocineroDTO.setEstado(EstadoCocinero.ACTIVO);
        cocineroDTO.setFechaContratacion(LocalDate.now());
        cocineroDTO.setIdSucursal(1L);

        cocinero = new Cocinero(1L,
                "Ana",
                "Martinez",
                "Pizzas",
                "987654321",
                EstadoCocinero.ACTIVO,
                LocalDate.now(),
                1L);
    }

    @Test
    @DisplayName("Debe listar cocineros y retornar 200")
    void listar_deberiaRetornarLista() {
        when(cocineroService.findDTOList()).thenReturn(List.of(cocineroDTO));

        ResponseEntity<List<CocineroDTO>> response = cocineroController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(cocineroService).findDTOList();
    }

    @Test
    @DisplayName("Debe buscar cocinero por ID y retornar 200 cuando existe")
    void buscarPorId_deberiaRetornarOk() {
        when(cocineroService.findDTO(1L)).thenReturn(cocineroDTO);

        ResponseEntity<CocineroDTO> response = cocineroController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ana Martinez", response.getBody().getNombreCompleto());
        verify(cocineroService).findDTO(1L);
    }

    @Test
    @DisplayName("Debe retornar 404 cuando busca un cocinero inexistente")
    void buscarPorId_cuandoNoExiste_deberiaRetornarNotFound() {
        when(cocineroService.findDTO(99L)).thenReturn(null);

        ResponseEntity<CocineroDTO> response = cocineroController.buscarPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(cocineroService).findDTO(99L);
    }

    @Test
    @DisplayName("Debe registrar cocinero y retornar 201")
    void registrar_deberiaRetornarCreated() {
        when(cocineroService.save(cocinero)).thenReturn(cocinero);

        ResponseEntity<Cocinero> response = cocineroController.registrar(cocinero);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cocinero, response.getBody());
        verify(cocineroService).save(cocinero);
    }

    @Test
    @DisplayName("Debe actualizar cocinero y retornar 200")
    void actualizar_deberiaRetornarOk() {
        when(cocineroService.update(1L, cocinero)).thenReturn(cocinero);

        ResponseEntity<Cocinero> response = cocineroController.actualizar(1L, cocinero);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cocinero, response.getBody());
        verify(cocineroService).update(1L, cocinero);
    }

    @Test
    @DisplayName("Debe borrar cocinero y retornar 204")
    void borrar_deberiaRetornarNoContent() {
        doNothing().when(cocineroService).delete(1L);

        ResponseEntity<Void> response = cocineroController.borrar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cocineroService).delete(1L);
    }

    @Test
    @DisplayName("Debe buscar por especialidad y retornar 200")
    void buscarPorEspecialidad_deberiaRetornarLista() {
        when(cocineroService.findBySpecialty("Pizzas")).thenReturn(List.of(cocineroDTO));

        ResponseEntity<List<CocineroDTO>> response = cocineroController.buscarPorEspecialidad("Pizzas");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(cocineroService).findBySpecialty("Pizzas");
    }

    @Test
    @DisplayName("Debe actualizar estado de cocinero y retornar 200 cuando existe")
    void actualizarEstado_deberiaRetornarOk() {
        when(cocineroService.updateEstado(1L, EstadoCocinero.INACTIVO)).thenReturn(cocinero);

        ResponseEntity<Cocinero> response = cocineroController.actualizarEstado(1L, "INACTIVO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cocinero, response.getBody());
        verify(cocineroService).updateEstado(1L, EstadoCocinero.INACTIVO);
    }
}
