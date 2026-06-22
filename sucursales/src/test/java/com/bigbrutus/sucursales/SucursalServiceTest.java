package com.bigbrutus.sucursales;

import com.bigbrutus.sucursales.dto.SucursalDTO;
import com.bigbrutus.sucursales.exception.BadNameException;
import com.bigbrutus.sucursales.exception.NotFoundException;
import com.bigbrutus.sucursales.mapper.SucursalMapper;
import com.bigbrutus.sucursales.model.Sucursal;
import com.bigbrutus.sucursales.model.TipoSucursal;
import com.bigbrutus.sucursales.repository.SucursalRepository;
import com.bigbrutus.sucursales.service.SucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para SucursalService")
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private SucursalMapper sucursalMapper;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursal;
    private SucursalDTO sucursalDTO;

    @BeforeEach
    public void setUp() {
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
    @DisplayName("Debe listar todas las sucursales correctamente")
    public void findAll_deberiaRetornarListaDeSucursales() {
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));

        List<Sucursal> resultado = sucursalService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sucursal Centro", resultado.get(0).getNombre());
        verify(sucursalRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar una sucursal por ID y retornar DTO cuando existe")
    public void findByID_cuandoExiste_deberiaRetornarDTO() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalMapper.toDTO(sucursal)).thenReturn(sucursalDTO);

        SucursalDTO resultado = sucursalService.findByID(1L);

        assertNotNull(resultado);
        assertEquals("Sucursal Centro", resultado.getNombre());
        assertEquals("Av. Siempre Viva 742, Santiago", resultado.getDireccionCompleta());
        verify(sucursalRepository).findById(1L);
        verify(sucursalMapper).toDTO(sucursal);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException cuando la sucursal no existe")
    public void findByID_cuandoNoExiste_deberiaLanzarException() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sucursalService.findByID(99L));
        verify(sucursalRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una sucursal correctamente")
    public void save_deberiaGuardarYRetornarSucursal() {
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);

        Sucursal resultado = sucursalService.save(sucursal);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSucursal());
        assertEquals("Sucursal Centro", resultado.getNombre());
        verify(sucursalRepository).save(sucursal);
    }

    @Test
    @DisplayName("Debe lanzar BadNameException al guardar una sucursal con nombre inválido")
    public void save_conNombreInvalido_deberiaLanzarException() {
        sucursal.setNombre("Su");

        assertThrows(BadNameException.class, () -> sucursalService.save(sucursal));
        verify(sucursalRepository, never()).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Debe eliminar una sucursal cuando existe")
    public void deleteById_cuandoExiste_deberiaEliminarSucursal() {
        when(sucursalRepository.existsById(1L)).thenReturn(true);

        sucursalService.deleteById(1L);

        verify(sucursalRepository).existsById(1L);
        verify(sucursalRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al eliminar una sucursal inexistente")
    public void deleteById_cuandoNoExiste_deberiaLanzarException() {
        when(sucursalRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> sucursalService.deleteById(99L));
        verify(sucursalRepository).existsById(99L);
        verify(sucursalRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe listar sucursales activas o inactivas")
    public void findAllByActiva_deberiaRetornarListaDeSucursales() {
        when(sucursalRepository.findAllByActiva(true)).thenReturn(List.of(sucursal));

        List<Sucursal> resultado = sucursalService.findAllByActiva(true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActiva());
        verify(sucursalRepository).findAllByActiva(true);
    }

    @Test
    @DisplayName("Debe listar sucursales por comuna")
    public void findAllByComuna_deberiaRetornarListaDeSucursales() {
        when(sucursalRepository.findAllByComuna("Santiago")).thenReturn(List.of(sucursal));

        List<Sucursal> resultado = sucursalService.findAllByComuna("Santiago");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Santiago", resultado.get(0).getComuna());
        verify(sucursalRepository).findAllByComuna("Santiago");
    }

    @Test
    @DisplayName("Debe listar sucursales por tipo")
    public void findAllByTipo_deberiaRetornarListaDeSucursales() {
        when(sucursalRepository.findAllByTipo(TipoSucursal.PARA_SERVIR)).thenReturn(List.of(sucursal));

        List<Sucursal> resultado = sucursalService.findAllByTipo(TipoSucursal.PARA_SERVIR);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(TipoSucursal.PARA_SERVIR, resultado.get(0).getTipo());
        verify(sucursalRepository).findAllByTipo(TipoSucursal.PARA_SERVIR);
    }
}
