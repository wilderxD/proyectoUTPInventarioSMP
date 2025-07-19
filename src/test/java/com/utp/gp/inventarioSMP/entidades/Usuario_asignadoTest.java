package com.utp.gp.inventarioSMP.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

@DisplayName("Tests para la entidad Usuario_asignado")
class Usuario_asignadoTest {

    private Usuario_asignado usuarioAsignado;
    private Oficina oficina;
    private Equipo equipo;

    @BeforeEach
    void setUp() {
        oficina = new Oficina();
        oficina.setId(1L);
        oficina.setNombre_oficina("SISTEMAS");

        equipo = new Equipo();
        equipo.setId(1L);
        equipo.setEquipo_codigo("EQ001");
        equipo.setEquipo_descripcion("Laptop Dell");

        usuarioAsignado = new Usuario_asignado();
        usuarioAsignado.setId(1L);
        usuarioAsignado.setCodigo("12345678");
        usuarioAsignado.setNombre("Juan Pérez");
        usuarioAsignado.setOficina(oficina);
        usuarioAsignado.setEquipo(equipo);
        usuarioAsignado.setFecha_asignado(new Date());
    }

    @Nested
    @DisplayName("Tests de getters y setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Getter y setter de id")
        void getIdYSetId_DeberiaFuncionarCorrectamente() {
            usuarioAsignado.setId(2L);
            assertEquals(2L, usuarioAsignado.getId());
        }

        @Test
        @DisplayName("Getter y setter de codigo")
        void getCodigoYSetCodigo_DeberiaFuncionarCorrectamente() {
            usuarioAsignado.setCodigo("87654321");
            assertEquals("87654321", usuarioAsignado.getCodigo());
        }

        @Test
        @DisplayName("Getter y setter de nombre")
        void getNombreYSetNombre_DeberiaFuncionarCorrectamente() {
            usuarioAsignado.setNombre("María García");
            assertEquals("María García", usuarioAsignado.getNombre());
        }

        @Test
        @DisplayName("Getter y setter de oficina")
        void getOficinaYSetOficina_DeberiaFuncionarCorrectamente() {
            Oficina nuevaOficina = new Oficina();
            nuevaOficina.setId(2L);
            nuevaOficina.setNombre_oficina("CONTABILIDAD");
            
            usuarioAsignado.setOficina(nuevaOficina);
            assertEquals(nuevaOficina, usuarioAsignado.getOficina());
        }

        @Test
        @DisplayName("Getter y setter de equipo")
        void getEquipoYSetEquipo_DeberiaFuncionarCorrectamente() {
            Equipo nuevoEquipo = new Equipo();
            nuevoEquipo.setId(2L);
            nuevoEquipo.setEquipo_codigo("EQ002");
            nuevoEquipo.setEquipo_descripcion("Desktop HP");
            
            usuarioAsignado.setEquipo(nuevoEquipo);
            assertEquals(nuevoEquipo, usuarioAsignado.getEquipo());
        }

        @Test
        @DisplayName("Getter y setter de fecha_asignado")
        void getFechaAsignadoYSetFechaAsignado_DeberiaFuncionarCorrectamente() {
            Date nuevaFecha = new Date();
            usuarioAsignado.setFecha_asignado(nuevaFecha);
            assertEquals(nuevaFecha, usuarioAsignado.getFecha_asignado());
        }
    }

    @Nested
    @DisplayName("Tests de lógica de negocio")
    class BusinessLogicTests {

        @Test
        @DisplayName("Usuario asignado debe tener equipo asignado")
        void tieneEquipoAsignado_ConEquipo_DeberiaRetornarTrue() {
            assertTrue(usuarioAsignado.getEquipo() != null);
        }

        @Test
        @DisplayName("Usuario asignado sin equipo debe retornar null")
        void tieneEquipoAsignado_SinEquipo_DeberiaRetornarNull() {
            usuarioAsignado.setEquipo(null);
            assertNull(usuarioAsignado.getEquipo());
        }

        @Test
        @DisplayName("Usuario asignado debe tener oficina")
        void tieneOficina_ConOficina_DeberiaRetornarTrue() {
            assertTrue(usuarioAsignado.getOficina() != null);
        }

        @Test
        @DisplayName("Usuario asignado debe tener fecha de asignación")
        void tieneFechaAsignacion_ConFecha_DeberiaRetornarTrue() {
            assertTrue(usuarioAsignado.getFecha_asignado() != null);
        }

        @Test
        @DisplayName("Usuario asignado debe tener código válido")
        void tieneCodigoValido_ConCodigo_DeberiaRetornarTrue() {
            assertTrue(usuarioAsignado.getCodigo() != null && !usuarioAsignado.getCodigo().isEmpty());
        }

        @Test
        @DisplayName("Usuario asignado debe tener nombre válido")
        void tieneNombreValido_ConNombre_DeberiaRetornarTrue() {
            assertTrue(usuarioAsignado.getNombre() != null && !usuarioAsignado.getNombre().isEmpty());
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode")
    class EqualsHashCodeTests {

        @Test
        @DisplayName("equals debe retornar true para el mismo objeto")
        void equals_MismoObjeto_DeberiaRetornarTrue() {
            assertEquals(usuarioAsignado, usuarioAsignado);
        }

        @Test
        @DisplayName("equals debe retornar false para null")
        void equals_ConNull_DeberiaRetornarFalse() {
            assertNotEquals(usuarioAsignado, null);
        }

        @Test
        @DisplayName("equals debe retornar false para objeto de diferente clase")
        void equals_ConDiferenteClase_DeberiaRetornarFalse() {
            assertNotEquals(usuarioAsignado, "string");
        }

        @Test
        @DisplayName("equals debe retornar true para usuarios asignados con mismo id")
        void equals_ConMismoId_DeberiaRetornarTrue() {
            Usuario_asignado otroUsuarioAsignado = new Usuario_asignado();
            otroUsuarioAsignado.setId(1L);
            otroUsuarioAsignado.setCodigo("DIFERENTE");
            
            assertEquals(usuarioAsignado, otroUsuarioAsignado);
        }

        @Test
        @DisplayName("hashCode debe ser consistente")
        void hashCode_DeberiaSerConsistente() {
            int hashCode1 = usuarioAsignado.hashCode();
            int hashCode2 = usuarioAsignado.hashCode();
            
            assertEquals(hashCode1, hashCode2);
        }
    }

    @Nested
    @DisplayName("Tests de toString")
    class ToStringTests {

        @Test
        @DisplayName("toString debe contener información del usuario asignado")
        void toString_DeberiaContenerInformacionDelUsuarioAsignado() {
            String toString = usuarioAsignado.toString();
            
            assertTrue(toString.contains("12345678"));
            assertTrue(toString.contains("Juan Pérez"));
            assertTrue(toString.contains("1"));
        }
    }

    @Nested
    @DisplayName("Tests de validaciones")
    class ValidationTests {

        @Test
        @DisplayName("Usuario asignado con código vacío debe ser válido (validación se hace en anotaciones)")
        void codigoVacio_DeberiaSerPermitido() {
            usuarioAsignado.setCodigo("");
            assertEquals("", usuarioAsignado.getCodigo());
        }

        @Test
        @DisplayName("Usuario asignado con nombre vacío debe ser válido (validación se hace en anotaciones)")
        void nombreVacio_DeberiaSerPermitido() {
            usuarioAsignado.setNombre("");
            assertEquals("", usuarioAsignado.getNombre());
        }

        @Test
        @DisplayName("Usuario asignado sin oficina debe ser válido (validación se hace en anotaciones)")
        void sinOficina_DeberiaSerPermitido() {
            usuarioAsignado.setOficina(null);
            assertNull(usuarioAsignado.getOficina());
        }

        @Test
        @DisplayName("Usuario asignado sin equipo debe ser válido")
        void sinEquipo_DeberiaSerPermitido() {
            usuarioAsignado.setEquipo(null);
            assertNull(usuarioAsignado.getEquipo());
        }

        @Test
        @DisplayName("Usuario asignado sin fecha debe ser válido (validación se hace en anotaciones)")
        void sinFecha_DeberiaSerPermitido() {
            usuarioAsignado.setFecha_asignado(null);
            assertNull(usuarioAsignado.getFecha_asignado());
        }
    }

    @Nested
    @DisplayName("Tests de casos edge")
    class EdgeCaseTests {

        @Test
        @DisplayName("Usuario asignado con código muy largo debe ser válido")
        void codigoMuyLargo_DeberiaSerPermitido() {
            String codigoLargo = "A".repeat(1000);
            usuarioAsignado.setCodigo(codigoLargo);
            assertEquals(codigoLargo, usuarioAsignado.getCodigo());
        }

        @Test
        @DisplayName("Usuario asignado con nombre muy largo debe ser válido")
        void nombreMuyLargo_DeberiaSerPermitido() {
            String nombreLargo = "A".repeat(1000);
            usuarioAsignado.setNombre(nombreLargo);
            assertEquals(nombreLargo, usuarioAsignado.getNombre());
        }

        @Test
        @DisplayName("Usuario asignado con caracteres especiales debe ser válido")
        void caracteresEspeciales_DeberiaSerPermitido() {
            usuarioAsignado.setCodigo("123-456_789");
            usuarioAsignado.setNombre("José María O'Connor");
            
            assertEquals("123-456_789", usuarioAsignado.getCodigo());
            assertEquals("José María O'Connor", usuarioAsignado.getNombre());
        }
    }
} 