package mx.edu.iunis.eats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Map<String, Object>> platillos = List.of(
                Map.of("nombre", "Torta de jamón", "precio", 35.0, "disponible", true),
                Map.of("nombre", "Quesadilla", "precio", 25.0, "disponible", true),
                Map.of("nombre", "Agua fresca", "precio", 15.0, "disponible", false),
                Map.of("nombre", "Sincronizada", "precio", 40.0, "disponible", true),
                Map.of("nombre", "Sándwich", "precio", 30.0, "disponible", true)
        );

        model.addAttribute("platillos", platillos);
        model.addAttribute("appName", "IUNIS Eats");
        return "menu";
    }
}