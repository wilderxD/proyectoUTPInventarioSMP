package com.utp.gp.inventarioSMP.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

@DisplayName("Tests para la entidad Usuario")
class UsuarioTest {

    private Usuario usuario;
    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ROLE_ADMIN");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
        usuario.setRol(rol);
        usuario.setStatus("ACTIVO");
        usuario.setPassword("password123");
        usuario.setFecha_modificacion(new Date());
    }

    @Nested
    @DisplayName("Tests de constructores")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor vacío debe crear usuario sin valores")
        void constructorVacio_DeberiaCrearUsuarioSinValores() {
            Usuario usuarioVacio = new Usuario();
            
            assertNull(usuarioVacio.getId());
            assertNull(usuarioVacio.getUsername());
            assertNull(usuarioVacio.getRol());
            assertNull(usuarioVacio.getStatus());
            assertNull(usuarioVacio.getPassword());
            assertNull(usuarioVacio.getFecha_modificacion());
        }

        @Test
        @DisplayName("Constructor con parámetros debe crear usuario con valores")
        void constructorConParametros_DeberiaCrearUsuarioConValores() {
            Date fecha = new Date();
            Usuario usuarioConParametros = new Usuario(1L, "testuser", rol, "ACTIVO", "password123", fecha);
            
            assertEquals(1L, usuarioConParametros.getId());
            assertEquals("testuser", usuarioConParametros.getUsername());
            assertEquals(rol, usuarioConParametros.getRol());
            assertEquals("ACTIVO", usuarioConParametros.getStatus());
            assertEquals("password123", usuarioConParametros.getPassword());
            assertEquals(fecha, usuarioConParametros.getFecha_modificacion());
        }
    }

    @Nested
    @DisplayName("Tests de getters y setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Getter y setter de id")
        void getIdYSetId_DeberiaFuncionarCorrectamente() {
            usuario.setId(2L);
            assertEquals(2L, usuario.getId());
        }

        @Test
        @DisplayName("Getter y setter de username")
        void getUsernameYSetUsername_DeberiaFuncionarCorrectamente() {
            usuario.setUsername("nuevousuario");
            assertEquals("nuevousuario", usuario.getUsername());
        }

        @Test
        @DisplayName("Getter y setter de rol")
        void getRolYSetRol_DeberiaFuncionarCorrectamente() {
            Rol nuevoRol = new Rol();
            nuevoRol.setId(2L);
            nuevoRol.setNombre("ROLE_USER");
            
            usuario.setRol(nuevoRol);
            assertEquals(nuevoRol, usuario.getRol());
        }

        @Test
        @DisplayName("Getter y setter de status")
        void getStatusYSetStatus_DeberiaFuncionarCorrectamente() {
            usuario.setStatus("INACTIVO");
            assertEquals("INACTIVO", usuario.getStatus());
        }

        @Test
        @DisplayName("Getter y setter de password")
        void getPasswordYSetPassword_DeberiaFuncionarCorrectamente() {
            usuario.setPassword("nuevapassword");
            assertEquals("nuevapassword", usuario.getPassword());
        }

        @Test
        @DisplayName("Getter y setter de fecha_modificacion")
        void getFechaModificacionYSetFechaModificacion_DeberiaFuncionarCorrectamente() {
            Date nuevaFecha = new Date();
            usuario.setFecha_modificacion(nuevaFecha);
            assertEquals(nuevaFecha, usuario.getFecha_modificacion());
        }
    }

    @Nested
    @DisplayName("Tests de métodos de negocio")
    class BusinessMethodTests {

        @Test
        @DisplayName("actualizarFechas debe establecer fecha si es null")
        void actualizarFechas_ConFechaNull_DeberiaEstablecerFechaActual() {
            Usuario usuarioSinFecha = new Usuario();
            usuarioSinFecha.setId(1L);
            usuarioSinFecha.setUsername("test");
            usuarioSinFecha.setRol(rol);
            usuarioSinFecha.setStatus("ACTIVO");
            usuarioSinFecha.setPassword("password");
            
            // Simular @PrePersist
            usuarioSinFecha.actualizarFechas();
            
            assertNotNull(usuarioSinFecha.getFecha_modificacion());
        }

        @Test
        @DisplayName("actualizarFechas no debe cambiar fecha si ya existe")
        void actualizarFechas_ConFechaExistente_NoDeberiaCambiarFecha() {
            Date fechaOriginal = new Date();
            usuario.setFecha_modificacion(fechaOriginal);
            
            // Simular @PreUpdate
            usuario.actualizarFechas();
            
            assertEquals(fechaOriginal, usuario.getFecha_modificacion());
        }
    }

    @Nested
    @DisplayName("Tests de equals y hashCode")
    class EqualsHashCodeTests {

        @Test
        @DisplayName("equals debe retornar true para el mismo objeto")
        void equals_MismoObjeto_DeberiaRetornarTrue() {
            assertEquals(usuario, usuario);
        }

        @Test
        @DisplayName("equals debe retornar false para null")
        void equals_ConNull_DeberiaRetornarFalse() {
            assertNotEquals(usuario, null);
        }

        @Test
        @DisplayName("equals debe retornar false para objeto de diferente clase")
        void equals_ConDiferenteClase_DeberiaRetornarFalse() {
            assertNotEquals(usuario, "string");
        }

        @Test
        @DisplayName("equals debe retornar true para usuarios con mismo id")
        void equals_ConMismoId_DeberiaRetornarTrue() {
            Usuario otroUsuario = new Usuario();
            otroUsuario.setId(1L);
            otroUsuario.setUsername("diferente");
            
            assertEquals(usuario, otroUsuario);
        }

        @Test
        @DisplayName("hashCode debe ser consistente")
        void hashCode_DeberiaSerConsistente() {
            int hashCode1 = usuario.hashCode();
            int hashCode2 = usuario.hashCode();
            
            assertEquals(hashCode1, hashCode2);
        }
    }

    @Nested
    @DisplayName("Tests de toString")
    class ToStringTests {

        @Test
        @DisplayName("toString debe contener información del usuario")
        void toString_DeberiaContenerInformacionDelUsuario() {
            String toString = usuario.toString();
            
            assertTrue(toString.contains("testuser"));
            assertTrue(toString.contains("ACTIVO"));
            assertTrue(toString.contains("1"));
        }
    }
} 