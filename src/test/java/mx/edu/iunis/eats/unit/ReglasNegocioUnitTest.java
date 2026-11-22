package mx.edu.iunis.eats.unit;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import mx.edu.iunis.eats.domain.CarritoItem;
import mx.edu.iunis.eats.domain.Notificacion;
import mx.edu.iunis.eats.domain.Pedido;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.repository.DatosPersonalesRepository;
import mx.edu.iunis.eats.repository.PlatilloRepository;
import mx.edu.iunis.eats.repository.UserRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import mx.edu.iunis.eats.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReglasNegocioUnitTest {

    private Validator validator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DatosPersonalesRepository datosPersonalesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PlatilloRepository platilloRepository;

    @InjectMocks
    private PlatilloService platilloService;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void calcularSubtotalesYTotalDeCarrito() {
        CarritoItem item1 = new CarritoItem(1L, "Chilaquiles", new BigDecimal("45.50"), 2);
        CarritoItem item2 = new CarritoItem(2L, "Agua de Horchata", new BigDecimal("18.00"), 3);

        List<CarritoItem> carrito = List.of(item1, item2);
        BigDecimal total = carrito.stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(new BigDecimal("91.00"), item1.getSubtotal());
        assertEquals(new BigDecimal("54.00"), item2.getSubtotal());
        assertEquals(new BigDecimal("145.00"), total);
    }

    @Test
    void validarReglasDePlatilloConBeanValidation() {
        Platillo invalido = new Platillo();
        invalido.setNombre("");
        invalido.setPrecio(new BigDecimal("-10.00"));
        invalido.setCategoria("");

        Set<ConstraintViolation<Platillo>> violaciones = validator.validate(invalido);
        assertEquals(3, violaciones.size());

        Platillo valido = new Platillo();
        valido.setNombre("Enchiladas Suizas");
        valido.setDescripcion("Orden de 4 piezas");
        valido.setPrecio(new BigDecimal("65.00"));
        valido.setCategoria("Comida");

        assertTrue(validator.validate(valido).isEmpty());
        assertTrue(valido.getDisponibilidad());
    }

    @Test
    void flujoDeEstadosPedidoYNotificacion() {
        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setIdUsuario(5L);
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setTotal(new BigDecimal("85.00"));
        pedido.setEstado("PENDIENTE");

        assertEquals("PENDIENTE", pedido.getEstado());

        pedido.setEstado("LISTO");
        Notificacion noti = new Notificacion();
        noti.setIdUsuario(pedido.getIdUsuario());
        noti.setIdPedido(pedido.getId());
        noti.setTipo("ESTADO");
        noti.setMensaje("Tu pedido #" + pedido.getId() + " ha cambiado al estado: " + pedido.getEstado());
        noti.setLeida(false);

        assertEquals("LISTO", pedido.getEstado());
        assertEquals(5L, noti.getIdUsuario());
        assertEquals("Tu pedido #10 ha cambiado al estado: LISTO", noti.getMensaje());
        assertFalse(noti.getLeida());
    }

    @Test
    void crearUsuarioEncriptaContrasenaYAsignaRol() {
        User nuevo = new User();
        nuevo.setUserName("estudiante01");
        nuevo.setPassword("secreto123");
        nuevo.setRole(1);

        when(passwordEncoder.encode("secreto123")).thenReturn("$2a$10$hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User guardado = userService.createUser(nuevo);

        assertEquals("$2a$10$hashedPassword", guardado.getPassword());
        assertEquals(1, guardado.getRole());
        verify(userRepository).save(nuevo);
    }

    @Test
    void cambiarDisponibilidadPlatilloMarcaAgotado() {
        Platillo platillo = new Platillo();
        platillo.setId(3L);
        platillo.setNombre("Torta de Milanesa");
        platillo.setDisponibilidad(true);

        when(platilloRepository.findById(3L)).thenReturn(Optional.of(platillo));

        platilloService.cambiarDisponibilidad(3L, false);

        assertFalse(platillo.getDisponibilidad());
        verify(platilloRepository).save(platillo);
    }
}


// TODO (Issue #24 - Regresión): Agregar pruebas unitarias para:
// 1. Validación de límite horario de pedidos (PeriodoServicio: bloqueo fuera de horario).
// 2. Lógica de negocio y cálculo de precios de combos (Combo / ComboPlatillo).