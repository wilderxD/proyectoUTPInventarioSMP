package com.utp.gp.inventarioSMP.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la entidad Oficina")
class OficinaTest {

    private Oficina oficina;

    @BeforeEach
    void setUp() {
        oficina = new Oficina();
        oficina.setId(1L);
        oficina.setNombre_oficina("SISTEMAS");
    }

    @Nested
    @DisplayName("Tests de constructores")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor vacío debe crear oficina sin valores")
        void constructorVacio_DeberiaCrearOficinaSinValores() {
            Oficina oficinaVacia = new Oficina();
            
            assertNull(oficinaVacia.getId());
            assertNull(oficinaVacia.getNombre_oficina());
        }

        @Test
        @DisplayName("Constructor con parámetros debe crear oficina con valores")
        void constructorConParametros_DeberiaCrearOficinaConValores() {
            Oficina oficinaConParametros = new Oficina(1L, "CONTABILIDAD");
            
            assertEquals(1L, oficinaConParametros.getId());
            assertEquals("CONTABILIDAD", oficinaConParametros.getNombre_oficina());
        }
    }

    @Nested
    @DisplayName("Tests de getters y setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Getter y setter de id")
        void getIdYSetId_DeberiaFuncionarCorrectamente() {
            oficina.setId(2L);
            assertEquals(2L, oficina.getId());
        }

        @Test
        @DisplayName("Getter y setter de nombre_oficina")
        void getNombreOficinaYSetNombreOficina_DeberiaFuncionarCorrectamente() {
            oficina.setNombre_oficina("R.R.H.H.");
            assertEquals("R.R.H.H.", oficina.getNombre_oficina());
        }
    }

    @Nested
    @DisplayName("Tests de lógica de negocio")
    class BusinessLogicTests {

        @Test
        @DisplayName("Oficina debe tener nombre válido")
        void tieneNombreValido_ConNombre_DeberiaRetornarTrue() {
            assertTrue(oficina.getNombre_oficina() != null && !oficina.getNombre_oficina().isEmpty());
        }

        @Test
        @DisplayName("Oficina debe tener id válido")
        void tieneIdValido_ConId_DeberiaRetornarTrue() {
            assertTrue(oficina.getId() != null);
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode")
    class EqualsHashCodeTests {

        @Test
        @DisplayName("equals debe retornar true para el mismo objeto")
        void equals_MismoObjeto_DeberiaRetornarTrue() {
            assertEquals(oficina, oficina);
        }

        @Test
        @DisplayName("equals debe retornar false para null")
        void equals_ConNull_DeberiaRetornarFalse() {
            assertNotEquals(oficina, null);
        }

        @Test
        @DisplayName("equals debe retornar false para objeto de diferente clase")
        void equals_ConDiferenteClase_DeberiaRetornarFalse() {
            assertNotEquals(oficina, "string");
        }

        @Test
        @DisplayName("equals debe retornar true para oficinas con mismo id")
        void equals_ConMismoId_DeberiaRetornarTrue() {
            Oficina otraOficina = new Oficina();
            otraOficina.setId(1L);
            otraOficina.setNombre_oficina("DIFERENTE");
            
            assertEquals(oficina, otraOficina);
        }

        @Test
        @DisplayName("hashCode debe ser consistente")
        void hashCode_DeberiaSerConsistente() {
            int hashCode1 = oficina.hashCode();
            int hashCode2 = oficina.hashCode();
            
            assertEquals(hashCode1, hashCode2);
        }
    }

    @Nested
    @DisplayName("Tests de toString")
    class ToStringTests {

        @Test
        @DisplayName("toString debe contener información de la oficina")
        void toString_DeberiaContenerInformacionDeLaOficina() {
            String toString = oficina.toString();
            
            assertTrue(toString.contains("SISTEMAS"));
            assertTrue(toString.contains("1"));
        }
    }

    @Nested
    @DisplayName("Tests de validaciones")
    class ValidationTests {

        @Test
        @DisplayName("Oficina con nombre vacío debe ser válido (validación se hace en anotaciones)")
        void nombreVacio_DeberiaSerPermitido() {
            oficina.setNombre_oficina("");
            assertEquals("", oficina.getNombre_oficina());
        }

        @Test
        @DisplayName("Oficina con nombre null debe ser válido (validación se hace en anotaciones)")
        void nombreNull_DeberiaSerPermitido() {
            oficina.setNombre_oficina(null);
            assertNull(oficina.getNombre_oficina());
        }
    }

    @Nested
    @DisplayName("Tests de casos edge")
    class EdgeCaseTests {

        @Test
        @DisplayName("Oficina con nombre muy largo debe ser válido")
        void nombreMuyLargo_DeberiaSerPermitido() {
            String nombreLargo = "A".repeat(1000);
            oficina.setNombre_oficina(nombreLargo);
            assertEquals(nombreLargo, oficina.getNombre_oficina());
        }

        @Test
        @DisplayName("Oficina con caracteres especiales debe ser válido")
        void caracteresEspeciales_DeberiaSerPermitido() {
            oficina.setNombre_oficina("SISTEMAS & TECNOLOGÍA");
            assertEquals("SISTEMAS & TECNOLOGÍA", oficina.getNombre_oficina());
        }

        @Test
        @DisplayName("Oficina con números debe ser válido")
        void nombreConNumeros_DeberiaSerPermitido() {
            oficina.setNombre_oficina("SISTEMAS 2.0");
            assertEquals("SISTEMAS 2.0", oficina.getNombre_oficina());
        }
    }

    @Nested
    @DisplayName("Tests de comparación")
    class ComparisonTests {

        @Test
        @DisplayName("Oficinas con mismo id deben ser iguales")
        void oficinasConMismoId_DeberianSerIguales() {
            Oficina oficina1 = new Oficina(1L, "SISTEMAS");
            Oficina oficina2 = new Oficina(1L, "CONTABILIDAD");
            
            assertEquals(oficina1, oficina2);
        }

        @Test
        @DisplayName("Oficinas con diferente id deben ser diferentes")
        void oficinasConDiferenteId_DeberianSerDiferentes() {
            Oficina oficina1 = new Oficina(1L, "SISTEMAS");
            Oficina oficina2 = new Oficina(2L, "SISTEMAS");
            
            assertNotEquals(oficina1, oficina2);
        }
    }
} 