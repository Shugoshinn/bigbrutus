package com.bigbrutus.repartidores;

import com.bigbrutus.repartidores.clients.VehiculoFeign;
import com.bigbrutus.repartidores.dto.RepartidorDTO;
import com.bigbrutus.repartidores.dto.VehiculoDTO;
import com.bigbrutus.repartidores.exception.BadNameException;
import com.bigbrutus.repartidores.exception.NotFoundException;
import com.bigbrutus.repartidores.mapper.RepartidorMapper;
import com.bigbrutus.repartidores.model.EstadoRepartidor;
import com.bigbrutus.repartidores.model.EstadoVehiculo;
import com.bigbrutus.repartidores.model.Repartidor;
import com.bigbrutus.repartidores.model.TipoVehiculo;
import com.bigbrutus.repartidores.repository.RepartidorRepository;
import com.bigbrutus.repartidores.service.RepartidorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para RepartidorService")
public class RepartidorServiceTest {

    @Mock
    private RepartidorRepository repartidorRepository;

    @Mock
    private RepartidorMapper repartidorMapper;

    @Mock
    private VehiculoFeign vehiculoFeign;

    @InjectMocks
    private RepartidorService repartidorService;

    private Repartidor repartidor;
    private RepartidorDTO repartidorDTO;
    private VehiculoDTO vehiculoDTO;

    @BeforeEach
    public void setUp() {
        repartidor = new Repartidor(
                1L,
                "Micha",
                "Fuentes",
                912345678,
                "Clase B",
                EstadoRepartidor.DISPONIBLE,
                1L
        );

        repartidorDTO = new RepartidorDTO();
        repartidorDTO.setId(1L);
        repartidorDTO.setNombreCompleto("Micha Fuentes");
        repartidorDTO.setTelefono(912345678);
        repartidorDTO.setEstado(EstadoRepartidor.DISPONIBLE);

        vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setIdVehiculo(1L);
        vehiculoDTO.setPatente("ABCD12");
        vehiculoDTO.setInfoVehiculo("MOTO Yamaha NMAX");
        vehiculoDTO.setEstado(EstadoVehiculo.DISPONIBLE);
    }

    @Test
    @DisplayName("Debe listar todos los repartidores correctamente")
    public void findAll_deberiaRetornarListaDeRepartidores() {
        when(repartidorRepository.findAll()).thenReturn(List.of(repartidor));

        List<Repartidor> resultado = repartidorService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Micha", resultado.get(0).getNombre());
        verify(repartidorRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un repartidor por ID cuando existe")
    public void findByID_cuandoExiste_deberiaRetornarRepartidor() {
        when(repartidorRepository.findById(1L)).thenReturn(Optional.of(repartidor));

        Repartidor resultado = repartidorService.findByID(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdRepartidor());
        assertEquals("Micha", resultado.getNombre());
        verify(repartidorRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException cuando el repartidor no existe")
    public void findByID_cuandoNoExiste_deberiaLanzarException() {
        when(repartidorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repartidorService.findByID(99L));
        verify(repartidorRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un repartidor correctamente")
    public void save_deberiaGuardarYRetornarRepartidor() {
        when(repartidorRepository.save(repartidor)).thenReturn(repartidor);

        Repartidor resultado = repartidorService.save(repartidor);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdRepartidor());
        assertEquals("Micha", resultado.getNombre());
        verify(repartidorRepository).save(repartidor);
    }

    @Test
    @DisplayName("Debe lanzar BadNameException al guardar un repartidor con nombre inválido")
    public void save_conNombreInvalido_deberiaLanzarException() {
        repartidor.setNombre("Mi");

        assertThrows(BadNameException.class, () -> repartidorService.save(repartidor));
        verify(repartidorRepository, never()).save(any(Repartidor.class));
    }

    @Test
    @DisplayName("Debe validar existencia al eliminar un repartidor")
    public void deleteById_cuandoExiste_deberiaValidarExistencia() {
        when(repartidorRepository.existsById(1L)).thenReturn(true);

        repartidorService.deleteById(1L);

        verify(repartidorRepository).existsById(1L);
        verify(repartidorRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al eliminar un repartidor inexistente")
    public void deleteById_cuandoNoExiste_deberiaLanzarException() {
        when(repartidorRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> repartidorService.deleteById(99L));
        verify(repartidorRepository).existsById(99L);
        verify(repartidorRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar un repartidor existente")
    public void update_cuandoExiste_deberiaActualizarYRetornarRepartidor() {
        Repartidor repartidorActualizado = new Repartidor();
        repartidorActualizado.setNombre("Juan");
        repartidorActualizado.setApellido("Perez");
        repartidorActualizado.setTelefono(987654321);
        repartidorActualizado.setLicencia("Clase C");
        repartidorActualizado.setEstado(EstadoRepartidor.EN_REPARTO);

        when(repartidorRepository.findById(1L)).thenReturn(Optional.of(repartidor));
        when(repartidorRepository.save(any(Repartidor.class))).thenReturn(repartidor);

        Repartidor resultado = repartidorService.update(1L, repartidorActualizado);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals(EstadoRepartidor.EN_REPARTO, resultado.getEstado());
        verify(repartidorRepository).findById(1L);
        verify(repartidorRepository).save(repartidor);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al actualizar un repartidor inexistente")
    public void update_cuandoNoExiste_deberiaLanzarException() {
        when(repartidorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repartidorService.update(99L, repartidor));
        verify(repartidorRepository).findById(99L);
        verify(repartidorRepository, never()).save(any(Repartidor.class));
    }

    @Test
    @DisplayName("Debe retornar la lista detallada con información del vehículo")
    public void listaDetallada_deberiaRetornarListaDTOConVehiculo() {
        when(repartidorRepository.findAll()).thenReturn(List.of(repartidor));
        when(repartidorMapper.toDTO(repartidor)).thenReturn(repartidorDTO);
        when(vehiculoFeign.buscarPorID(1L)).thenReturn(vehiculoDTO);

        List<RepartidorDTO> resultado = repartidorService.listaDetallada();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("MOTO Yamaha NMAX", resultado.get(0).getVehiculo());
        verify(repartidorRepository).findAll();
        verify(repartidorMapper).toDTO(repartidor);
        verify(vehiculoFeign).buscarPorID(1L);
    }

    @Test
    @DisplayName("Debe listar repartidores por tipo de vehículo")
    public void listRepPorTipoVehiculo_deberiaRetornarListaDTO() {
        when(vehiculoFeign.findAllByTipo(TipoVehiculo.MOTO)).thenReturn(List.of(vehiculoDTO));
        when(repartidorRepository.findByVehiculoIn(List.of(1L))).thenReturn(List.of(repartidor));
        when(repartidorMapper.toDTO(repartidor)).thenReturn(repartidorDTO);

        List<RepartidorDTO> resultado = repartidorService.listRepPorTipoVehiculo(TipoVehiculo.MOTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("MOTO Yamaha NMAX", resultado.get(0).getVehiculo());
        verify(vehiculoFeign).findAllByTipo(TipoVehiculo.MOTO);
        verify(repartidorRepository).findByVehiculoIn(List.of(1L));
        verify(repartidorMapper).toDTO(repartidor);
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no existen vehículos por tipo")
    public void listRepPorTipoVehiculo_cuandoNoHayVehiculos_deberiaRetornarListaVacia() {
        when(vehiculoFeign.findAllByTipo(TipoVehiculo.MOTO)).thenReturn(Collections.emptyList());

        List<RepartidorDTO> resultado = repartidorService.listRepPorTipoVehiculo(TipoVehiculo.MOTO);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(vehiculoFeign).findAllByTipo(TipoVehiculo.MOTO);
        verify(repartidorRepository, never()).findByVehiculoIn(anyList());
        verify(repartidorMapper, never()).toDTO(any(Repartidor.class));
    }

    @Test
    @DisplayName("Debe listar repartidores por estado")
    public void findAllByEstado_deberiaRetornarListaDTO() {
        when(repartidorRepository.findAllByEstado(EstadoRepartidor.DISPONIBLE)).thenReturn(List.of(repartidor));
        when(repartidorMapper.toDTO(repartidor)).thenReturn(repartidorDTO);

        List<RepartidorDTO> resultado = repartidorService.findAllByEstado(EstadoRepartidor.DISPONIBLE);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(EstadoRepartidor.DISPONIBLE, resultado.get(0).getEstado());
        verify(repartidorRepository).findAllByEstado(EstadoRepartidor.DISPONIBLE);
        verify(repartidorMapper).toDTO(repartidor);
    }
}
