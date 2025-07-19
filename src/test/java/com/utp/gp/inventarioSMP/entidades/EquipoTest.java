package com.utp.gp.inventarioSMP.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la entidad Equipo")
class EquipoTest {

    private Equipo equipo;
    private Categoria categoria;
    private Usuario_asignado usuarioAsignado;

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
    }

    @Nested
    @DisplayName("Tests de constructores")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor vacío debe crear equipo sin valores")
        void constructorVacio_DeberiaCrearEquipoSinValores() {
            Equipo equipoVacio = new Equipo();
            
            assertNull(equipoVacio.getId());
            assertNull(equipoVacio.getEquipo_codigo());
            assertNull(equipoVacio.getEquipo_descripcion());
            assertEquals(0.0, equipoVacio.getValor());
            assertNull(equipoVacio.getTipoMoneda());
            assertNull(equipoVacio.getObservacion());
            assertNull(equipoVacio.getCategoria());
            assertNull(equipoVacio.getEstado());
            assertNull(equipoVacio.getAsignado());
        }

        @Test
        @DisplayName("Constructor con parámetros debe crear equipo con valores")
        void constructorConParametros_DeberiaCrearEquipoConValores() {
            Equipo equipoConParametros = new Equipo(1L, "EQ001", "Laptop Dell", 1500.0, 
                "DOLARES", "Nuevo", categoria, "ACTIVO", usuarioAsignado);
            
            assertEquals(1L, equipoConParametros.getId());
            assertEquals("EQ001", equipoConParametros.getEquipo_codigo());
            assertEquals("Laptop Dell", equipoConParametros.getEquipo_descripcion());
            assertEquals(1500.0, equipoConParametros.getValor());
            assertEquals("DOLARES", equipoConParametros.getTipoMoneda());
            assertEquals("Nuevo", equipoConParametros.getObservacion());
            assertEquals(categoria, equipoConParametros.getCategoria());
            assertEquals("ACTIVO", equipoConParametros.getEstado());
            assertEquals(usuarioAsignado, equipoConParametros.getAsignado());
        }
    }

    @Nested
    @DisplayName("Tests de getters y setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Getter y setter de id")
        void getIdYSetId_DeberiaFuncionarCorrectamente() {
            equipo.setId(2L);
            assertEquals(2L, equipo.getId());
        }

        @Test
        @DisplayName("Getter y setter de equipo_codigo")
        void getEquipoCodigoYSetEquipoCodigo_DeberiaFuncionarCorrectamente() {
            equipo.setEquipo_codigo("EQ002");
            assertEquals("EQ002", equipo.getEquipo_codigo());
        }

        @Test
        @DisplayName("Getter y setter de equipo_descripcion")
        void getEquipoDescripcionYSetEquipoDescripcion_DeberiaFuncionarCorrectamente() {
            equipo.setEquipo_descripcion("Desktop HP");
            assertEquals("Desktop HP", equipo.getEquipo_descripcion());
        }

        @Test
        @DisplayName("Getter y setter de valor")
        void getValorYSetValor_DeberiaFuncionarCorrectamente() {
            equipo.setValor(2000.0);
            assertEquals(2000.0, equipo.getValor());
        }

        @Test
        @DisplayName("Getter y setter de tipoMoneda")
        void getTipoMonedaYSetTipoMoneda_DeberiaFuncionarCorrectamente() {
            equipo.setTipoMoneda("SOLES");
            assertEquals("SOLES", equipo.getTipoMoneda());
        }

        @Test
        @DisplayName("Getter y setter de observacion")
        void getObservacionYSetObservacion_DeberiaFuncionarCorrectamente() {
            equipo.setObservacion("Equipo usado");
            assertEquals("Equipo usado", equipo.getObservacion());
        }

        @Test
        @DisplayName("Getter y setter de categoria")
        void getCategoriaYSetCategoria_DeberiaFuncionarCorrectamente() {
            Categoria nuevaCategoria = new Categoria();
            nuevaCategoria.setId(2L);
            nuevaCategoria.setCategoria_nombre("MÓVIL");
            
            equipo.setCategoria(nuevaCategoria);
            assertEquals(nuevaCategoria, equipo.getCategoria());
        }

        @Test
        @DisplayName("Getter y setter de estado")
        void getEstadoYSetEstado_DeberiaFuncionarCorrectamente() {
            equipo.setEstado("INACTIVO");
            assertEquals("INACTIVO", equipo.getEstado());
        }

        @Test
        @DisplayName("Getter y setter de asignado")
        void getAsignadoYSetAsignado_DeberiaFuncionarCorrectamente() {
            Usuario_asignado nuevoAsignado = new Usuario_asignado();
            nuevoAsignado.setId(2L);
            nuevoAsignado.setCodigo("87654321");
            nuevoAsignado.setNombre("María García");
            
            equipo.setAsignado(nuevoAsignado);
            assertEquals(nuevoAsignado, equipo.getAsignado());
        }
    }

    @Nested
    @DisplayName("Tests de lógica de negocio")
    class BusinessLogicTests {

        @Test
        @DisplayName("Equipo debe estar asignado cuando tiene usuario_asignado")
        void estaAsignado_ConUsuarioAsignado_DeberiaRetornarTrue() {
            assertTrue(equipo.getAsignado() != null);
        }

        @Test
        @DisplayName("Equipo no debe estar asignado cuando no tiene usuario_asignado")
        void estaAsignado_SinUsuarioAsignado_DeberiaRetornarFalse() {
            equipo.setAsignado(null);
            assertTrue(equipo.getAsignado() == null);
        }

        @Test
        @DisplayName("Equipo debe estar activo cuando estado es ACTIVO")
        void estaActivo_ConEstadoActivo_DeberiaRetornarTrue() {
            assertTrue("ACTIVO".equals(equipo.getEstado()));
        }

        @Test
        @DisplayName("Equipo no debe estar activo cuando estado es INACTIVO")
        void estaActivo_ConEstadoInactivo_DeberiaRetornarFalse() {
            equipo.setEstado("INACTIVO");
            assertFalse("ACTIVO".equals(equipo.getEstado()));
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode")
    class EqualsHashCodeTests {

        @Test
        @DisplayName("equals debe retornar true para el mismo objeto")
        void equals_MismoObjeto_DeberiaRetornarTrue() {
            assertEquals(equipo, equipo);
        }

        @Test
        @DisplayName("equals debe retornar false para null")
        void equals_ConNull_DeberiaRetornarFalse() {
            assertNotEquals(equipo, null);
        }

        @Test
        @DisplayName("equals debe retornar false para objeto de diferente clase")
        void equals_ConDiferenteClase_DeberiaRetornarFalse() {
            assertNotEquals(equipo, "string");
        }

        @Test
        @DisplayName("equals debe retornar true para equipos con mismo id")
        void equals_ConMismoId_DeberiaRetornarTrue() {
            Equipo otroEquipo = new Equipo();
            otroEquipo.setId(1L);
            otroEquipo.setEquipo_codigo("DIFERENTE");
            
            assertEquals(equipo, otroEquipo);
        }

        @Test
        @DisplayName("hashCode debe ser consistente")
        void hashCode_DeberiaSerConsistente() {
            int hashCode1 = equipo.hashCode();
            int hashCode2 = equipo.hashCode();
            
            assertEquals(hashCode1, hashCode2);
        }
    }

    @Nested
    @DisplayName("Tests de toString")
    class ToStringTests {

        @Test
        @DisplayName("toString debe contener información del equipo")
        void toString_DeberiaContenerInformacionDelEquipo() {
            String toString = equipo.toString();
            
            assertTrue(toString.contains("EQ001"));
            assertTrue(toString.contains("Laptop Dell Inspiron"));
            assertTrue(toString.contains("ACTIVO"));
            assertTrue(toString.contains("1"));
        }
    }

    @Nested
    @DisplayName("Tests de validaciones")
    class ValidationTests {

        @Test
        @DisplayName("Equipo con valor negativo debe ser válido (validación se hace en anotaciones)")
        void valorNegativo_DeberiaSerPermitido() {
            equipo.setValor(-100.0);
            assertEquals(-100.0, equipo.getValor());
        }

        @Test
        @DisplayName("Equipo con valor cero debe ser válido")
        void valorCero_DeberiaSerPermitido() {
            equipo.setValor(0.0);
            assertEquals(0.0, equipo.getValor());
        }

        @Test
        @DisplayName("Equipo con código vacío debe ser válido (validación se hace en anotaciones)")
        void codigoVacio_DeberiaSerPermitido() {
            equipo.setEquipo_codigo("");
            assertEquals("", equipo.getEquipo_codigo());
        }
    }
} 