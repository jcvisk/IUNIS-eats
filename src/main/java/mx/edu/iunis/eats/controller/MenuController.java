package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.*;
import mx.edu.iunis.eats.repository.DetallePedidoRepository;
import mx.edu.iunis.eats.repository.NotificacionRepository;
import mx.edu.iunis.eats.repository.PedidoRepository;
import mx.edu.iunis.eats.repository.UserRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    private PlatilloService platilloService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private UserRepository userRepository; // Inyectado
    @Autowired
    private NotificacionRepository notificacionRepository;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("platillos", platilloService.obtenerTodos());
        return "estudiante/menu";
    }

    // Método auxiliar para obtener el Usuario autenticado
    private User getUsuarioLogueado(Authentication authentication) {
        if (authentication == null) return null;
        return userRepository.findByUserName(authentication.getName()).orElse(null);
    }

    @GetMapping("/mis-pedidos")
    public String misPedidos(Model model, Authentication authentication) {
        User usuario = getUsuarioLogueado(authentication);

        if (usuario == null) return "redirect:/login";

        // Consulta directa a la BD, nada de cargar todo en memoria
        List<Pedido> misPedidos = pedidoRepository.findByIdUsuarioOrderByFechaHoraDesc(usuario.getId());

        Map<Long, List<Map<String, Object>>> detallesPorPedido = new HashMap<>();
        for (Pedido pedido : misPedidos) {
            List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido(pedido.getId());
            List<Map<String, Object>> listaItems = new ArrayList<>();
            for (DetallePedido d : detalles) {
                Platillo p = platilloService.obtenerPorId(d.getIdPlatillo()).orElse(null);
                Map<String, Object> item = new HashMap<>();
                item.put("nombre", (p != null) ? p.getNombre() : "Desconocido");
                item.put("cantidad", d.getCantidad());
                item.put("subtotal", d.getPrecioAplicado().multiply(new BigDecimal(d.getCantidad())));
                listaItems.add(item);
            }
            detallesPorPedido.put(pedido.getId(), listaItems);
        }

        model.addAttribute("pageTitle", "Mis pedidos");
        model.addAttribute("pedidos", misPedidos);
        model.addAttribute("detallesPorPedido", detallesPorPedido);
        model.addAttribute("mostrarBanner", false);
        return "estudiante/mis-pedidos";
    }

    @GetMapping("/notificaciones")
    public String notificaciones(Model model, Authentication authentication) {
        User usuario = getUsuarioLogueado(authentication);
        if (usuario == null) return "redirect:/login";

        List<Notificacion> notis = notificacionRepository.findByIdUsuarioOrderByFechaHoraDesc(usuario.getId());
        model.addAttribute("notificaciones", notis);
        model.addAttribute("pageTitle", "Notificaciones");
        return "estudiante/notificaciones";
    }
}