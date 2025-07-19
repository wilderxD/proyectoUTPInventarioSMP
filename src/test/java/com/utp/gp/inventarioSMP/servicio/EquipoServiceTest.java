package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.EquipoDao;
import com.utp.gp.inventarioSMP.entidades.Categoria;
import com.utp.gp.inventarioSMP.entidades.Equipo;
import com.utp.gp.inventarioSMP.entidades.Usuario_asignado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para el servicio EquipoService")
class EquipoServiceTest {

    @Mock
    private EquipoDao equipoDao;

    @InjectMocks
    private EquipoService equipoService;

    private Equipo equipo;
    private Categoria categoria;
    private Usuario_asignado usuarioAsignado;
    private List<Equipo> equipos;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setCategoria_nombre("COMPUTADOR");
        categoria.setStatus("ACTIVO");

        usuarioAsignado = new Usuario_asignado();
        usuarioAsignado.setId(1L);
        usuarioAsignado.setCodigo("12345678");
        usuarioAsignado.setNombre("Juan Pérez");

        equipo = new Equipo();
        equipo.setId(1L);
        equipo.setEquipo_codigo("EQ001");
        equipo.setEquipo_descripcion("Laptop Dell Inspiron");
        equipo.setValor(1500.0);
        equipo.setTipoMoneda("DOLARES");
        equipo.setObservacion("Equipo nuevo");
        equipo.setCategoria(categoria);
        equipo.setEstado("ACTIVO");
        equipo.setAsignado(usuarioAsignado);

        equipos = new ArrayList<>();
        equipos.add(equipo);
    }

    @Nested
    @DisplayName("Tests de findAll")
    class FindAllTests {

        @Test
        @DisplayName("findAll debe retornar lista de equipos")
        void findAll_DeberiaRetornarListaDeEquipos() {
            // Arrange
            when(equipoDao.findAll()).thenReturn(equipos);

            // Act
            List<Equipo> resultado = equipoService.findAll();

            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(equipo, resultado.get(0));
            verify(equipoDao, times(1)).findAll();
        }

        @Test
        @DisplayName("findAll debe retornar lista vacía cuando no hay equipos")
        void findAll_SinEquipos_DeberiaRetornarListaVacia() {
            // Arrange
            when(equipoDao.findAll()).thenReturn(new ArrayList<>());

            // Act
            List<Equipo> resultado = equipoService.findAll();

            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(equipoDao, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Tests de findAll con paginación")
    class FindAllPagedTests {

        @Test
        @DisplayName("findAll con Pageable debe retornar página de equipos")
        void findAllConPageable_DeberiaRetornarPaginaDeEquipos() {
            // Arrange
            Pageable pageable = PageRequest.of(0, 10);
            Page<Equipo> page = new PageImpl<>(equipos, pageable, 1);
            when(equipoDao.findAll(pageable)).thenReturn(page);

            // Act
            Page<Equipo> resultado = equipoService.findAll(pageable);

            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.getTotalElements());
            assertEquals(equipo, resultado.getContent().get(0));
            verify(equipoDao, times(1)).findAll(pageable);
        }
    }

    @Nested
    @DisplayName("Tests de save")
    class SaveTests {

        @Test
        @DisplayName("save debe guardar equipo correctamente")
        void save_DeberiaGuardarEquipo() {
            // Arrange
            when(equipoDao.save(any(Equipo.class))).thenReturn(equipo);

            // Act
            equipoService.save(equipo);

            // Assert
            verify(equipoDao, times(1)).save(equipo);
        }

        @Test
        @DisplayName("save debe manejar equipo null")
        void save_ConEquipoNull_DeberiaManejarCorrectamente() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                equipoService.save(null);
            });
        }
    }

    @Nested
    @DisplayName("Tests de findOne")
    class FindOneTests {

        @Test
        @DisplayName("findOne debe retornar equipo cuando existe")
        void findOne_ConIdExistente_DeberiaRetornarEquipo() {
            // Arrange
            when(equipoDao.findById(1L)).thenReturn(Optional.of(equipo));

            // Act
            Equipo resultado = equipoService.findOne(1L);

            // Assert
            assertNotNull(resultado);
            assertEquals(equipo, resultado);
            verify(equipoDao, times(1)).findById(1L);
        }

        @Test
        @DisplayName("findOne debe retornar null cuando no existe")
        void findOne_ConIdInexistente_DeberiaRetornarNull() {
            // Arrange
            when(equipoDao.findById(999L)).thenReturn(Optional.empty());

            // Act
            Equipo resultado = equipoService.findOne(999L);

            // Assert
            assertNull(resultado);
            verify(equipoDao, times(1)).findById(999L);
        }

        @Test
        @DisplayName("findOne debe manejar id null")
        void findOne_ConIdNull_DeberiaRetornarNull() {
            // Act
            Equipo resultado = equipoService.findOne(null);

            // Assert
            assertNull(resultado);
            verify(equipoDao, never()).findById(any());
        }
    }

    @Nested
    @DisplayName("Tests de delete")
    class DeleteTests {

        @Test
        @DisplayName("delete debe eliminar equipo correctamente")
        void delete_DeberiaEliminarEquipo() {
            // Arrange
            doNothing().when(equipoDao).deleteById(1L);

            // Act
            equipoService.delete(1L);

            // Assert
            verify(equipoDao, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("delete debe manejar id null")
        void delete_ConIdNull_DeberiaManejarCorrectamente() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                equipoService.delete(null);
            });
        }
    }

    @Nested
    @DisplayName("Tests de actualizarEquipo")
    class ActualizarEquipoTests {

        @Test
        @DisplayName("actualizarEquipo debe lanzar UnsupportedOperationException")
        void actualizarEquipo_DeberiaLanzarExcepcion() {
            // Act & Assert
            assertThrows(UnsupportedOperationException.class, () -> {
                equipoService.actualizarEquipo(equipo);
            });
        }
    }

    @Nested
    @DisplayName("Tests de equiposAsignados")
    class EquiposAsignadosTests {

        @Test
        @DisplayName("equiposAsignados debe retornar equipos asignados")
        void equiposAsignados_ConEquiposAsignados_DeberiaRetornarEquiposAsignados() {
            // Arrange
            Equipo equipoNoAsignado = new Equipo();
            equipoNoAsignado.setId(2L);
            equipoNoAsignado.setAsignado(null);

            List<Equipo> todosLosEquipos = new ArrayList<>();
            todosLosEquipos.add(equipo); // Asignado
            todosLosEquipos.add(equipoNoAsignado); // No asignado

            when(equipoDao.findAll()).thenReturn(todosLosEquipos);

            // Act
            List<Equipo> resultado = equipoService.equiposAsignados();

            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(equipo, resultado.get(0));
            verify(equipoDao, times(1)).findAll();
        }

        @Test
        @DisplayName("equiposAsignados debe retornar lista vacía cuando no hay equipos asignados")
        void equiposAsignados_SinEquiposAsignados_DeberiaRetornarListaVacia() {
            // Arrange
            Equipo equipoNoAsignado1 = new Equipo();
            equipoNoAsignado1.setId(1L);
            equipoNoAsignado1.setAsignado(null);

            Equipo equipoNoAsignado2 = new Equipo();
            equipoNoAsignado2.setId(2L);
            equipoNoAsignado2.setAsignado(null);

            List<Equipo> equiposNoAsignados = new ArrayList<>();
            equiposNoAsignados.add(equipoNoAsignado1);
            equiposNoAsignados.add(equipoNoAsignado2);

            when(equipoDao.findAll()).thenReturn(equiposNoAsignados);

            // Act
            List<Equipo> resultado = equipoService.equiposAsignados();

            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(equipoDao, times(1)).findAll();
        }

        @Test
        @DisplayName("equiposAsignados debe manejar asignado con toString vacío")
        void equiposAsignados_ConAsignadoToStringVacio_DeberiaExcluirEquipo() {
            // Arrange
            Usuario_asignado asignadoVacio = new Usuario_asignado();
            asignadoVacio.setId(1L);
            asignadoVacio.setCodigo("");
            asignadoVacio.setNombre("");

            Equipo equipoConAsignadoVacio = new Equipo();
            equipoConAsignadoVacio.setId(2L);
            equipoConAsignadoVacio.setAsignado(asignadoVacio);

            List<Equipo> todosLosEquipos = new ArrayList<>();
            todosLosEquipos.add(equipo); // Asignado válido
            todosLosEquipos.add(equipoConAsignadoVacio); // Asignado con toString vacío

            when(equipoDao.findAll()).thenReturn(todosLosEquipos);

            // Act
            List<Equipo> resultado = equipoService.equiposAsignados();

            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(equipo, resultado.get(0));
            verify(equipoDao, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Tests de equiposNoAsignados")
    class EquiposNoAsignadosTests {

        @Test
        @DisplayName("equiposNoAsignados debe retornar equipos no asignados")
        void equiposNoAsignados_ConEquiposNoAsignados_DeberiaRetornarEquiposNoAsignados() {
            // Arrange
            Equipo equipoNoAsignado = new Equipo();
            equipoNoAsignado.setId(2L);
            equipoNoAsignado.setAsignado(null);

            List<Equipo> todosLosEquipos = new ArrayList<>();
            todosLosEquipos.add(equipo); // Asignado
            todosLosEquipos.add(equipoNoAsignado); // No asignado

            when(equipoDao.findAll()).thenReturn(todosLosEquipos);

            // Act
            List<Equipo> resultado = equipoService.equiposNoAsignados();

            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(equipoNoAsignado, resultado.get(0));
            verify(equipoDao, times(1)).findAll();
        }

        @Test
        @DisplayName("equiposNoAsignados debe retornar lista vacía cuando todos están asignados")
        void equiposNoAsignados_TodosAsignados_DeberiaRetornarListaVacia() {
            // Arrange
            when(equipoDao.findAll()).thenReturn(equipos);

            // Act
            List<Equipo> resultado = equipoService.equiposNoAsignados();

            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(equipoDao, times(1)).findAll();
        }

        @Test
        @DisplayName("equiposNoAsignados debe incluir equipos con asignado null")
        void equiposNoAsignados_ConAsignadoNull_DeberiaIncluirEquipo() {
            // Arrange
            Equipo equipoConAsignadoNull = new Equipo();
            equipoConAsignadoNull.setId(2L);
            equipoConAsignadoNull.setAsignado(null);

            List<Equipo> todosLosEquipos = new ArrayList<>();
            todosLosEquipos.add(equipo); // Asignado
            todosLosEquipos.add(equipoConAsignadoNull); // Asignado null

            when(equipoDao.findAll()).thenReturn(todosLosEquipos);

            // Act
            List<Equipo> resultado = equipoService.equiposNoAsignados();

            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(equipoConAsignadoNull, resultado.get(0));
            verify(equipoDao, times(1)).findAll();
        }
    }
} 