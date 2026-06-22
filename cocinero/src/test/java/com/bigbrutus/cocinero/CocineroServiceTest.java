package com.bigbrutus.cocinero;

import com.bigbrutus.cocinero.dto.CocineroDTO;
import com.bigbrutus.cocinero.exception.CocineroNotFoundException;
import com.bigbrutus.cocinero.exception.EspecialidadNotValidException;
import com.bigbrutus.cocinero.mapper.CocineroMapper;
import com.bigbrutus.cocinero.model.Cocinero;
import com.bigbrutus.cocinero.model.EstadoCocinero;
import com.bigbrutus.cocinero.repository.CocineroRepository;
import com.bigbrutus.cocinero.service.CocineroService;
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
@DisplayName("Pruebas unitarias para CocineroService")
public class CocineroServiceTest {

    @Mock
    private CocineroRepository cocineroRepository;

    @Mock
    private CocineroMapper cocineroMapper;

    @InjectMocks
    private CocineroService cocineroService;

    private Cocinero cocinero;
    private CocineroDTO cocineroDTO;

    @BeforeEach
    void setUp() {
        cocinero = new Cocinero(1L,
                "Ana",
                "Martinez",
                "Pizzas",
                "987654321",
                EstadoCocinero.ACTIVO,
                LocalDate.now(),
                1L);

        cocineroDTO = new CocineroDTO();
        cocineroDTO.setIdCocinero(1L);
        cocineroDTO.setNombreCompleto("Ana Martinez");
        cocineroDTO.setEspecialidad("Pizzas");
        cocineroDTO.setTelefono("987654321");
        cocineroDTO.setEstado(EstadoCocinero.ACTIVO);
        cocineroDTO.setFechaContratacion(cocinero.getFechaContratacion());
        cocineroDTO.setIdSucursal(1L);
    }

    @Test
    @DisplayName("Debe listar cocineros como DTO")
    void findDTOList_deberiaRetornarListaDTO() {
        when(cocineroRepository.findAll()).thenReturn(List.of(cocinero));
        when(cocineroMapper.toDTOList(List.of(cocinero))).thenReturn(List.of(cocineroDTO));

        List<CocineroDTO> resultado = cocineroService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ana Martinez", resultado.get(0).getNombreCompleto());
        verify(cocineroRepository).findAll();
        verify(cocineroMapper).toDTOList(List.of(cocinero));
    }

    @Test
    @DisplayName("Debe buscar cocinero por ID cuando existe")
    void findById_deberiaRetornarCocinero() {
        when(cocineroRepository.findById(1L)).thenReturn(Optional.of(cocinero));

        Cocinero resultado = cocineroService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCocinero());
        verify(cocineroRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar CocineroNotFoundException cuando busca ID inexistente")
    void findById_cuandoNoExiste_deberiaLanzarException() {
        when(cocineroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CocineroNotFoundException.class, () -> cocineroService.findById(99L));
        verify(cocineroRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar cocinero con especialidad valida")
    void save_deberiaGuardarCocineroValido() {
        when(cocineroRepository.save(cocinero)).thenReturn(cocinero);

        Cocinero resultado = cocineroService.save(cocinero);

        assertNotNull(resultado);
        assertEquals("Pizzas", resultado.getEspecialidad());
        verify(cocineroRepository).save(cocinero);
    }

    @Test
    @DisplayName("Debe lanzar excepcion si la especialidad no es valida")
    void save_cuandoEspecialidadNoValida_deberiaLanzarException() {
        Cocinero invalid = new Cocinero(2L,
                "Luis",
                "Lopez",
                "Ensaladas",
                "111222333",
                EstadoCocinero.ACTIVO,
                LocalDate.now(),
                2L);

        assertThrows(EspecialidadNotValidException.class, () -> cocineroService.save(invalid));
        verify(cocineroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar cocinero existente")
    void delete_deberiaEliminarCocinero() {
        when(cocineroRepository.existsById(1L)).thenReturn(true);

        cocineroService.delete(1L);

        verify(cocineroRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar cocinero inexistente")
    void delete_cuandoNoExiste_deberiaLanzarException() {
        when(cocineroRepository.existsById(99L)).thenReturn(false);

        assertThrows(CocineroNotFoundException.class, () -> cocineroService.delete(99L));
        verify(cocineroRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar estado del cocinero")
    void updateEstado_deberiaActualizarEstado() {
        when(cocineroRepository.findById(1L)).thenReturn(Optional.of(cocinero));
        when(cocineroRepository.save(cocinero)).thenReturn(cocinero);

        Cocinero resultado = cocineroService.updateEstado(1L, EstadoCocinero.INACTIVO);

        assertNotNull(resultado);
        assertEquals(EstadoCocinero.INACTIVO, resultado.getEstado());
        verify(cocineroRepository).save(cocinero);
    }
}
