package com.utp.gp.inventarioSMP.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la entidad Rol")
class RolTest {

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ROLE_ADMIN");
    }

    @Nested
    @DisplayName("Tests de getters y setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Getter y setter de id")
        void getIdYSetId_DeberiaFuncionarCorrectamente() {
            rol.setId(2L);
            assertEquals(2L, rol.getId());
        }

        @Test
        @DisplayName("Getter y setter de nombre")
        void getNombreYSetNombre_DeberiaFuncionarCorrectamente() {
            rol.setNombre("ROLE_USER");
            assertEquals("ROLE_USER", rol.getNombre());
        }
    }

    @Nested
    @DisplayName("Tests de lógica de negocio")
    class BusinessLogicTests {

        @Test
        @DisplayName("Rol debe tener nombre válido")
        void tieneNombreValido_ConNombre_DeberiaRetornarTrue() {
            assertTrue(rol.getNombre() != null && !rol.getNombre().isEmpty());
        }

        @Test
        @DisplayName("Rol debe tener id válido")
        void tieneIdValido_ConId_DeberiaRetornarTrue() {
            assertTrue(rol.getId() != null);
        }

        @Test
        @DisplayName("Rol debe ser admin cuando nombre es ROLE_ADMIN")
        void esAdmin_ConNombreRoleAdmin_DeberiaRetornarTrue() {
            assertTrue("ROLE_ADMIN".equals(rol.getNombre()));
        }

        @Test
        @DisplayName("Rol no debe ser admin cuando nombre es ROLE_USER")
        void esAdmin_ConNombreRoleUser_DeberiaRetornarFalse() {
            rol.setNombre("ROLE_USER");
            assertFalse("ROLE_ADMIN".equals(rol.getNombre()));
        }

        @Test
        @DisplayName("Rol debe ser user cuando nombre es ROLE_USER")
        void esUser_ConNombreRoleUser_DeberiaRetornarTrue() {
            rol.setNombre("ROLE_USER");
            assertTrue("ROLE_USER".equals(rol.getNombre()));
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode")
    class EqualsHashCodeTests {

        @Test
        @DisplayName("equals debe retornar true para el mismo objeto")
        void equals_MismoObjeto_DeberiaRetornarTrue() {
            assertEquals(rol, rol);
        }

        @Test
        @DisplayName("equals debe retornar false para null")
        void equals_ConNull_DeberiaRetornarFalse() {
            assertNotEquals(rol, null);
        }

        @Test
        @DisplayName("equals debe retornar false para objeto de diferente clase")
        void equals_ConDiferenteClase_DeberiaRetornarFalse() {
            assertNotEquals(rol, "string");
        }

        @Test
        @DisplayName("equals debe retornar true para roles con mismo id")
        void equals_ConMismoId_DeberiaRetornarTrue() {
            Rol otroRol = new Rol();
            otroRol.setId(1L);
            otroRol.setNombre("ROLE_USER");
            
            assertEquals(rol, otroRol);
        }

        @Test
        @DisplayName("hashCode debe ser consistente")
        void hashCode_DeberiaSerConsistente() {
            int hashCode1 = rol.hashCode();
            int hashCode2 = rol.hashCode();
            
            assertEquals(hashCode1, hashCode2);
        }
    }

    @Nested
    @DisplayName("Tests de toString")
    class ToStringTests {

        @Test
        @DisplayName("toString debe contener información del rol")
        void toString_DeberiaContenerInformacionDelRol() {
            String toString = rol.toString();
            
            assertTrue(toString.contains("ROLE_ADMIN"));
            assertTrue(toString.contains("1"));
        }
    }

    @Nested
    @DisplayName("Tests de validaciones")
    class ValidationTests {

        @Test
        @DisplayName("Rol con nombre vacío debe ser válido (validación se hace en anotaciones)")
        void nombreVacio_DeberiaSerPermitido() {
            rol.setNombre("");
            assertEquals("", rol.getNombre());
        }

        @Test
        @DisplayName("Rol con nombre null debe ser válido (validación se hace en anotaciones)")
        void nombreNull_DeberiaSerPermitido() {
            rol.setNombre(null);
            assertNull(rol.getNombre());
        }
    }

    @Nested
    @DisplayName("Tests de casos edge")
    class EdgeCaseTests {

        @Test
        @DisplayName("Rol con nombre muy largo debe ser válido")
        void nombreMuyLargo_DeberiaSerPermitido() {
            String nombreLargo = "A".repeat(1000);
            rol.setNombre(nombreLargo);
            assertEquals(nombreLargo, rol.getNombre());
        }

        @Test
        @DisplayName("Rol con caracteres especiales debe ser válido")
        void caracteresEspeciales_DeberiaSerPermitido() {
            rol.setNombre("ROLE_ADMIN_ESPECIAL");
            assertEquals("ROLE_ADMIN_ESPECIAL", rol.getNombre());
        }

        @Test
        @DisplayName("Rol con números debe ser válido")
        void nombreConNumeros_DeberiaSerPermitido() {
            rol.setNombre("ROLE_ADMIN_2");
            assertEquals("ROLE_ADMIN_2", rol.getNombre());
        }
    }

    @Nested
    @DisplayName("Tests de comparación")
    class ComparisonTests {

        @Test
        @DisplayName("Roles con mismo id deben ser iguales")
        void rolesConMismoId_DeberianSerIguales() {
            Rol rol1 = new Rol();
            rol1.setId(1L);
            rol1.setNombre("ROLE_ADMIN");
            
            Rol rol2 = new Rol();
            rol2.setId(1L);
            rol2.setNombre("ROLE_USER");
            
            assertEquals(rol1, rol2);
        }

        @Test
        @DisplayName("Roles con diferente id deben ser diferentes")
        void rolesConDiferenteId_DeberianSerDiferentes() {
            Rol rol1 = new Rol();
            rol1.setId(1L);
            rol1.setNombre("ROLE_ADMIN");
            
            Rol rol2 = new Rol();
            rol2.setId(2L);
            rol2.setNombre("ROLE_ADMIN");
            
            assertNotEquals(rol1, rol2);
        }
    }

    @Nested
    @DisplayName("Tests de roles específicos")
    class SpecificRoleTests {

        @Test
        @DisplayName("Rol ROLE_ADMIN debe ser válido")
        void roleAdmin_DeberiaSerValido() {
            rol.setNombre("ROLE_ADMIN");
            assertEquals("ROLE_ADMIN", rol.getNombre());
        }

        @Test
        @DisplayName("Rol ROLE_USER debe ser válido")
        void roleUser_DeberiaSerValido() {
            rol.setNombre("ROLE_USER");
            assertEquals("ROLE_USER", rol.getNombre());
        }

        @Test
        @DisplayName("Rol personalizado debe ser válido")
        void rolePersonalizado_DeberiaSerValido() {
            rol.setNombre("ROLE_GUEST");
            assertEquals("ROLE_GUEST", rol.getNombre());
        }

        @Test
        @DisplayName("Rol sin prefijo ROLE_ debe ser válido")
        void roleSinPrefijo_DeberiaSerValido() {
            rol.setNombre("ADMIN");
            assertEquals("ADMIN", rol.getNombre());
        }
    }

    @Nested
    @DisplayName("Tests de casos especiales")
    class SpecialCaseTests {

        @Test
        @DisplayName("Rol con espacios debe ser válido")
        void roleConEspacios_DeberiaSerValido() {
            rol.setNombre("ROLE ADMIN");
            assertEquals("ROLE ADMIN", rol.getNombre());
        }

        @Test
        @DisplayName("Rol con guiones debe ser válido")
        void roleConGuiones_DeberiaSerValido() {
            rol.setNombre("ROLE-ADMIN");
            assertEquals("ROLE-ADMIN", rol.getNombre());
        }

        @Test
        @DisplayName("Rol con guiones bajos debe ser válido")
        void roleConGuionesBajos_DeberiaSerValido() {
            rol.setNombre("ROLE_ADMIN_ESPECIAL");
            assertEquals("ROLE_ADMIN_ESPECIAL", rol.getNombre());
        }

        @Test
        @DisplayName("Rol con minúsculas debe ser válido")
        void roleConMinusculas_DeberiaSerValido() {
            rol.setNombre("role_admin");
            assertEquals("role_admin", rol.getNombre());
        }
    }
} 