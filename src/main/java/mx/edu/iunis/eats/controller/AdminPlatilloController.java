package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/platillos")
public class AdminPlatilloController {

    @Autowired
    private PlatilloService platilloService;

    // 1. LISTAR (Ya lo tenías)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Platillos");
        model.addAttribute("breadcrumb", "Catálogo / Platillos");
        model.addAttribute("pantallaActual", "platillos");
        model.addAttribute("platillos", platilloService.obtenerTodos());
        return "admin/platillos";
    }

    // 2. MOSTRAR FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo platillo");
        model.addAttribute("breadcrumb", "Catálogo / Platillos / Nuevo");
        model.addAttribute("pantallaActual", "platillos");
        model.addAttribute("platillo", new Platillo());
        return "admin/formulario-platillo";
    }

    // 3. GUARDAR (sirve para nuevo y editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Platillo platillo) {
        platilloService.guardar(platillo);
        return "redirect:/admin/platillos";
    }

    // 4. MOSTRAR FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Editar platillo");
        model.addAttribute("breadcrumb", "Catálogo / Platillos / Editar");
        model.addAttribute("pantallaActual", "platillos");
        platilloService.obtenerPorId(id).ifPresent(p -> model.addAttribute("platillo", p));
        return "admin/formulario-platillo";
    }

    // 5. ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        platilloService.eliminar(id);
        return "redirect:/admin/platillos";
    }

    // 6. CAMBIAR DISPONIBILIDAD (El switch de la tabla)
    @PostMapping("/toggle/{id}")
    public String toggleDisponibilidad(@PathVariable Long id, @RequestParam boolean disponible) {
        platilloService.cambiarDisponibilidad(id, disponible);
        return "redirect:/admin/platillos";
    }
}