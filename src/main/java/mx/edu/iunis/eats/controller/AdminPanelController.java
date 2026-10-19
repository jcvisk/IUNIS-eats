package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @Autowired
    private PlatilloService platilloService;

    @GetMapping("/admin")
    public String panel(Model model) {
        model.addAttribute("pageTitle", "Panel");
        model.addAttribute("breadcrumb", "Panel / Vista general");
        model.addAttribute("pantallaActual", "panel");
        model.addAttribute("estadoTurno", "Turno Activo");
        model.addAttribute("pedidosPendientes", 0);
        model.addAttribute("enCocina", 0);
        model.addAttribute("listos", 0);
        model.addAttribute("platillosActivos", platilloService.obtenerTodos().size());
        return "admin/panel";
    }
}