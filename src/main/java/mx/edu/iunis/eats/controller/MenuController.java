package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private PlatilloService platilloService;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("platillos", platilloService.obtenerTodos());
        return "estudiante/menu";
    }
}