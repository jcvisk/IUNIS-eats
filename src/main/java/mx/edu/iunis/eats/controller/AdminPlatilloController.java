package mx.edu.iunis.eats.controller;

import jakarta.validation.Valid;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/platillos")
public class AdminPlatilloController {

    @Autowired
    private PlatilloService platilloService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Platillos");
        model.addAttribute("breadcrumb", "Catálogo / Platillos");
        model.addAttribute("pantallaActual", "platillos");
        model.addAttribute("platillos", platilloService.obtenerTodos());
        return "admin/platillos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo platillo");
        model.addAttribute("breadcrumb", "Catálogo / Platillos / Nuevo");
        model.addAttribute("pantallaActual", "platillos");
        model.addAttribute("platillo", new Platillo());
        return "admin/formulario-platillo";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Platillo platillo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si hay errores de validación, regresamos al formulario
            model.addAttribute("pageTitle", platillo.getId() != null ? "Editar platillo" : "Nuevo platillo");
            model.addAttribute("breadcrumb", "Catálogo / Platillos / Formulario");
            model.addAttribute("pantallaActual", "platillos");
            return "admin/formulario-platillo";
        }
        platilloService.guardar(platillo);
        return "redirect:/admin/platillos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return platilloService.obtenerPorId(id).map(p -> {
            model.addAttribute("pageTitle", "Editar platillo");
            model.addAttribute("breadcrumb", "Catálogo / Platillos / Editar");
            model.addAttribute("pantallaActual", "platillos");
            model.addAttribute("platillo", p);
            return "admin/formulario-platillo";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "El platillo con ID " + id + " no existe.");
            return "redirect:/admin/platillos";
        });
    }
    @Autowired
    private mx.edu.iunis.eats.repository.DetallePedidoRepository detallePedidoRepository;
    // AHORA ES POST (Seguro)
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Verificamos si el platillo ya tiene pedidos
        if (detallePedidoRepository.existsByIdPlatillo(id)) {
            // Si tiene pedidos, HACEMOS SOFT DELETE
            platilloService.cambiarDisponibilidad(id, false);
            redirectAttributes.addFlashAttribute("error", "El platillo tiene pedidos asociados. Se ha marcado como 'Agotado' en lugar de eliminarse.");
        } else {
            // Si NO tiene pedidos, HACEMOS HARD DELETE
            platilloService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Platillo eliminado permanentemente.");
        }
        return "redirect:/admin/platillos";
    }

    @PostMapping("/toggle/{id}")
    public String toggleDisponibilidad(@PathVariable Long id, @RequestParam boolean disponible) {
        platilloService.cambiarDisponibilidad(id, disponible);
        return "redirect:/admin/platillos";
    }
}