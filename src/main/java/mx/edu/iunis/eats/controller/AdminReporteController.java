package mx.edu.iunis.eats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/reportes")
public class AdminReporteController {

    @GetMapping
    public String reportes(Model model) {
        model.addAttribute("pageTitle", "Reportes");
        model.addAttribute("breadcrumb", "Análisis / Reportes de demanda");
        model.addAttribute("pantallaActual", "reportes");

        // Datos de prueba para que el gráfico se vea bien
        model.addAttribute("topPlatillos", List.of(
                Map.of("nombre", "Torta de Jamón", "categoria", "Comida", "unidades", 80, "porcentaje", 100, "destacado", false),
                Map.of("nombre", "Agua Fresca", "categoria", "Bebida", "unidades", 65, "porcentaje", 81, "destacado", true),
                Map.of("nombre", "Quesadilla", "categoria", "Comida", "unidades", 48, "porcentaje", 60, "destacado", false),
                Map.of("nombre", "Sincronizada", "categoria", "Comida", "unidades", 35, "porcentaje", 44, "destacado", false),
                Map.of("nombre", "Café", "categoria", "Bebida", "unidades", 25, "porcentaje", 31, "destacado", false)
        ));

        return "admin/reportes";
    }
}