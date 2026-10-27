package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.*;
import mx.edu.iunis.eats.repository.DetallePedidoRepository;
import mx.edu.iunis.eats.repository.PedidoRepository;
import mx.edu.iunis.eats.repository.UserRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private PlatilloService platilloService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private UserRepository userRepository; // Inyectamos el repositorio de usuarios

    @SuppressWarnings("unchecked")
    private List<CarritoItem> getCarrito(HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        List<CarritoItem> carrito = getCarrito(session);
        BigDecimal total = carrito.stream().map(CarritoItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("pageTitle", "Tu pedido");
        return "estudiante/carrito";
    }

    @PostMapping("/agregar/{id}")
    public String agregar(@PathVariable Long id, HttpSession session) {
        Platillo platillo = platilloService.obtenerPorId(id).orElse(null);
        if (platillo != null) {
            List<CarritoItem> carrito = getCarrito(session);
            boolean existe = false;
            for (CarritoItem item : carrito) {
                if (item.getIdPlatillo().equals(id)) {
                    item.setCantidad(item.getCantidad() + 1);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                carrito.add(new CarritoItem(platillo.getId(), platillo.getNombre(), new BigDecimal(platillo.getPrecio().toString()), 1));
            }
        }
        return "redirect:/menu";
    }

    @PostMapping("/confirmar")
    public String confirmar(HttpSession session, Authentication authentication) {
        List<CarritoItem> carrito = getCarrito(session);

        if (!carrito.isEmpty()) {
            // --- SOLUCIÓN AL ISSUE #15 ---
            // 1. Buscamos al usuario real por su username en la BD
            String username = authentication.getName();
            User usuario = userRepository.findByUserName(username).orElse(null);

            // Si el usuario no existe por alguna razón, lo rechazamos (controlado)
            if (usuario == null) {
                return "redirect:/login?error=usuarioNoEncontrado";
            }

            BigDecimal total = carrito.stream().map(CarritoItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

            Pedido pedido = new Pedido();
            pedido.setIdUsuario(usuario.getId()); // Guardamos el ID REAL (Primary Key)
            pedido.setFechaHora(LocalDateTime.now());
            pedido.setTotal(total);
            pedido.setEstado("PENDIENTE");

            pedido = pedidoRepository.save(pedido);

            for (CarritoItem item : carrito) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdPedido(pedido.getId());
                detalle.setIdPlatillo(item.getIdPlatillo());
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioAplicado(item.getPrecio());
                detallePedidoRepository.save(detalle);
            }
            carrito.clear();
        }
        return "redirect:/mis-pedidos";
    }
}