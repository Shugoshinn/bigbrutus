package com.bigbrutus.vehiculos;

import com.bigbrutus.vehiculos.dto.VehiculoDTO;
import com.bigbrutus.vehiculos.exception.AnioNoValidoException;
import com.bigbrutus.vehiculos.exception.NotFoundException;
import com.bigbrutus.vehiculos.mapper.VehiculoMapper;
import com.bigbrutus.vehiculos.model.EstadoVehiculo;
import com.bigbrutus.vehiculos.model.TipoVehiculo;
import com.bigbrutus.vehiculos.model.Vehiculo;
import com.bigbrutus.vehiculos.repository.VehiculoRepository;
import com.bigbrutus.vehiculos.service.VehiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para VehiculoService")
public class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;
    private VehiculoDTO vehiculoDTO;

    @BeforeEach
    public void setUp() {
        vehiculo = new Vehiculo(
                1L,
                "ABCD12",
                "Yamaha",
                "NMAX",
                2024,
                TipoVehiculo.MOTO,
                EstadoVehiculo.DISPONIBLE
        );

        vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setIdVehiculo(1L);
        vehiculoDTO.setPatente("ABCD12");
        vehiculoDTO.setInfoVehiculo("MOTO Yamaha NMAX");
        vehiculoDTO.setEstado(EstadoVehiculo.DISPONIBLE);
    }

    @Test
    @DisplayName("Debe listar todos los vehículos correctamente")
    public void findAll_deberiaRetornarListaDeVehiculos() {
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));

        List<Vehiculo> resultado = vehiculoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ABCD12", resultado.get(0).getPatente());
        verify(vehiculoRepository).findAll();
    }

    @Test
    @DisplayName("Debe listar todos los vehículos en formato DTO")
    public void findAllDTO_deberiaRetornarListaDTO() {
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        List<VehiculoDTO> resultado = vehiculoService.findAllDTO();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("MOTO Yamaha NMAX", resultado.get(0).getInfoVehiculo());
        verify(vehiculoRepository).findAll();
        verify(vehiculoMapper).toDTO(vehiculo);
    }

    @Test
    @DisplayName("Debe buscar un vehículo por ID y retornar DTO cuando existe")
    public void findByID_cuandoExiste_deberiaRetornarDTO() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        VehiculoDTO resultado = vehiculoService.findByID(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdVehiculo());
        assertEquals("ABCD12", resultado.getPatente());
        verify(vehiculoRepository).findById(1L);
        verify(vehiculoMapper).toDTO(vehiculo);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException cuando el vehículo no existe")
    public void findByID_cuandoNoExiste_deberiaLanzarException() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vehiculoService.findByID(99L));
        verify(vehiculoRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un vehículo correctamente")
    public void save_deberiaGuardarYRetornarVehiculo() {
        when(vehiculoRepository.save(vehiculo)).thenReturn(vehiculo);

        Vehiculo resultado = vehiculoService.save(vehiculo);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdVehiculo());
        assertEquals("ABCD12", resultado.getPatente());
        verify(vehiculoRepository).save(vehiculo);
    }

    @Test
    @DisplayName("Debe lanzar AnioNoValidoException al guardar un vehículo con año inválido")
    public void save_conAnioInvalido_deberiaLanzarException() {
        vehiculo.setAnio(Year.now().getValue() + 1);

        assertThrows(AnioNoValidoException.class, () -> vehiculoService.save(vehiculo));
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debe eliminar un vehículo cuando existe")
    public void deleteById_cuandoExiste_deberiaEliminarVehiculo() {
        when(vehiculoRepository.existsById(1L)).thenReturn(true);

        vehiculoService.deleteById(1L);

        verify(vehiculoRepository).existsById(1L);
        verify(vehiculoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al eliminar un vehículo inexistente")
    public void deleteById_cuandoNoExiste_deberiaLanzarException() {
        when(vehiculoRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> vehiculoService.deleteById(99L));
        verify(vehiculoRepository).existsById(99L);
        verify(vehiculoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Debe actualizar un vehículo existente")
    public void update_cuandoExiste_deberiaActualizarYRetornarVehiculo() {
        Vehiculo vehiculoActualizado = new Vehiculo();
        vehiculoActualizado.setPatente("WXYZ99");
        vehiculoActualizado.setMarca("Honda");
        vehiculoActualizado.setModelo("PCX");
        vehiculoActualizado.setAnio(2025);
        vehiculoActualizado.setTipo(TipoVehiculo.SCOOTER);
        vehiculoActualizado.setEstado(EstadoVehiculo.EN_REPARTO);

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo resultado = vehiculoService.update(1L, vehiculoActualizado);

        assertNotNull(resultado);
        assertEquals("WXYZ99", resultado.getPatente());
        assertEquals("Honda", resultado.getMarca());
        assertEquals(TipoVehiculo.SCOOTER, resultado.getTipo());
        assertEquals(EstadoVehiculo.EN_REPARTO, resultado.getEstado());
        verify(vehiculoRepository).findById(1L);
        verify(vehiculoRepository).save(vehiculo);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException al actualizar un vehículo inexistente")
    public void update_cuandoNoExiste_deberiaLanzarException() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vehiculoService.update(99L, vehiculo));
        verify(vehiculoRepository).findById(99L);
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debe buscar un vehículo por patente")
    public void findByPatente_cuandoExiste_deberiaRetornarDTO() {
        when(vehiculoRepository.findByPatente("ABCD12")).thenReturn(Optional.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        VehiculoDTO resultado = vehiculoService.findByPatente("ABCD12");

        assertNotNull(resultado);
        assertEquals("ABCD12", resultado.getPatente());
        verify(vehiculoRepository).findByPatente("ABCD12");
        verify(vehiculoMapper).toDTO(vehiculo);
    }

    @Test
    @DisplayName("Debe listar vehículos por tipo")
    public void findAllByTipo_deberiaRetornarListaDeVehiculos() {
        when(vehiculoRepository.findAllByTipo(TipoVehiculo.MOTO)).thenReturn(List.of(vehiculo));

        List<Vehiculo> resultado = vehiculoService.findAllByTipo(TipoVehiculo.MOTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(TipoVehiculo.MOTO, resultado.get(0).getTipo());
        verify(vehiculoRepository).findAllByTipo(TipoVehiculo.MOTO);
    }

    @Test
    @DisplayName("Debe actualizar solo el estado de un vehículo")
    public void updateEstado_cuandoExiste_deberiaActualizarEstado() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo resultado = vehiculoService.updateEstado(1L, EstadoVehiculo.EN_REPARTO);

        assertNotNull(resultado);
        assertEquals(EstadoVehiculo.EN_REPARTO, resultado.getEstado());
        verify(vehiculoRepository).findById(1L);
        verify(vehiculoRepository).save(vehiculo);
    }

    @Test
    @DisplayName("Debe listar vehículos por estado en formato DTO")
    public void findAllByEstado_deberiaRetornarListaDTO() {
        when(vehiculoRepository.findAllByEstado(EstadoVehiculo.DISPONIBLE)).thenReturn(List.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        List<VehiculoDTO> resultado = vehiculoService.findAllByEstado(EstadoVehiculo.DISPONIBLE);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(EstadoVehiculo.DISPONIBLE, resultado.get(0).getEstado());
        verify(vehiculoRepository).findAllByEstado(EstadoVehiculo.DISPONIBLE);
        verify(vehiculoMapper).toDTO(vehiculo);
    }

    @Test
    @DisplayName("Debe listar vehículos por tipo en formato DTO")
    public void findAllByTipoDTO_deberiaRetornarListaDTO() {
        when(vehiculoRepository.findAllByTipo(TipoVehiculo.MOTO)).thenReturn(List.of(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        List<VehiculoDTO> resultado = vehiculoService.findAllByTipoDTO(TipoVehiculo.MOTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("MOTO Yamaha NMAX", resultado.get(0).getInfoVehiculo());
        verify(vehiculoRepository).findAllByTipo(TipoVehiculo.MOTO);
        verify(vehiculoMapper).toDTO(vehiculo);
    }
}
