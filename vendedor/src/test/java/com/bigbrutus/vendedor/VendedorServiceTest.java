package com.bigbrutus.vendedor;

import com.bigbrutus.vendedor.dto.VendedorDTO;
import com.bigbrutus.vendedor.exception.VendedorNotFoundException;
import com.bigbrutus.vendedor.mapper.VendedorMapper;
import com.bigbrutus.vendedor.model.EstadoVendedor;
import com.bigbrutus.vendedor.model.Vendedor;
import com.bigbrutus.vendedor.repository.VendedorRepository;
import com.bigbrutus.vendedor.service.VendedorService;
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
@DisplayName("Pruebas unitarias para VendedorService")
public class VendedorServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private VendedorMapper vendedorMapper;

    @InjectMocks
    private VendedorService vendedorService;

    private Vendedor vendedor;
    private VendedorDTO vendedorDTO;

    @BeforeEach
    void setUp() {
        vendedor = new Vendedor(1L,
                "Pedro",
                "Gomez",
                "123456789",
                "pedro@example.com",
                LocalDate.now(),
                BigDecimal.valueOf(1200),
                EstadoVendedor.ACTIVO,
                1L);

        vendedorDTO = new VendedorDTO();
        vendedorDTO.setIdVendedor(1L);
        vendedorDTO.setNombreCompleto("Pedro Gomez");
        vendedorDTO.setTelefono("123456789");
        vendedorDTO.setEmail("pedro@example.com");
        vendedorDTO.setFechaContratacion(vendedor.getFechaContratacion());
        vendedorDTO.setSalario(BigDecimal.valueOf(1200));
        vendedorDTO.setEstado("ACTIVO");
        vendedorDTO.setIdSucursal(1L);
    }

    @Test
    @DisplayName("Debe listar vendedores como DTO")
    void findDTOList_deberiaRetornarListaDTO() {
        when(vendedorRepository.findAll()).thenReturn(List.of(vendedor));
        when(vendedorMapper.toDTOList(List.of(vendedor))).thenReturn(List.of(vendedorDTO));

        List<VendedorDTO> resultado = vendedorService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedro Gomez", resultado.get(0).getNombreCompleto());
        verify(vendedorRepository).findAll();
        verify(vendedorMapper).toDTOList(List.of(vendedor));
    }

    @Test
    @DisplayName("Debe buscar vendedor por ID cuando existe")
    void findById_deberiaRetornarVendedor() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));

        Vendedor resultado = vendedorService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdVendedor());
        verify(vendedorRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando busca vendedor inexistente")
    void findById_cuandoNoExiste_deberiaLanzarException() {
        when(vendedorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(VendedorNotFoundException.class, () -> vendedorService.findById(99L));
        verify(vendedorRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar vendedor")
    void save_deberiaGuardarVendedor() {
        when(vendedorRepository.save(vendedor)).thenReturn(vendedor);

        Vendedor resultado = vendedorService.save(vendedor);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombre());
        verify(vendedorRepository).save(vendedor);
    }

    @Test
    @DisplayName("Debe eliminar vendedor existente")
    void delete_deberiaEliminarVendedor() {
        when(vendedorRepository.existsById(1L)).thenReturn(true);

        vendedorService.delete(1L);

        verify(vendedorRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar vendedor inexistente")
    void delete_cuandoNoExiste_deberiaLanzarException() {
        when(vendedorRepository.existsById(99L)).thenReturn(false);

        assertThrows(VendedorNotFoundException.class, () -> vendedorService.delete(99L));
        verify(vendedorRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe buscar vendedor por email y retornar DTO cuando existe")
    void findByEmail_deberiaRetornarDTO() {
        when(vendedorRepository.findByEmail("pedro@example.com")).thenReturn(vendedor);
        when(vendedorMapper.toDTO(vendedor)).thenReturn(vendedorDTO);

        VendedorDTO resultado = vendedorService.findByEmail("pedro@example.com");

        assertNotNull(resultado);
        assertEquals("pedro@example.com", resultado.getEmail());
        verify(vendedorRepository).findByEmail("pedro@example.com");
        verify(vendedorMapper).toDTO(vendedor);
    }

    @Test
    @DisplayName("Debe actualizar estado del vendedor")
    void updateEstado_deberiaActualizarEstado() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendedorRepository.save(vendedor)).thenReturn(vendedor);

        Vendedor resultado = vendedorService.updateEstado(1L, EstadoVendedor.INACTIVO);

        assertNotNull(resultado);
        assertEquals(EstadoVendedor.INACTIVO, resultado.getEstado());
        verify(vendedorRepository).save(vendedor);
    }
}
