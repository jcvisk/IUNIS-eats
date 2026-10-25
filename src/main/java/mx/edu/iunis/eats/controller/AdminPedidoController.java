package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.Pedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pageTitle", "Pedidos");
        model.addAttribute("breadcrumb", "Pedidos / Recepción de comandas");
        model.addAttribute("pantallaActual", "pedidos");

        // --- DATOS DE PRUEBA (MOCK) ---
        List<Pedido> pedidosMock = new ArrayList<>();

        Pedido p1 = new Pedido();
        p1.setId(43L);
        p1.setIdUsuario(20230145L);
        p1.setFechaHora(LocalDateTime.now().minusMinutes(3));
        p1.setTotal(new BigDecimal("95.00"));
        p1.setEstado("PENDIENTE");
        pedidosMock.add(p1);

        Pedido p2 = new Pedido();
        p2.setId(44L);
        p2.setIdUsuario(20230152L);
        p2.setFechaHora(LocalDateTime.now().minusMinutes(12));
        p2.setTotal(new BigDecimal("50.00"));
        p2.setEstado("PENDIENTE");
        pedidosMock.add(p2);

        model.addAttribute("pedidos", pedidosMock);
        model.addAttribute("pedidoSeleccionado", pedidosMock.isEmpty() ? null : pedidosMock.get(0));

        return "admin/pedidos";
    }
}