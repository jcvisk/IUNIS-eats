package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.repository.PedidoRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @Autowired
    private PlatilloService platilloService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/admin")
    public String panel(Model model) {
        model.addAttribute("pageTitle", "Panel");
        model.addAttribute("breadcrumb", "Panel / Vista general");
        model.addAttribute("pantallaActual", "panel");
        model.addAttribute("estadoTurno", "Turno Activo");

        // Datos reales consultados a la base de datos
        model.addAttribute("pedidosPendientes", pedidoRepository.countByEstado("PENDIENTE"));
        model.addAttribute("enCocina", pedidoRepository.countByEstado("EN PREPARACION"));
        model.addAttribute("listos", pedidoRepository.countByEstado("LISTO"));
        model.addAttribute("platillosActivos", platilloService.obtenerTodos().size());

        return "admin/panel";
    }
}