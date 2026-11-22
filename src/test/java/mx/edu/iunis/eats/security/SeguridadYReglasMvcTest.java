package mx.edu.iunis.eats.security;

import mx.edu.iunis.eats.controller.AdminPedidoController;
import mx.edu.iunis.eats.controller.AdminPlatilloController;
import mx.edu.iunis.eats.controller.CarritoController;
import mx.edu.iunis.eats.controller.MenuController;
import mx.edu.iunis.eats.controller.auth.AuthController;
import mx.edu.iunis.eats.domain.CarritoItem;
import mx.edu.iunis.eats.domain.Notificacion;
import mx.edu.iunis.eats.domain.Pedido;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.repository.DetallePedidoRepository;
import mx.edu.iunis.eats.repository.NotificacionRepository;
import mx.edu.iunis.eats.repository.PedidoRepository;
import mx.edu.iunis.eats.repository.UserRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import mx.edu.iunis.eats.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({
        AuthController.class,
        MenuController.class,
        CarritoController.class,
        AdminPedidoController.class,
        AdminPlatilloController.class
})
@Import(SecurityConfig.class)
class SeguridadYReglasMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PlatilloService platilloService;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @MockitoBean
    private DetallePedidoRepository detallePedidoRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private NotificacionRepository notificacionRepository;

    // --- 1. AUTORIZACIÓN POR ROLES (ANÓNIMO, STUDENT, CAFETERIA_STAFF) ---

    @Test
    void anonimoPuedeVerLoginYRegisterPeroNoMenuNiAdmin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));

        mockMvc.perform(get("/menu"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        mockMvc.perform(get("/admin/pedidos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "alumno1", roles = "STUDENT")
    void estudiantePuedeAccederAMenuPeroNoARutasAdmin() throws Exception {
        when(platilloService.obtenerTodos()).thenReturn(List.of());

        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("estudiante/menu"));

        mockMvc.perform(get("/admin/pedidos"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/admin/platillos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin1", roles = "CAFETERIA_STAFF")
    void staffPuedeAccederAPanelDePedidosYPlatillos() throws Exception {
        when(pedidoRepository.findAllByOrderByFechaHoraDesc()).thenReturn(List.of());
        when(platilloService.obtenerTodos()).thenReturn(List.of());

        mockMvc.perform(get("/admin/pedidos"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pedidos"));

        mockMvc.perform(get("/admin/platillos"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/platillos"));
    }

    // --- 2. PROTECCIÓN CSRF ---

    @Test
    @WithMockUser(username = "alumno1", roles = "STUDENT")
    void postSinTokenCsrfEsRechazadoCon403() throws Exception {
        mockMvc.perform(post("/carrito/agregar/1"))
                .andExpect(status().isForbidden());
    }

    // --- 3. REGISTRO SOLO ESTUDIANTE Y VALIDACIÓN DE FORMULARIO ---

    @Test
    void registroFuerzaRolEstudianteAunqueEnvienOtroRol() throws Exception {
        when(userService.userNameExists("nuevo_alumno")).thenReturn(false);

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("userName", "nuevo_alumno")
                        .param("password", "clave123")
                        .param("role", "2")) // Intento de escalar privilegios
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService).createUser(captor.capture());
        assertEquals(1, captor.getValue().getRole()); // Debe forzar rol 1 (STUDENT)
    }

    @Test
    void registroConCamposVaciosODuplicadosMuestraErrorDeFormulario() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("userName", "")
                        .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("formError"));

        when(userService.userNameExists("existente")).thenReturn(true);

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("userName", "existente")
                        .param("password", "clave123"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attribute("formError", "El nombre de usuario ya existe"));
    }

    // --- 4. PROPIEDAD DE PEDIDOS Y NOTIFICACIONES ---

    @Test
    @WithMockUser(username = "alumno1", roles = "STUDENT")
    void misPedidosYNotificacionesFiltranSoloPorUsuarioAutenticado() throws Exception {
        User alumno1 = new User(10L, "alumno1", "hash", 1);
        when(userRepository.findByUserName("alumno1")).thenReturn(Optional.of(alumno1));
        when(pedidoRepository.findByIdUsuarioOrderByFechaHoraDesc(10L)).thenReturn(List.of());
        when(notificacionRepository.findByIdUsuarioOrderByFechaHoraDesc(10L)).thenReturn(List.of());

        mockMvc.perform(get("/mis-pedidos"))
                .andExpect(status().isOk())
                .andExpect(view().name("estudiante/mis-pedidos"));

        mockMvc.perform(get("/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(view().name("estudiante/notificaciones"));

        verify(pedidoRepository).findByIdUsuarioOrderByFechaHoraDesc(10L);
        verify(pedidoRepository, never()).findAllByOrderByFechaHoraDesc();
        verify(notificacionRepository).findByIdUsuarioOrderByFechaHoraDesc(10L);
    }

    // --- 5. CARRITO, CONFIRMACIÓN Y CAMBIO DE ESTADO CON NOTIFICACIÓN ---

    @Test
    @WithMockUser(username = "alumno1", roles = "STUDENT")
    void agregarYConfirmarCarritoCreaPedidoPendienteYLimpiaSesion() throws Exception {
        Platillo platillo = new Platillo();
        platillo.setId(5L);
        platillo.setNombre("Chilaquiles Verdes");
        platillo.setPrecio(new BigDecimal("50.00"));
        platillo.setDisponibilidad(true);

        User alumno1 = new User(10L, "alumno1", "hash", 1);
        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setId(99L);

        when(platilloService.obtenerPorId(5L)).thenReturn(Optional.of(platillo));
        when(userRepository.findByUserName("alumno1")).thenReturn(Optional.of(alumno1));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/carrito/agregar/5")
                        .session(session)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu"));

        mockMvc.perform(post("/carrito/confirmar")
                        .session(session)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mis-pedidos"));

        verify(pedidoRepository).save(argThat(p ->
                p.getIdUsuario().equals(10L)
                        && "PENDIENTE".equals(p.getEstado())
                        && new BigDecimal("50.00").compareTo(p.getTotal()) == 0
        ));
        verify(detallePedidoRepository).save(any());
    }

    @Test
    @WithMockUser(username = "staff1", roles = "CAFETERIA_STAFF")
    void staffCambiaEstadoPedidoYGeneraNotificacion() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(77L);
        pedido.setIdUsuario(10L);
        pedido.setEstado("PENDIENTE");
        pedido.setFechaHora(LocalDateTime.now());

        when(pedidoRepository.findById(77L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(post("/admin/pedidos/cambiar-estado/77")
                        .with(csrf())
                        .param("nuevoEstado", "EN_PREPARACION"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pedidos?idPedido=77"));

        verify(pedidoRepository).save(argThat(p -> "EN_PREPARACION".equals(p.getEstado())));
        verify(notificacionRepository).save(argThat(n ->
                n.getIdUsuario().equals(10L)
                        && n.getIdPedido().equals(77L)
                        && !n.getLeida()
        ));
    }

    // --- 6. AGOTADOS (SOFT DELETE) Y ERRORES DE FORMULARIO DE PLATILLOS ---

    @Test
    @WithMockUser(username = "staff1", roles = "CAFETERIA_STAFF")
    void eliminarPlatilloConPedidosLoMarcaComoAgotadoEnLugarDeBorrarlo() throws Exception {
        when(detallePedidoRepository.existsByIdPlatillo(4L)).thenReturn(true);

        mockMvc.perform(post("/admin/platillos/eliminar/4")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/platillos"))
                .andExpect(flash().attributeExists("error"));

        verify(platilloService).cambiarDisponibilidad(4L, false);
        verify(platilloService, never()).eliminar(4L);
    }

    @Test
    @WithMockUser(username = "staff1", roles = "CAFETERIA_STAFF")
    void guardarPlatilloConDatosInvalidosRegresaAlFormularioConErrores() throws Exception {
        mockMvc.perform(post("/admin/platillos/guardar")
                        .with(csrf())
                        .param("nombre", "")
                        .param("precio", "-5")
                        .param("categoria", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/formulario-platillo"))
                .andExpect(model().hasErrors());

        verify(platilloService, never()).guardar(any());
    }
}

// TODO (Issue #24 - Regresión MVC): Agregar pruebas de endpoints una vez integrados en main:
// 1. Rechazo de creación/confirmación de pedido fuera del límite horario configurado.
// 2. Flujo de selección y compra de combos en menú/carrito y administración de combos.