package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.DetallePedido;
import mx.edu.iunis.eats.domain.Pedido;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.repository.DetallePedidoRepository;
import mx.edu.iunis.eats.repository.PedidoRepository;
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
    private mx.edu.iunis.eats.repository.NotificacionRepository notificacionRepository;

    @GetMapping("/notificaciones")
    public String notificaciones(Model model, Authentication authentication) {
        String username = authentication.getName();
        Long idEstudiante = 0L;
        try { idEstudiante = Long.parseLong(username); } catch (Exception e) {}

        List<mx.edu.iunis.eats.domain.Notificacion> notis =
                notificacionRepository.findByIdUsuarioOrderByFechaHoraDesc(idEstudiante);

        model.addAttribute("notificaciones", notis);
        model.addAttribute("pageTitle", "Notificaciones");
        return "estudiante/notificaciones";
    }

    @Autowired
    private PlatilloService platilloService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("platillos", platilloService.obtenerTodos());
        return "estudiante/menu";
    }

    @GetMapping("/mis-pedidos")
    public String misPedidos(Model model, Authentication authentication) {
        // 1. Obtener el ID del estudiante logueado
        String username = authentication.getName();
        Long idEstudiante = 0L;
        try {
            idEstudiante = Long.parseLong(username);
        } catch (NumberFormatException e) {
            // Ignorar
        }

        Long finalIdEstudiante = idEstudiante;

        // 2. Filtrar los pedidos de este estudiante
        List<Pedido> misPedidos = pedidoRepository.findAll().stream()
                .filter(p -> p.getIdUsuario() != null && p.getIdUsuario().equals(finalIdEstudiante))
                .toList();

        // 3. Crear un Mapa para guardar los detalles de cada pedido
        Map<Long, List<Map<String, Object>>> detallesPorPedido = new HashMap<>();

        for (Pedido pedido : misPedidos) {
            List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido(pedido.getId());
            List<Map<String, Object>> listaItems = new ArrayList<>();

            for (DetallePedido d : detalles) {
                Platillo p = platilloService.obtenerPorId(d.getIdPlatillo()).orElse(null);
                String nombre = (p != null) ? p.getNombre() : "Platillo Desconocido";

                Map<String, Object> item = new HashMap<>();
                item.put("nombre", nombre);
                item.put("cantidad", d.getCantidad());
                item.put("subtotal", d.getPrecioAplicado().multiply(new BigDecimal(d.getCantidad())));
                listaItems.add(item);
            }
            detallesPorPedido.put(pedido.getId(), listaItems);
        }

        model.addAttribute("pageTitle", "Mis pedidos");
        model.addAttribute("pedidos", misPedidos);
        model.addAttribute("detallesPorPedido", detallesPorPedido); // Enviamos los detalles al HTML
        model.addAttribute("mostrarBanner", false);

        return "estudiante/mis-pedidos";
    }
}